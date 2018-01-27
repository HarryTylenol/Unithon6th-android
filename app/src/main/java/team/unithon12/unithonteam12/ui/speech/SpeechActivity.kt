package team.unithon12.unithonteam12.ui.speech

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.widget.SeekBar
import com.bumptech.glide.Glide
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_speech.*
import kotlinx.android.synthetic.main.layout_speech.*
import org.jetbrains.anko.toast
import team.unithon12.unithonteam12.R
import team.unithon12.unithonteam12.data.RoomHelper
import team.unithon12.unithonteam12.data.SocketManager
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

        btn_play.setOnClickListener {
            srm.start()
        }
        btn_stop.setOnClickListener {
            srm.stop()
        }

        sizeSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                SocketManager.size(p1)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })

        spaSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                SocketManager.spa(p1)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })

        tspaSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                SocketManager.tspa(p1)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })

        if (intent.getBooleanExtra(KEY_ENABLED, false)) {
            container_sync.isVisible(false)
            container_speech.isVisible(true)
        } else {
            container_sync.isVisible(true)
            container_speech.isVisible(false)
        }

        runOnUiThread {
            try {
                Glide.with(this).load(R.raw.pulse_motion_graphics).into(iv_eq_view)
            } catch (e: IllegalArgumentException) {
                e.printStackTrace()
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.let {
            setIntent(intent)
            if (intent.getBooleanExtra(KEY_ENABLED, false)) {
                container_sync.isVisible(false)
                container_speech.isVisible(true)
            } else {
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
    }

    override fun onStop() {
        super.onStop()
        srm.release()
    }

}
