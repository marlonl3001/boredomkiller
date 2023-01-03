package br.com.mdr.boredomkiller.data.repository

import br.com.mdr.boredomkiller.data.ActivitiesApi
import br.com.mdr.base.domain.UserActivityResponse
import br.com.mdr.base.domain.UserActivityStatus
import br.com.mdr.boredomkiller.data.dao.UserDAO
import br.com.mdr.base.domain.UserActivity

interface MainRepository {
    suspend fun getActivity(activityType: String? = null): UserActivityResponse?
    suspend fun getActivitiesInProgress(): List<UserActivity>
    suspend fun getAllUserActivities(): List<UserActivity>
    suspend fun saveActivity(userActivity: UserActivity)
    suspend fun updateActivity(userActivity: UserActivity)
    suspend fun hasActivitySaved(key: String): Boolean
}

class MainRepositoryImpl(
    private val api: ActivitiesApi,
    private val dao: UserDAO
): MainRepository {

    override suspend fun getActivity(activityType: String?): UserActivityResponse? =
        api.getActivity(activityType)

    override suspend fun getActivitiesInProgress(): List<UserActivity> =
        dao.getActivitiesByStatus(UserActivityStatus.IN_PROGRESS)

    override suspend fun getAllUserActivities(): List<UserActivity> =
        dao.getMyActivities()

    override suspend fun saveActivity(userActivity: UserActivity) {
        dao.insert(userActivity)
    }

    override suspend fun updateActivity(userActivity: UserActivity) {
        dao.update(userActivity)
    }

    override suspend fun hasActivitySaved(key: String) =
        dao.getActivity(key) != null
}