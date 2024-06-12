package com.i.video.caller.connect

data class ConnectState(
    val name: String = "",
    val isConnected: Boolean = false,
    val errorMessage: String? = null
)
