package com.i.video.caller.connect

sealed interface ConnectAction {
    data class OnNameChange(val name:String) : ConnectAction
    data object OnConnectClick : ConnectAction
}