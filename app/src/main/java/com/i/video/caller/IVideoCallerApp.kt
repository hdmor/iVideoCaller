package com.i.video.caller

import android.app.Application
import com.i.video.caller.di.appModule
import io.getstream.video.android.core.StreamVideo
import io.getstream.video.android.core.StreamVideoBuilder
import io.getstream.video.android.model.User
import io.getstream.video.android.model.UserType
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class IVideoCallerApp : Application() {

    private var currentName: String? = null
    var client: StreamVideo? = null

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@IVideoCallerApp)
            modules(appModule)
        }
    }

    fun initVideoClient(name: String) {
        if (client == null || name != currentName) {
            StreamVideo.removeClient()
            currentName = name
            client = StreamVideoBuilder(context = this, apiKey = "nkdu6dpmczvq", user = User(id = name, name = name, type = UserType.Guest)).build()
        }
    }
}