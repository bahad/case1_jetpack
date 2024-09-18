package com.example.coffeeapp.di
import com.example.coffeeapp.data.api.CoffeeApi
import com.example.coffeeapp.presentation.SharedViewModel
import com.example.coffeeapp.domain.repository.CoffeeRepository
import com.example.coffeeapp.domain.repository.CoffeeRepositoryImpl
import com.example.myapplication.presentation.coffee_detail.CoffeeDetailViewModel
import com.example.myapplication.presentation.coffee_list.CoffeeListViewModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    // Provide Gson
    single<Gson> { GsonBuilder().create() }

    // Provide OkHttpClient
    single<OkHttpClient> {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    // Provide Retrofit and CoffeeApi
    single<CoffeeApi> {
        Retrofit.Builder()
            .baseUrl("https://fake-coffee-api.vercel.app")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create(get()))
            .build()
            .create(CoffeeApi::class.java)
    }

    // Provide CoffeeRepository
    single<CoffeeRepository> { CoffeeRepositoryImpl(get()) }

    // Provide ViewModels
    viewModel { CoffeeListViewModel(get()) }
    viewModel { CoffeeDetailViewModel(get()) }
    viewModel { SharedViewModel(get()) } // If you're using the SharedViewModel
}