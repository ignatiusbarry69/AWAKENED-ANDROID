package com.example.hubuser.ui

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hubuser.*
import com.example.hubuser.background.FactoryDatastore
import com.example.hubuser.background.SettingViewModel
import com.example.hubuser.databinding.FragmentSearchUserBinding
import com.example.hubuser.repository.SettingPreferences
import com.example.hubuser.response.GithubResponse
import com.example.hubuser.response.ItemsItem
import com.example.hubuser.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SearchUserFragment : Fragment() {
    private lateinit var binding: FragmentSearchUserBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentSearchUserBinding.inflate(inflater,container,false)
        setHasOptionsMenu(true)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val switchTheme = binding.switchTheme

        val pref = SettingPreferences.getInstance(requireContext().dataStore)
        val mainViewModel = ViewModelProvider(requireActivity(), FactoryDatastore(pref))[SettingViewModel::class.java]
        mainViewModel.getThemeSettings().observe(requireActivity()
        ) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }

        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            mainViewModel.saveThemeSetting(isChecked)
        }

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvGithubUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        binding.rvGithubUser.addItemDecoration(itemDecoration)

        findUser(DEFAULT_USER)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.option_menu, menu)
        val searchManager = requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                findUser(query)
                searchView.clearFocus()
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.favorite -> {
                val favoriteFragment = FavoriteFragment()
                val fragmentManager = parentFragmentManager
                fragmentManager.commit{
                    addToBackStack(null)
                    replace(R.id.frame_container, favoriteFragment, FavoriteFragment::class.java.simpleName)
                }
                true
            }R.id.switch_theme -> {
                true
            }else -> {
                true
            }
        }
    }

    private fun findUser(username: String) {
        showLoading(true)
        showNoUser(true)
        val client = ApiConfig.getApiService().getUser(username)
        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(call: Call<GithubResponse>, response: Response<GithubResponse>) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        if(responseBody.totalCount == 0){
                            showNoUser(false)
                            setViewedUser(responseBody)
                        }
                        else{
                            showNoUser(true)
                            setViewedUser(responseBody)

                        }
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                    val errorMessage = "Something went wrong"
                    Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                    showNoUser(false)
                }
            }
            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                showLoading(false)
                showNoUser(false)
                Log.e(TAG, "onFailure: ${t.message}")
                val errorMessage = "Network error"
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setViewedUser(usersData: GithubResponse) {

        val listUserResponse = usersData.items
        val adapter = ListGitHubAdapter(listUserResponse)
        binding.rvGithubUser.adapter = adapter

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
    }

    private fun showNoUser(state: Boolean) {binding.tvNoUser.visibility = if (state) View.GONE else View.VISIBLE}

    private fun showLoading(state: Boolean) { binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE }

    companion object {
        const val TAG = "SearchUserFragment"
        const val DEFAULT_USER = "elon musk"
    }
}