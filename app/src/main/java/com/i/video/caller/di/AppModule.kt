package com.i.video.caller.di

import com.i.video.caller.IVideoCallerApp
import com.i.video.caller.connect.ConnectViewModel
import com.i.video.caller.video.VideoCallViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    factory {
        val app = androidContext().applicationContext as IVideoCallerApp
        app.client
    }
    viewModelOf(::ConnectViewModel)
    viewModelOf(::VideoCallViewModel)
}