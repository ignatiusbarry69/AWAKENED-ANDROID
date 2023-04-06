package com.example.hubuser.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.hubuser.R
import com.example.hubuser.background.FactoryMain
import com.example.hubuser.background.FactoryRepository
import com.example.hubuser.background.FavoriteViewModel
import com.example.hubuser.background.FollowViewModel
import com.example.hubuser.database.FavoriteUser
import com.example.hubuser.databinding.FragmentDetailUserBinding
import com.example.hubuser.response.DetailUserResponse
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserFragment : Fragment() {
    private lateinit var binding: FragmentDetailUserBinding
    private lateinit var favoriteUserEntity: FavoriteUser
    private var isFavoriteUser: Boolean? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentDetailUserBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity() as AppCompatActivity
        val login = arguments?.getString(USERNAME)

        //view pager 2
        val sectionsPagerAdapter = SectionsPagerAdapter(activity,login)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter

        //tab layout
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager){tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        val actionBar = activity.supportActionBar
        actionBar?.elevation = 0f

        //instantiate view model
        val factoryRepository: FactoryRepository = FactoryRepository.getInstance(requireActivity())
        val factoryMain = FactoryMain(requireContext())
        val favoriteViewModel: FavoriteViewModel by viewModels(){factoryRepository}
        val followViewModel: FollowViewModel by viewModels(){factoryMain}
        val fab = binding.fab

        if (arguments != null) {
            val login = arguments?.getString(USERNAME)
            followViewModel.getDetailUser(login.toString())
            followViewModel.detailUser.observe(viewLifecycleOwner){user ->
                getDetailUserData(user)
                addToFavoriteUser(user)
            }
            followViewModel.isLoading.observe(viewLifecycleOwner) {
                showLoading(it)
            }
            favoriteViewModel.isFavoriteUsers(login.toString()).observe(viewLifecycleOwner){
                isFavoriteUser = it
                if(it){
                    binding.fab.setImageDrawable(ContextCompat.getDrawable(fab.context,R.drawable.ic_baseline_favorite_24))

                }else{
                    binding.fab.setImageDrawable(ContextCompat.getDrawable(fab.context,R.drawable.ic_baseline_favorite_border_24))
                }
            }
        }
    }

    private fun addToFavoriteUser(data: DetailUserResponse){
        binding.fab.setOnClickListener{
            val favoriteUserEntity = FavoriteUser(data.id.toString(),data.login,data.avatarUrl)

            val factoryRepository: FactoryRepository = FactoryRepository.getInstance(requireActivity())
            val favoriteViewModel: FavoriteViewModel by viewModels(){factoryRepository}

            if (isFavoriteUser==true){
                Toast.makeText(requireContext(), "User dihapus dari favorite!", Toast.LENGTH_SHORT).show()
                favoriteViewModel.deleteFavoriteUser(favoriteUserEntity)
            }
            else{
                Toast.makeText(requireContext(), "User ditambahkan ke favorite!", Toast.LENGTH_SHORT).show()
                favoriteViewModel.insertFavoriteUser(favoriteUserEntity)
            }
        }
    }


    private fun getDetailUserData(data: DetailUserResponse) {
        Glide.with(this)
            .load(data.avatarUrl)
            .circleCrop()
            .into(binding.ivAvatarUser)
        with(binding){
            tvLoginUser.text = data.login
            tvNameUser.text = data.name
            tvFollowers.text = resources.getString(R.string.setFollowers, data.followers)
            tvFollowing.text = resources.getString(R.string.setFollowing, data.following)
        }
    }
    private fun showLoading(state: Boolean) { binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE }


    companion object {
        const val TAG = "SearchUserFragment"
        const val USERNAME = "username"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )
    }

}
