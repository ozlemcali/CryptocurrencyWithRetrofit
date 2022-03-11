package com.ozlemcali.cyrptourrencycwithretrofit.services


import com.ozlemcali.cyrptourrencycwithretrofit.model.CryptoModel
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET


interface CryptoAPI {

    //Get,Post,Update,Delete
    //https://raw.githubusercontent.com/atilsamancioglu/K21-JSONDataSet/master/crypto.json
    @GET("atilsamancioglu/K21-JSONDataSet/master/crypto.json")
    //fun getData(): Call<List<CryptoModel>>
    fun getData(): Observable<List<CryptoModel>>

}