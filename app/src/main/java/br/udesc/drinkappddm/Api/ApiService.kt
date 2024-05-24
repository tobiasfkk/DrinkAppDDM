package br.udesc.drinkappddm.Api

import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("/validade/payment")
    suspend fun getPaymentValidation(): Response<String>
}