package com.example.pinboard.data.api

import com.example.pinboard.model.PinModel
import io.reactivex.Single
import retrofit2.http.GET

interface ApiService {

	@GET("raw/wgkJgazE")
	fun getPins(): Single<List<PinModel>>

}