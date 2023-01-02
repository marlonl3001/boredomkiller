package br.com.mdr.boredomkiller.data.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.mdr.base.domain.UserActivity

@Database(entities = [UserActivity::class], version = 1, exportSchema = false)
abstract class UserDatabase: RoomDatabase() {
    abstract fun getRepositoryDAO(): RepositoryDAO
}