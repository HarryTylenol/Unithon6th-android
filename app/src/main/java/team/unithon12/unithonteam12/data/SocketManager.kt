package team.unithon12.unithonteam12.data

import android.util.Log
import io.socket.client.IO
import io.socket.client.Socket
import team.unithon12.unithonteam12.constant.ServerConst

object SocketManager {

    private val socket: Socket by lazy {
        IO.socket(
                ServerConst.URL,
                IO.Options().apply {
//                    port = ServerConst.PORT
                }
        ).apply {
            on(Socket.EVENT_CONNECT, {
                Log.d("EVENT_CONNECT", "CONNECT")
            }).on(Socket.EVENT_DISCONNECT, {
                Log.d("EVENT_DISCONNECT", "DISCONNECT")
            }).on(Socket.EVENT_ERROR, {
                Log.d("EVENT_ERROR", "${it.firstOrNull()} s")
            }).on(Socket.EVENT_CONNECT_ERROR, {
                Log.d("EVENT_CONNECT_ERROR", "${it.firstOrNull()} s")
            }).on(Socket.EVENT_RECONNECT_ERROR, {
                Log.d("EVENT_RECONNECT_ERROR", "${it.firstOrNull()} s")
            }).on(Socket.EVENT_MESSAGE, {
                Log.d("EVENT_MESSAGE", "${it.firstOrNull()} s")
            })
        }
    }

    val isConnected get() = socket.connected()

    fun connect(callback: (Array<out Any>) -> Unit) {
        socket.open()
        socket.on(Socket.EVENT_CONNECT, callback)
    }

    fun listen(eventName: String, callback: (Array<out Any>) -> Unit) {
        socket.on(eventName, callback)
    }

    fun emmit(eventName: String, obj: Any) {
        socket.emit(eventName, obj)
    }

    fun close() {
        socket.close()
    }
}
