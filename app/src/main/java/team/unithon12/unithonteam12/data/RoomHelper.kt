package team.unithon12.unithonteam12.data

import android.content.Intent
import android.util.Log
import team.unithon12.unithonteam12.App
import team.unithon12.unithonteam12.ui.main.MainActivity
import team.unithon12.unithonteam12.ui.speech.SpeechActivity

object RoomHelper {

    var currentId: Long? = null
        private set

    fun appJoin(id: Long) { // click시에만 호출
        currentId = id
        App.context?.startActivity(Intent(App.context, SpeechActivity::class.java).putExtra(SpeechActivity.KEY_ENABLED, false).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP))
        SocketManager.appJoin(id)
    }

    fun leave(id: Long? = currentId, manual: Boolean) {
        if (currentId == id && currentId != null) {
            App.context?.startActivity(Intent(App.context, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP))
            if (manual)
                SocketManager.leave(currentId!!)
            currentId = null
        }
    }

    fun webJoin(id: Long) {
        Log.e("WebJoin", "$id : $currentId")
        currentId = id
        App.context?.startActivity(Intent(App.context, SpeechActivity::class.java).putExtra(SpeechActivity.KEY_ENABLED, true).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP))
    }
//
//
//    앱에 들어왔을때 바로 커넥
//    1. 조인이 들어옴 (웹이 먼저 들어가 있을떄)
//    2. 들어간 뒤 조인이 들어옴(웹이 들어가 있지 않을 때)
//    3. 현재 조인된 스크립트 아이디와 들어온 스크립트 아이디가 다를때 이동.
//    4. 같다면 가만히 있음.
//    3. 앱에서 나갈 때
//    4. 웹에서 나갈 때

}
