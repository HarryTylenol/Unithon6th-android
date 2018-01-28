package team.unithon12.unithonteam12

import android.app.Application
import android.content.Context
import team.unithon12.unithonteam12.data.SocketManager

class App : Application(){
    companion object {
        var context : Context? = null
    }
    override fun onCreate() {
        super.onCreate()
        context = this
    }

    override fun onTerminate() {
        super.onTerminate()
        SocketManager.close()
    }
}
