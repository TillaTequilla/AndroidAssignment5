package com.androidAssignment5.data.api

import com.androidAssignment5.data.remote.*
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
    suspend fun loginUser(@Body request: LoginRequest): LoginResponse

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

    @DELETE("users/{userId}/contacts/{contactId}")
    fun deleteContact(
        @Path("userId") userId: String,
        @Path("contactId") contactId: String,
        @Header("Authorization") accessToken: String,
    ): Single<GetUserContactsResponse>
}