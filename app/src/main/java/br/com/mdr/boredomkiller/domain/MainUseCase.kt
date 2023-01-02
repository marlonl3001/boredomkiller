package br.com.mdr.boredomkiller.domain

import br.com.mdr.base.domain.ActivityType
import br.com.mdr.base.domain.UserActivityResponse
import br.com.mdr.base.domain.UserActivity
import br.com.mdr.base.domain.UserActivityStatus
import br.com.mdr.boredomkiller.data.repository.MainRepository
import java.util.*

class MainUseCase(
    private val repository: MainRepository
) {

    suspend fun getActivity(activityType: ActivityType?): UserActivityResponse? {
        val currentType =
            if (activityType == ActivityType.RANDOM)
                null
            else
                activityType?.type

        return repository.getActivity(currentType)
    }

    suspend fun getActivitiesInProgress(): List<UserActivity> =
        repository.getActivitiesInProgress()

    suspend fun getUserActivities(): List<UserActivity> =
        repository.getAllUserActivities()

    suspend fun addActivity(userActivityResponse: UserActivityResponse, start: Boolean) {
        with(userActivityResponse) {
            val activity =
                UserActivity(
                    key,
                    activity,
                    type,
                    link,
                    if (start)
                        UserActivityStatus.IN_PROGRESS
                    else
                        UserActivityStatus.ADDED
                )

            if (start)
                startActivity(activity)
            else
                saveActivity(activity)
        }
    }

    suspend fun startActivity(userActivity: UserActivity) {
        userActivity.started = Date().time

        if (userActivity.status != UserActivityStatus.IN_PROGRESS)
            userActivity.status = UserActivityStatus.IN_PROGRESS

        if (repository.hasActivitySaved(userActivity.key))
            updateActivity(userActivity)
        else
            saveActivity(userActivity)
    }

    suspend fun finishActivity(userActivity: UserActivity) {
        userActivity.finished = Date().time
        userActivity.status = UserActivityStatus.FINISHED
        updateActivity(userActivity)
    }

    suspend fun giveUpActivity(userActivity: UserActivity) {
        userActivity.status = UserActivityStatus.GIVE_UP
        updateActivity(userActivity)
    }

    private suspend fun saveActivity(userActivity: UserActivity) {
        repository.saveActivity(userActivity)
    }

    private suspend fun updateActivity(userActivity: UserActivity) {
        repository.updateActivity(userActivity)
    }
}