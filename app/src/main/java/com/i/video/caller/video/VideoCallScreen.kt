package com.i.video.caller.video

import android.Manifest
import android.os.Build
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.getstream.video.android.compose.permission.rememberCallPermissionsState
import io.getstream.video.android.compose.ui.components.call.activecall.CallContent
import io.getstream.video.android.compose.ui.components.call.controls.actions.DefaultOnCallActionHandler
import io.getstream.video.android.core.call.state.LeaveCall

@Composable
fun VideoCallScreen(state: VideoCallState, onAction: (VideoCallAction) -> Unit) {
    when {
        state.errorMessage != null -> Box(modifier = Modifier.fillMaxSize().padding(16.dp), contentAlignment = Alignment.Center) {
            Text(text = state.errorMessage, color = MaterialTheme.colorScheme.error, textAlign = TextAlign.Center)
        }

        state.callState == CallState.JOINING -> Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "... joining ...")
        }

        else -> {
            val basePermissions = listOf(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)
            val bluetoothConnectPermission = if (Build.VERSION.SDK_INT >= 31) listOf(Manifest.permission.BLUETOOTH_CONNECT) else emptyList()
            val notificationPermission = if (Build.VERSION.SDK_INT >= 33) listOf(Manifest.permission.POST_NOTIFICATIONS) else emptyList()
            val context = LocalContext.current
            CallContent(
                call = state.call,
                modifier = Modifier.fillMaxSize(),
                permissions = rememberCallPermissionsState(
                    call = state.call,
                    permissions = basePermissions + bluetoothConnectPermission + notificationPermission,
                    onPermissionsResult = { permissions ->
                        if (permissions.values.contains(false)) {
                            Toast.makeText(context, "Please grant all permissions to use this app", Toast.LENGTH_LONG).show()
                        } else onAction(VideoCallAction.JoinCall)
                    }
                ),
                onCallAction = { action ->
                    if (action == LeaveCall) onAction(VideoCallAction.OnDisconnectClick)
                    DefaultOnCallActionHandler.onCallAction(state.call, action)
                },
                onBackPressed = { onAction(VideoCallAction.OnDisconnectClick) }
            )
        }
    }
}