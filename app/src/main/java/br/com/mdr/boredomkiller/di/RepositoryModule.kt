package br.com.mdr.boredomkiller.di

import br.com.mdr.boredomkiller.data.repository.MainRepository
import br.com.mdr.boredomkiller.data.repository.MainRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<MainRepository> { MainRepositoryImpl(get(), get()) }
}