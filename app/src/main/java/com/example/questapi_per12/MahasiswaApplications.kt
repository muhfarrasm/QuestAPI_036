package com.example.questapi_per12

import android.app.Application
import com.example.questapi_per12.dependeciesinjection.AppContainer
import com.example.questapi_per12.dependeciesinjection.MahasiswaContainer

class MahasiswaApplications: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = MahasiswaContainer()
    }
}