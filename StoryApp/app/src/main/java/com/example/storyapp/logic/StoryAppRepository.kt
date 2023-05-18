package com.example.storyapp.logic

import androidx.paging.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.storyapp.logic.repository.db.StoryDatabase
import com.example.storyapp.logic.repository.preference.UserModel
import com.example.storyapp.logic.repository.preference.UserPreference
import com.example.storyapp.logic.repository.retrofit.ApiService
import com.example.storyapp.logic.repository.retrofit.responses.*
import com.example.storyapp.logic.utils.AppExecutors
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoryAppRepository private constructor(
    private val apiService: ApiService,
    private val preference: UserPreference,
    private val appExecutors: AppExecutors,
    private val database: StoryDatabase
) {
    fun getPref(): UserPreference = preference

    fun logout() {
        appExecutors.diskIO.execute{
            preference.logout()
        }
    }

    fun uploadStory2(token: String, desc: RequestBody, file: MultipartBody.Part, lat: RequestBody?=null, lon: RequestBody?=null): LiveData<Result<Boolean>> {
        val result = MutableLiveData<Result<Boolean>>()
        result.value = Result.Loading
        val client = apiService.uploadStory2(token, desc, file, lat, lon)
        client.enqueue(object : Callback<PushResponse> {
            override fun onResponse(
                call: Call<PushResponse>,
                response: Response<PushResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null && !responseBody.error) {
                        result.value = Result.Success(true)
                    }
                } else {
                    result.value = Result.Error("err1")
                }
            }

            override fun onFailure(call: Call<PushResponse>, t: Throwable) {
                result.value = Result.Error("err2")
            }
        })
        return result
    }

    fun uploadStory(token: String, desc: RequestBody, file: MultipartBody.Part): LiveData<Result<Boolean>> {
        val result = MutableLiveData<Result<Boolean>>()
        result.value = Result.Loading
        val client = apiService.uploadStory(token, desc, file)
        client.enqueue(object : Callback<PushResponse> {
            override fun onResponse(
                call: Call<PushResponse>,
                response: Response<PushResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null && !responseBody.error) {
                        result.value = Result.Success(true)
                    }
                } else {
                    result.value = Result.Error("err1")
                }
            }

            override fun onFailure(call: Call<PushResponse>, t: Throwable) {
                result.value = Result.Error("err2")
            }
        })
        return result
    }

    fun register(name: String, email: String, pass: String): LiveData<Result<Boolean>> {
        val result = MutableLiveData<Result<Boolean>>()
        result.value = Result.Loading
        val client = apiService.registerUser(name, email, pass)
        client.enqueue(object : Callback<PushResponse> {
            override fun onResponse(call: Call<PushResponse>, response: Response<PushResponse>) {
                if (response.isSuccessful) {
                    result.value = Result.Success(true)
                } else {
                    result.value = Result.Error("err1")
                }
            }

            override fun onFailure(call: Call<PushResponse>, t: Throwable) {
                result.value = Result.Error("err2")
            }
        })
        return result
    }

    fun loginCheck(email: String, pass: String): LiveData<Result<Boolean>> {
        val result = MutableLiveData<Result<Boolean>>()
        result.value = Result.Loading
        val client = apiService.loginUser(email, pass)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    appExecutors.diskIO.execute {
                        val userModel = UserModel(
                            responseBody?.loginResult?.name,
                            responseBody?.loginResult?.token, email, pass, true
                        )
                        preference.setUser(userModel)
                    }
                    result.value = Result.Success(true)
                } else {
                    result.value = Result.Error("err1")
                }
            }
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                result.value = Result.Error("err2")
            }
        })
        return result
    }



    fun getListStory(): LiveData<PagingData<ListStoryItem>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 6
            ),
            remoteMediator = ListStoryRemoteMediator(database,apiService,preference),
            pagingSourceFactory = {
                database.storyDao().getAllStory()
            }
        ).liveData
    }

    fun getLocation(token: String, location: Int? = 0): LiveData<Result<List<ListStoryItem>>> {
        val result = MutableLiveData<Result<List<ListStoryItem>>>()
        apiService.getLocation("Bearer $token", location)
            .enqueue(object : Callback<ListStoryResponse> {
                override fun onResponse(
                    call: Call<ListStoryResponse>,
                    response: Response<ListStoryResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null && !responseBody.error) {
                            result.value = Result.Success(responseBody.listStory)
                        }
                    }
                }
                override fun onFailure(call: Call<ListStoryResponse>, t: Throwable) {
                    result.value = Result.Error("error")
                }
            })
        return result
    }

    companion object {
        @Volatile
        private var instance: StoryAppRepository? = null
        fun getInstance(
            apiService: ApiService,
            preference: UserPreference,
            appExecutors: AppExecutors,
            database: StoryDatabase
        ): StoryAppRepository =
            instance ?: synchronized(this) {
                instance ?: StoryAppRepository(apiService, preference,appExecutors,database)
            }.also { instance = it }
    }
}