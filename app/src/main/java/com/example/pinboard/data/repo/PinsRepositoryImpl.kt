package com.example.pinboard.data.repo

import com.example.pinboard.data.api.ApiService
import com.example.pinboard.model.PinModel
import io.reactivex.Single
import javax.inject.Inject

class PinsRepositoryImpl @Inject constructor(private val apiService: ApiService) : PinsRepository {

    override fun getPins(): Single<List<PinModel>> {
        return apiService.getPins().map {
            it
        }
    }

}