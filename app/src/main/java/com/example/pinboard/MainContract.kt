package com.example.pinboard

import com.example.pinboard.model.PinModel

interface MainContract {

    interface View {

        fun showEventList(pinList: List<PinModel>)

        fun resultFailed(message: String)

        fun showProgress()

        fun hideProgress()
    }

    interface Presenter {
        fun subscribeEvent()
    }

}