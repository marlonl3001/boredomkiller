package br.com.mdr.boredomkiller.di

import androidx.room.Room
import br.com.mdr.boredomkiller.data.dao.UserDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            UserDatabase::class.java,
            "user_database"
        ).build()
    }

    single {
        val database = get<UserDatabase>()
        database.getRepositoryDAO()
    }
}