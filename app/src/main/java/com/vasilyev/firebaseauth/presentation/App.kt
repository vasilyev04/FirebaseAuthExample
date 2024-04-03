package com.vasilyev.firebaseauth.presentation

import android.app.Application
import com.vasilyev.firebaseauth.di.components.DaggerApplicationComponent

class App: Application() {
    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}