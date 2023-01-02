package br.com.mdr.boredomkiller.di

import br.com.mdr.boredomkiller.domain.MainUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { MainUseCase(get()) }
}