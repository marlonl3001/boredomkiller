package br.com.mdr.boredomkiller.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.mdr.base.domain.ActivityType
import br.com.mdr.base.domain.UserActivity
import br.com.mdr.base.domain.UserActivityResponse
import br.com.mdr.base.presentation.BaseViewModel
import br.com.mdr.boredomkiller.R
import br.com.mdr.boredomkiller.domain.MainUseCase

open class MainViewModel(
    private val useCase: MainUseCase
) : BaseViewModel() {

    private val _userActivity = MutableLiveData<UserActivityResponse>()
    var userActivity: LiveData<UserActivityResponse> = _userActivity

    private val _activities = MutableLiveData<List<UserActivity>>()
    var activities: LiveData<List<UserActivity>> = _activities

    private val _activityType = MutableLiveData(ActivityType.RANDOM)
    var activityType: LiveData<ActivityType> = _activityType

    fun fetchActivity(activityType: ActivityType? = null) {
        launch(
            block = {
                _userActivity.postValue(useCase.getActivity(
                    activityType ?: _activityType.value))
            }
        )
    }

    fun addActivity(start: Boolean = false) {
        launch(
            block = {
                _userActivity.value?.let {
                    useCase.addActivity(it, start)
                }
            },
            successBlock = {
                _successMessage.postValue(
                    if (start)
                        R.string.activity_started
                    else
                        R.string.activity_added
                )
                fetchActivity()
            }
        )
    }

    fun startActivity(userActivity: UserActivity) {
        launch(
            block = {
                useCase.startActivity(userActivity)
            },
            successBlock = {
                _successMessage.postValue(R.string.activity_started)
                getAllActivities()
            }
        )
    }

    fun finishActivity(userActivity: UserActivity, fetchAll: Boolean = false) {
        launch(
            block = {
                useCase.finishActivity(userActivity)
            },
            successBlock = {
                if (fetchAll)
                    getAllActivities()
                else
                    getActivitiesInProgress()
            }
        )
    }

    fun giveUpActivity(userActivity: UserActivity, fetchAll: Boolean = false) {
        launch(
            block = {
                useCase.giveUpActivity(userActivity)
            },
            successBlock = {
                if (fetchAll)
                    getAllActivities()
                else
                    getActivitiesInProgress()
            }
        )
    }

    fun getActivitiesInProgress() {
        launch(
            block = {
                _activities.postValue(useCase.getActivitiesInProgress())
            }
        )
    }

    fun getAllActivities() {
        launch(
            block = {
                _activities.postValue(useCase.getUserActivities())
            }
        )
    }

    fun sortActivities(activityType: ActivityType) {
        _activityType.postValue(activityType)
        fetchActivity(activityType)
    }

}