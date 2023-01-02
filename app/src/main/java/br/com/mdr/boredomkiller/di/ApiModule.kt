package br.com.mdr.boredomkiller.di

import br.com.mdr.boredomkiller.data.ActivitiesApi
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {
    single { get<Retrofit>().create(ActivitiesApi::class.java) }
}