package com.i.video.caller.video

sealed interface VideoCallAction {
    data object OnDisconnectClick : VideoCallAction
    data object JoinCall : VideoCallAction
}