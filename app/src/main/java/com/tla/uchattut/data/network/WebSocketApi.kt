package com.tla.uchattut.data.network

import android.util.Log
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.URI
import kotlin.coroutines.CoroutineContext


object WebSocketApi : CoroutineScope {

    private const val LOG_WEB_SOCKET = "WebsocketUchatTut"
    // private const val WS_URL = "wss://uchattut.herokuapp.com/ws/chat/1/"


    override val coroutineContext: CoroutineContext
        get() = SupervisorJob() + Dispatchers.IO

    private lateinit var webSocketClient: WebSocketClient

    private val channel = BroadcastChannel<String>(5)

    fun observeMessages(): Flow<String> =
        channel.asFlow()

    fun connectWebSocket(id: Int) {
        val uri = URI("ws://uchattut.herokuapp.com/ws/chat/$id/")

        webSocketClient = object : WebSocketClient(uri) {
            override fun onOpen(serverHandshake: ServerHandshake) {
                Log.i(LOG_WEB_SOCKET, "Opened")
            }

            override fun onMessage(message: String) {
                Log.i(LOG_WEB_SOCKET, "Received message: $message")
                receiveMessage(message)
            }

            override fun onClose(code: Int, reason: String, remote: Boolean) {
                Log.i(LOG_WEB_SOCKET, "Closed $reason")
            }

            override fun onError(e: Exception) {
                Log.i(LOG_WEB_SOCKET, "Error " + e.message)
            }
        }
        webSocketClient.connect()
    }

    private fun receiveMessage(message: String) = launch {
        channel.send(message)
    }

    fun sendMessage(message: String) {
        webSocketClient.send(message)
    }
}