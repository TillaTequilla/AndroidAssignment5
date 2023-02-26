package com.androidAssignment5.data.remote

import io.reactivex.Single
import retrofit2.http.*

interface AppApi {
    @POST("./users")
    fun registerUser(@Body request: RegisterRequest): Single<RegisterResponse>

    @GET("users/{userId}/contacts")
    fun getUserContacts(
        @Path("userId") userId: String,
        @Header("Authorization") accessToken: String,
    ): Single<GetUserContactsResponse>

    @POST("./login")
    fun loginUser(@Body request: LoginRequest): Single<LoginResponse>

    @GET("users/{userId}")
    fun getUser(
        @Path("userId") userId: String,
        @Header("Authorization") accessToken: String,
    ): Single<GetUserResponse>

    @GET("./users")
    fun getAllUsers(
        @Header("Authorization") accessToken: String
    ): Single<AllUsersResponse>

    @PUT("users/{userId}/contacts")
    fun addContact(
        @Path("userId") userId: String,
        @Header("Authorization") accessToken: String,
        @Body request: AddContactRequest
    ): Single<GetUserContactsResponse>

}