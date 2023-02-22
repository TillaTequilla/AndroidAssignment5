package com.androidAssignment5.data.remote

import io.reactivex.Single
import retrofit2.http.POST

interface AppApi {

    @POST("./users")
    fun registerUser():Single<RegisterResponse>
}