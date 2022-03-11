package com.ozlemcali.cyrptourrencycwithretrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ozlemcali.cyrptourrencycwithretrofit.adapter.RecyclerViewAdapter
import com.ozlemcali.cyrptourrencycwithretrofit.model.CryptoModel
import com.ozlemcali.cyrptourrencycwithretrofit.services.CryptoAPI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(),RecyclerViewAdapter.Listener {
    private val Base_URL="https://raw.githubusercontent.com/"
    private var cryptoModels:ArrayList<CryptoModel>?=null
    private var recyclerViewAdapter:RecyclerViewAdapter?=null

    //Disposable->Kullan-at
    private var compositeDisposable:CompositeDisposable?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        compositeDisposable= CompositeDisposable()

        val layoutManager: RecyclerView.LayoutManager=LinearLayoutManager(this)
        recyclerView.layoutManager=layoutManager
        loadData()
    }

    //https://raw.githubusercontent.com/atilsamancioglu/K21-JSONDataSet/master/crypto.json

    private fun loadData() {
        val retrofit = Retrofit.Builder()
            .baseUrl(Base_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(CryptoAPI::class.java)

        compositeDisposable?.add(retrofit.getData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::handleResponse))





        /*
        val service = retrofit.create(CryptoAPI::class.java)
       val call = service.getData()
       call.enqueue(object : Callback<List<CryptoModel>> {
           override fun onFailure(call: Call<List<CryptoModel>>, t: Throwable) {
               t.printStackTrace()
           }

           override fun onResponse(
               call: Call<List<CryptoModel>>,
               response: Response<List<CryptoModel>>
           ) {
               if (response.isSuccessful) {
                   response.body()?.let {
                       cryptoModels = ArrayList(it)
                       cryptoModels?.let {
                           recyclerViewAdapter =
                               RecyclerViewAdapter(it, this@MainActivity)
                               recyclerView.adapter=recyclerViewAdapter
                       }
                   }

               }
           }


       })
       */

    }

    private fun handleResponse(cryptoList:List<CryptoModel>){
        cryptoModels = ArrayList(cryptoList)
        cryptoModels?.let {
            recyclerViewAdapter =
                RecyclerViewAdapter(it, this@MainActivity)
            recyclerView.adapter=recyclerViewAdapter
        }


    }

    override fun onItemClick(cryptoModel: CryptoModel) {
        Toast.makeText(this,"Clicked:${cryptoModel.currency}",Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable?.clear()
    }
}




