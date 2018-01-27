package team.unithon12.unithonteam12.data

import android.util.Log
import io.socket.client.IO
import io.socket.client.Socket
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.json.JSONArray
import team.unithon12.unithonteam12.constant.ServerConst
import team.unithon12.unithonteam12.util.UserInfo

object SocketManager : AnkoLogger {

    var leaveCallback : () -> Unit = {}

    private val socket: Socket by lazy {
        IO.socket(
            ServerConst.URL,
            IO.Options()
        ).apply {
            on(Socket.EVENT_CONNECT, {
                appConnect()
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
            }).on("WebJoin", {
                val jsonArray = (it.first() as JSONArray)
                val id = jsonArray[0] as Int
                Log.e("WebJoin", id.toString())
                RoomHelper.webJoin(id.toLong())
            }).on("leave", {
                val jsonArray = (it.first() as JSONArray)
                val id = jsonArray[0] as Int
                Log.e("leave", id.toString())
                leaveCallback()
                RoomHelper.leave(id.toLong(), false)
            })
        }
    }

    val isConnected get() = socket.connected()

    fun connect() {
        socket.close()
        socket.open()
    }

    fun appConnect() {
        socket.emit("AppConnect", JSONArray().put(UserInfo.email))
    }

    fun appJoin(currentScriptId: Long) {
        socket.emit("AppJoin", JSONArray().put(currentScriptId).put(UserInfo.email))
    }

    fun leave(currentScriptId: Long) {
        socket.emit("leave", JSONArray().put(currentScriptId).put(UserInfo.email))
    }

    fun voice(data: String) {
        socket.emit("voice", data)
    }

    fun size(size: Int) {
        info("Size emit")
        socket.emit("size", size)
    }

    fun spa(spa: Int) {
        info("Spa emit")
        socket.emit("spa", spa)
    }

    fun tspa(tspa: Int) {
        info("Tspa emit")
        socket.emit("tspa", tspa)
    }

    fun up() {
        socket.emit("up")
    }

    fun down() {
        socket.emit("down")
    }

    fun close() {
        socket.close()
    }
}
