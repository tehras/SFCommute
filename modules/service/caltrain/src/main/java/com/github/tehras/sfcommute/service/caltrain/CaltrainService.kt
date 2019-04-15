package com.github.tehras.sfcommute.service.caltrain

import com.github.tehras.sfcommute.service.caltrain.models.RoutesResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CaltrainService {
    // api/v1/routes?operated_by=o-9q9-caltrain
    @GET("/api/v1/routes")
    fun caltrainRoutes(@Query("operated_by") operatedBy: String = "o-9q9-caltrain"): Single<RoutesResponse>
}