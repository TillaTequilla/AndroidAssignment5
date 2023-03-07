package com.androidAssignment5.data.api

import com.androidAssignment5.data.remote.*
import com.androidAssignment5.data.remote.requests.AddContactRequest
import com.androidAssignment5.data.remote.requests.LoginRequest
import com.androidAssignment5.data.remote.requests.RegisterRequest
import com.androidAssignment5.data.remote.responses.*
import retrofit2.http.*

interface AppApi {
    @POST("./users")
    suspend fun registerUser(@Body request: RegisterRequest): RegisterResponse

    @GET("users/{userId}/contacts")
    suspend fun getUserContacts(
        @Path("userId") userId: String,
        @Header("Authorization") accessToken: String,
    ): GetUserContactsResponse

    @POST("./login")
    suspend fun loginUser(@Body request: LoginRequest): LoginResponse

    @GET("users/{userId}")
    suspend fun getUser(
        @Path("userId") userId: String,
        @Header("Authorization") accessToken: String,
    ): GetUserResponse

    @GET("./users")
    suspend fun getAllUsers(
        @Header("Authorization") accessToken: String
    ): AllUsersResponse

    @PUT("users/{userId}/contacts")
    suspend fun addContact(
        @Path("userId") userId: String,
        @Header("Authorization") accessToken: String,
        @Body request: AddContactRequest
    ): GetUserContactsResponse

    @DELETE("users/{userId}/contacts/{contactId}")
    suspend fun deleteContact(
        @Path("userId") userId: String,
        @Path("contactId") contactId: String,
        @Header("Authorization") accessToken: String,
    ): GetUserContactsResponse
}