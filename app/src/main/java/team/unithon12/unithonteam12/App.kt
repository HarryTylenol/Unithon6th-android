package team.unithon12.unithonteam12

import android.app.Application
import android.content.Context

class App : Application(){
    companion object {
        var context : Context? = null
    }
    override fun onCreate() {
        super.onCreate()
        context = this
    }
}
