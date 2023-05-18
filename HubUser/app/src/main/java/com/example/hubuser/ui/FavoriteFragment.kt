package com.example.hubuser.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hubuser.R
import com.example.hubuser.background.FactoryRepository
import com.example.hubuser.background.FavoriteViewModel
import com.example.hubuser.databinding.FragmentFavoriteBinding
import com.example.hubuser.response.ItemsItem


class FavoriteFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factoryRepository: FactoryRepository = FactoryRepository.getInstance(requireActivity())
        val favoriteViewModel: FavoriteViewModel by viewModels(){factoryRepository}

        favoriteViewModel.getFavoriteUsers().observe(viewLifecycleOwner){ it ->

            if(it!=null){
                binding.tvNoFavorite.visibility = View.GONE
                val items = arrayListOf<ItemsItem>()

                //yg return getfavorit itu langsung live data, dan itu bntuknya list<Favuser>
                //walaupun bntuknya beda tp isi strukturnya sma dgn response dr githubresponse dgn arraylist<itemsitem>
                //makanya ini di map biar bisa make adapter yg sama, sbenernya bisa aja dibikinin baru
                it.map {
                    val item = ItemsItem(id = it.id.toInt(), login = it.username.toString(), avatarUrl = it.avatarUrl.toString())
                    items.add(item)
                }

                val layoutManager = LinearLayoutManager(requireContext())
                binding.rvFavoriteUser.layoutManager = layoutManager
                binding.rvFavoriteUser.setHasFixedSize(true)
                val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
                binding.rvFavoriteUser.addItemDecoration(itemDecoration)
                val adapter = ListGitHubAdapter(items)
                binding.rvFavoriteUser.adapter = adapter

                adapter.setOnItemClickCallback(object : ListGitHubAdapter.OnItemClickCallback {
                    override fun onItemClicked(data: ItemsItem) {
                        val detailCategoryFragment = DetailUserFragment()
                        val bundle = Bundle()
                        bundle.putString(DetailUserFragment.USERNAME,data.login)
                        detailCategoryFragment.arguments = bundle
                        val fragmentManager = parentFragmentManager
                        fragmentManager.commit{
                            addToBackStack(null)
                            replace(R.id.frame_container, detailCategoryFragment, DetailUserFragment::class.java.simpleName)
                        }
                    }
                })

                if (it.isEmpty()){
                    binding.tvNoFavorite.visibility = View.VISIBLE
                }
            }
        }
    }
}