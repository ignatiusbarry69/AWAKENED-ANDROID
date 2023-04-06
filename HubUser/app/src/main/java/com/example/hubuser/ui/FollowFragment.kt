package com.example.hubuser.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hubuser.R
import com.example.hubuser.background.FactoryMain
import com.example.hubuser.background.FollowViewModel
import com.example.hubuser.databinding.FragmentFollowBinding
import com.example.hubuser.response.ItemsItem


class FollowFragment : Fragment() {
    private lateinit var binding: FragmentFollowBinding
    private lateinit var position: String
    private lateinit var username: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factoryMain: FactoryMain = FactoryMain(requireContext())
        val followViewModel: FollowViewModel by viewModels(){factoryMain}

        arguments?.let {
            position = it.getInt(POSITION).toString()
            username = it.getString(USERNAME).toString()

        }
        if (this.position == "1") {
            followViewModel.findFollowers(username)
            followViewModel.followers.observe(viewLifecycleOwner){followers ->

                setViewedUser(followers)
            }

            followViewModel.isLoading.observe(viewLifecycleOwner) {
                showLoading(it)
            }

        } else {
            followViewModel.findFollowing(username)
            followViewModel.following.observe(viewLifecycleOwner) { following ->

                setViewedUser(following)
            }
            followViewModel.isLoading.observe(viewLifecycleOwner) {
                showLoading(it)
            }
        }
    }

    private fun setViewedUser(usersData: ArrayList<ItemsItem>) {
        binding.rvFollow.layoutManager = LinearLayoutManager(requireContext())
        val adapter = ListGitHubAdapter(usersData)
        binding.rvFollow.adapter = adapter

        adapter.setOnItemClickCallback(object : ListGitHubAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ItemsItem) {
                val detailCategoryFragment = DetailUserFragment()
                val bundle = Bundle()
                bundle.putString(DetailUserFragment.USERNAME, data.login)
                detailCategoryFragment.arguments = bundle
                val fragmentManager = parentFragmentManager
                fragmentManager.commit {
                    addToBackStack(null)
                    replace(
                        R.id.frame_container,
                        detailCategoryFragment,
                        DetailUserFragment::class.java.simpleName
                    )
                }
            }
        })
    }

    private fun showLoading(state: Boolean) { binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE }


    companion object {
        const val TAG = "FollowFragment"
        const val POSITION = "position"
        const val USERNAME = "username"
    }
}