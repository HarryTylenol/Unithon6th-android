package team.unithon12.unithonteam12.ui.speech

import android.content.Intent
import android.os.Bundle
import com.bumptech.glide.Glide
import com.jakewharton.rxbinding2.widget.RxSeekBar
import kotlinx.android.synthetic.main.activity_speech.*
import kotlinx.android.synthetic.main.layout_speech.*
import team.unithon12.unithonteam12.R
import team.unithon12.unithonteam12.data.RoomHelper
import team.unithon12.unithonteam12.data.SocketManager
import team.unithon12.unithonteam12.data.SocketManager.leaveCallback
import team.unithon12.unithonteam12.data.SpeechRecognitionManager
import team.unithon12.unithonteam12.ext.isVisible
import team.unithon12.unithonteam12.ui._base.BaseActivity
import team.unithon12.unithonteam12.util.UserInfo

/**
 * Created by baghyeongi on 2018. 1. 27..
 */
class SpeechActivity : BaseActivity(), SpeechRecognitionManager.SpeechListener {

    companion object {
        const val KEY_ENABLED = "enabled"
    }



    private val srm: SpeechRecognitionManager by lazy { SpeechRecognitionManager(this) }

    override val layoutResId = R.layout.activity_speech
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            Glide.with(this).load(R.raw.pulse_motion_graphics).into(iv_eq_view)
        }
        catch (e: IllegalArgumentException) {
            e.printStackTrace()
        }
        iv_eq_view.isVisible(false)
        btn_play.setOnClickListener {
            if (!srm.isRunning) {
                btn_play.setImageResource(R.drawable.btn_pause)
                srm.start()
                iv_eq_view.isVisible(true)
            }
            else {
                btn_play.setImageResource(R.drawable.btn_play)
                srm.stop()
                iv_eq_view.isVisible(false)
            }
        }
        btn_stop.setOnClickListener { srm.stop(); finish() }

        RxSeekBar.changeEvents(sizeSeekBar).subscribe { SocketManager.size(it.view().progress) }
        RxSeekBar.changeEvents(spaSeekBar).subscribe { SocketManager.spa(it.view().progress) }
        RxSeekBar.changeEvents(tspaSeekBar).subscribe { SocketManager.tspa(it.view().progress) }

        if (intent.getBooleanExtra(KEY_ENABLED, false)) {
            container_sync.isVisible(false)
            container_speech.isVisible(true)
        }
        else {
            container_sync.isVisible(true)
            container_speech.isVisible(false)
        }

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.let {
            setIntent(intent)
            if (intent.getBooleanExtra(KEY_ENABLED, false)) {
                container_sync.isVisible(false)
                container_speech.isVisible(true)
            }
            else {
                container_sync.isVisible(true)
                container_speech.isVisible(false)
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        RoomHelper.leave(manual = true)
    }

    override fun onResult(text: String) {
        if (SocketManager.isConnected && UserInfo.email != null) {
            SocketManager.voice(text)
        }
    }

    override fun onStart() {
        super.onStart()
        srm.init()
        leaveCallback = { finish() }
    }

    override fun onStop() {
        super.onStop()
        srm.release()
        leaveCallback = {}
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}
