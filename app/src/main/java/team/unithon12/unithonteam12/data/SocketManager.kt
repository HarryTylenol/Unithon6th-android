package team.unithon12.unithonteam12.data

import io.socket.client.IO
import io.socket.client.Socket
import team.unithon12.unithonteam12.constant.ServerConst

/**
 * Created by baghyeongi on 2018. 1. 27..
 */
class SocketManager {

    val socket: Socket by lazy { IO.socket(ServerConst.URL) }
    val isConnected get() = socket.connected()

    fun connect(callback: (Array<out Any>) -> Unit) {
        socket.connect()
        socket.on(Socket.EVENT_CONNECT, callback)
    }

    fun listen(eventName: String, callback: (Array<out Any>) -> Unit) {
        socket.on(eventName, callback)
    }

    fun emmit(eventName: String, obj: Any) {
        socket.emit(eventName, obj)
    }

}