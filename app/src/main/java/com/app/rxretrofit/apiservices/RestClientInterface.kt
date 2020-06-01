package com.app.rxretrofit.apiservices

import com.app.rxretrofit.apiservices.responsebean.UserResponseBean
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface RestClientInterface {
    //https://reqres.in/api/users?page=1
    @GET("users?")
    fun requestListUsers(@Query("page") username: String?): Observable<UserResponseBean>
}