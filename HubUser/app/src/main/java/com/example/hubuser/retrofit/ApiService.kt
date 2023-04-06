package com.example.hubuser.retrofit

import com.example.hubuser.response.DetailUserResponse
import com.example.hubuser.response.GithubResponse
import com.example.hubuser.response.ItemsItem
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
     @GET("search/users")
    fun getUser(
        @Query("q") username: String
    ): Call<GithubResponse>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String): Call<ArrayList<ItemsItem>>

    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String): Call<ArrayList<ItemsItem>>

}
