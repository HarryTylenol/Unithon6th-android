package team.unithon12.unithonteam12.ui.speech

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import com.bumptech.glide.Glide
import com.github.pwittchen.swipe.library.rx2.Swipe
import com.github.pwittchen.swipe.library.rx2.SwipeEvent
import com.jakewharton.rxbinding2.widget.RxSeekBar
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_speech.*
import kotlinx.android.synthetic.main.layout_speech.*
import org.jetbrains.anko.info
import team.unithon12.unithonteam12.R
import team.unithon12.unithonteam12.data.RoomHelper
import team.unithon12.unithonteam12.data.SocketManager
import team.unithon12.unithonteam12.data.SpeechRecognitionManager
import team.unithon12.unithonteam12.ext.isVisible
import team.unithon12.unithonteam12.ui._base.BaseActivity
import team.unithon12.unithonteam12.util.UserInfo


class SpeechActivity : BaseActivity(), SpeechRecognitionManager.SpeechListener {

    companion object {
        const val KEY_ENABLED = "enabled"
    }

    private val swipe: Swipe by lazy { Swipe() }
    lateinit var disposable: Disposable

    private val srm: SpeechRecognitionManager by lazy { SpeechRecognitionManager(this) }
    private fun setupSwipeListener() {
        disposable = swipe.observe()
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {

                when {
                    it == SwipeEvent.SWIPED_DOWN -> {
                        SocketManager.down()
                        info("Swipe : $it")
                    }
                    it == SwipeEvent.SWIPED_UP -> {
                        SocketManager.up()
                        info("Swipe : $it")
                    }
                }
            }
    }

    override val layoutResId = R.layout.activity_speech
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            Glide.with(this).load(R.raw.pulse_motion_graphics).into(iv_eq_view)
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        }
        iv_eq_view.isVisible(false)
        btn_play.setOnClickListener {
            if (!srm.isRunning) {
                btn_play.setImageResource(R.drawable.btn_pause)
                srm.start()
                iv_eq_view.isVisible(true)
            } else {
                btn_play.setImageResource(R.drawable.btn_play)
                srm.stop()
                iv_eq_view.isVisible(false)
            }
        }
        btn_stop.setOnClickListener {
            srm.stop();
            onBackPressed()
        }

        RxSeekBar.changeEvents(sizeSeekBar).subscribe { SocketManager.size(it.view().progress) }
        RxSeekBar.changeEvents(spaSeekBar).subscribe { SocketManager.spa(it.view().progress) }
        RxSeekBar.changeEvents(tspaSeekBar).subscribe { SocketManager.tspa(it.view().progress) }

        if (intent.getBooleanExtra(KEY_ENABLED, false)) {
            container_sync.isVisible(false)
            container_speech.isVisible(true)
        } else {
            container_sync.isVisible(true)
            container_speech.isVisible(false)
        }

        disposable = swipe.observe().subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (container_sync.visibility == View.VISIBLE) return@subscribe

                when {
                    it == SwipeEvent.SWIPED_DOWN -> SocketManager.down()
                    it == SwipeEvent.SWIPED_UP -> SocketManager.up()
                }
            }
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        swipe.dispatchTouchEvent(event)
        return super.dispatchTouchEvent(event)
    }

    override fun onPause() {
        super.onPause()
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose()
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

    override fun onDestroy() {
        super.onDestroy()
    }

}
