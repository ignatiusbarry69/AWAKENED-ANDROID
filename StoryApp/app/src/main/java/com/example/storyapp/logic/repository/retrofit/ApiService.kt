package com.example.storyapp.logic.repository.retrofit

import com.example.storyapp.logic.repository.retrofit.responses.ListStoryResponse
import com.example.storyapp.logic.repository.retrofit.responses.LoginResponse
import com.example.storyapp.logic.repository.retrofit.responses.PushResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*


interface ApiService {
    @FormUrlEncoded
    @POST("v1/login")
    fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String,
    ): Call<LoginResponse>

    /**
        we can actually use data class as variable instead of using @formurlendcoded and @field
        @POST("v1/login")
        fun loginUser(@Body requestBody: LoginRequest): Call<LoginResponse>
        data class LoginRequest(val email:String, val password: String)
    */

    @GET("v1/stories")
    fun getLocation(
        @Header("Authorization") token: String,
        @Query("location") location: Int? = 0
    ): Call<ListStoryResponse>

    @GET("v1/stories")
    suspend fun getListStory2(
        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): ListStoryResponse

    @FormUrlEncoded
    @POST("v1/register")
    fun registerUser(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
    ): Call<PushResponse>

    @Multipart
    @POST("v1/stories")
    fun uploadStory(
        @Header("Authorization") token: String,
        @Part("description") description: RequestBody,
        @Part file: MultipartBody.Part,
    ): Call<PushResponse>

    @Multipart
    @POST("v1/stories")
    fun uploadStory2(
        @Header("Authorization") token: String,
        @Part("description") description: RequestBody,
        @Part file: MultipartBody.Part,
        @Part("lat") lat: RequestBody?,
        @Part("lon") lon: RequestBody?,
    ): Call<PushResponse>
}