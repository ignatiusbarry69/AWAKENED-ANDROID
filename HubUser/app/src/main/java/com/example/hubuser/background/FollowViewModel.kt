package com.example.hubuser.background

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hubuser.response.DetailUserResponse
import com.example.hubuser.response.ItemsItem
import com.example.hubuser.retrofit.ApiConfig
import com.example.hubuser.ui.DetailUserFragment
import com.example.hubuser.ui.SearchUserFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowViewModel(private val context: Context) : ViewModel() {
    private val _followers = MutableLiveData<ArrayList<ItemsItem>>()
    val followers: LiveData<ArrayList<ItemsItem>> = _followers

    private val _following = MutableLiveData<ArrayList<ItemsItem>>()
    val following: LiveData<ArrayList<ItemsItem>> = _following

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _detailUser = MutableLiveData<DetailUserResponse>()
    val detailUser: MutableLiveData<DetailUserResponse> = _detailUser

    fun findFollowers(username:String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowers(username)
        client.enqueue(object : Callback<ArrayList<ItemsItem>> {
            override fun onResponse(call: Call<ArrayList<ItemsItem>>, response: Response<ArrayList<ItemsItem>>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _followers.value = responseBody!!
                    }
                } else {
                    Log.e(SearchUserFragment.TAG, "onFailure: ${response.message()}")
                    Toast.makeText(context,"Something went wrong",Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<ArrayList<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(SearchUserFragment.TAG, "onFailure: ${t.message}")
                Toast.makeText(context,"Network Error",Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun findFollowing(username:String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowing(username)
        client.enqueue(object : Callback<ArrayList<ItemsItem>> {
            override fun onResponse(call: Call<ArrayList<ItemsItem>>, response: Response<ArrayList<ItemsItem>>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _following.value = responseBody!!
                    }
                } else {
                    Log.e(SearchUserFragment.TAG, "onFailure: ${response.message()}")
                    Toast.makeText(context,"Something went wrong",Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<ArrayList<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(SearchUserFragment.TAG, "onFailure: ${t.message}")
                Toast.makeText(context,"Network Error",Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun getDetailUser(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _detailUser.value = responseBody!!
                    } else {
                        Log.e(DetailUserFragment.TAG, "onFailure: ${response.message()}")
                        Toast.makeText(context,"Something went wrong",Toast.LENGTH_SHORT).show()
                    }
                }
            }
            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(DetailUserFragment.TAG, "onFailure: ${t.message}")
                Toast.makeText(context,"Network Error",Toast.LENGTH_SHORT).show()
            }
        })
    }

}