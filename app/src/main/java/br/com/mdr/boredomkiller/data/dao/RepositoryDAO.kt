package br.com.mdr.boredomkiller.data.dao

import androidx.room.*
import br.com.mdr.base.domain.UserActivityStatus
import br.com.mdr.base.domain.UserActivity

@Dao
interface RepositoryDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(activity: UserActivity)

    @Query("SELECT * FROM UserActivity ORDER BY `key`")
    suspend fun getMyActivities(): List<UserActivity>

    @Query("SELECT * FROM UserActivity WHERE `key` == :key")
    suspend fun getActivity(key: String): UserActivity?

    @Query("SELECT * FROM UserActivity WHERE status == :status ORDER BY `key`")
    suspend fun getActivitiesByStatus(status: UserActivityStatus): List<UserActivity>

    @Update
    suspend fun update(activity: UserActivity)
}