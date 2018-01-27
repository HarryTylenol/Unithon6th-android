package team.unithon12.unithonteam12.ui.speech

import android.Manifest
import android.os.Bundle
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_speech.*
import org.jetbrains.anko.toast
import team.unithon12.unithonteam12.R
import team.unithon12.unithonteam12.data.SocketManager
import team.unithon12.unithonteam12.data.SpeechRecognitionManager
import team.unithon12.unithonteam12.ui._base.BaseActivity
import team.unithon12.unithonteam12.util.UserInfo

/**
 * Created by baghyeongi on 2018. 1. 27..
 */
class SpeechActivity : BaseActivity(), SpeechRecognitionManager.SpeechListener {

    private val rxPermission by lazy { RxPermissions(this) }
    private val srm: SpeechRecognitionManager by lazy { SpeechRecognitionManager(this) }

    override val layoutResId = R.layout.activity_speech
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        btn_speech.setOnClickListener {
            checkPermission().subscribe {
                if (it)
                    when {
                        srm.isRunning -> srm.stop()
                        else -> srm.start()
                    }
                else toast("권한을 승인 하셔야 이용 가능합니다.")
            }
        }
        SocketManager.close()
        SocketManager.connect {
            // Connected
        }
    }

    private fun checkPermission() = rxPermission.request(
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    override fun onResult(text: String) {
        if (SocketManager.isConnected && UserInfo.email != null) {
            SocketManager.emmit(UserInfo.email!!, text)
        }
    }

    override fun onStart() {
        super.onStart()
        srm.init()
    }

    override fun onStop() {
        super.onStop()
        srm.release()
    }

}
