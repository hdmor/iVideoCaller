package com.i.video.caller.connect

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.i.video.caller.IVideoCallerApp

class ConnectViewModel(private val application: Application) : AndroidViewModel(application) {

    var state by mutableStateOf(ConnectState())
        private set

    fun onAction(action: ConnectAction) {
        when (action) {
            ConnectAction.OnConnectClick -> connectToRoom()
            is ConnectAction.OnNameChange -> state = state.copy(name = action.name)
        }
    }

    private fun connectToRoom() {
        state = state.copy(errorMessage = null)
        if (state.name.isBlank()) {
            state = state.copy(errorMessage = "The name can't be blank!")
            return
        }
        (application as IVideoCallerApp).initVideoClient(state.name)
        state = state.copy(isConnected = true)
    }
}