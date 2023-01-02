package br.com.mdr.boredomkiller.data

import br.com.mdr.base.domain.UserActivityResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ActivitiesApi {

    @GET("activity")
    suspend fun getActivity(@Query("type") activityType: String?): UserActivityResponse?

}
