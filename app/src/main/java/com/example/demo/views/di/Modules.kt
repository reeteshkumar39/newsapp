package com.example.demo.views.di

import androidx.room.Room
import com.example.demo.views.db.ArrticleRoomDatabase
import com.example.demo.views.network.ApiInterface
import com.example.demo.views.repositories.NewsFeedRepo
import com.example.demo.views.utils.ItemDecorator
import com.example.demo.views.utils.retrofitBuilder
import com.example.demo.views.utils.retrofitHttpClient
import com.example.demo.views.view.fragments.bookmark.BookmarksViewModel
import com.example.demo.views.view.fragments.dashboard.DashboardViewModel
import com.example.demo.views.view.fragments.details.DetailsViewModel
import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.Interceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit

val viewModelModule: Module = module {
    viewModel { DashboardViewModel(repository = get()) }
    viewModel { DetailsViewModel() }
    viewModel { BookmarksViewModel(repository = get()) }
}

val apiModule: Module = module {
    single(createdAtStart = false) { get<Retrofit>().create(ApiInterface::class.java)}
}

val itemDecorator: Module = module {
    factory { ItemDecorator(0f, 0f, 36f, 0f) }
}
val repositoryModule = module {
    single { NewsFeedRepo(get(), get()) }
}

val dbModule = module {
    single { Room.databaseBuilder(get(), ArrticleRoomDatabase::class.java, "article_database.db").build() }
    single { get<ArrticleRoomDatabase>().getDao() }
    single { get<ArrticleRoomDatabase>().getHistoryDao() }
}

val retrofitModule = module {
    single { Cache(androidApplication().cacheDir, 10L * 1024 * 1024) }
    single { GsonBuilder().create() }
    single { retrofitHttpClient() }
    single { retrofitBuilder() }
    single {
        Interceptor { chain ->
            chain.proceed(chain.request().newBuilder().apply {
                header("Accept", "application/json")
            }.build())
        }
    }
}

