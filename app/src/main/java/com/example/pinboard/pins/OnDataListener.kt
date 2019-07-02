package com.example.pinboard.pins

import com.example.pinboard.model.PinModel

interface OnDataListener {

    fun profileClicked(pinModel: PinModel) { }

}