package com.example.pinboard.data.repo

import com.example.pinboard.model.PinModel
import io.reactivex.Single

interface PinsRepository {

	fun getPins(): Single<List<PinModel>>

}