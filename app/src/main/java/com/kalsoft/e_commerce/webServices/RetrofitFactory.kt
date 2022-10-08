package com.kalsoft.e_commerce.webServices

import com.fictivestudios.imdfitness.webServices.Services
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class RetrofitFactory {
    companion object {
        fun getHeader(): OkHttpClient {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            return OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addNetworkInterceptor(
                    object : Interceptor {
                        @Throws(IOException::class)
                        override fun intercept(chain: Interceptor.Chain): Response {
                            var request: Request? = null

                            val original = chain.request()
                            // Request customization: add request headers
                            val requestBuilder = original.newBuilder().addHeader(
                                "Authorization",
                                "Bearer ${"token"}"
                            ).addHeader("Accept", "application/json")
                            request = requestBuilder.build()

                            return chain.proceed(request)
                        }
                    })
                .build()
        }

        val retrofit = Retrofit.Builder()
            .baseUrl("BaseUrl")
            .client(getHeader())
            .addConverterFactory(GsonConverterFactory.create())
            .build()


        val service = retrofit.create(Services::class.java)
        /*fun getStripe(): OkHttpClient {
               val interceptor = HttpLoggingInterceptor()
               interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
               return OkHttpClient.Builder()
                   .addInterceptor(interceptor)
                   .addNetworkInterceptor(
                       object : Interceptor {
                           @Throws(IOException::class)
                           override fun intercept(chain: Interceptor.Chain): Response {
                               var request: Request? = null

                               val original = chain.request()

                               val requestBuilder = original.newBuilder().addHeader(
                                   "Authorization",
                                   "Bearer ${StripeSecretKey}"
                               )
                                   .addHeader("Accept", "application/json")
                               request = requestBuilder.build()

                               return chain.proceed(request)
                           }
                       })
                   .build()
           }

           val retrofitStripe = Retrofit.Builder()
               .baseUrl(StripeBaseUrl)
               .client(getStripe())
               .addConverterFactory(GsonConverterFactory.create())
               .build()

           val serviceStripe = retrofitStripe.create(Services::class.java)*/
    }
}