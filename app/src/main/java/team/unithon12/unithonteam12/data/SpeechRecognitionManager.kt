package team.unithon12.unithonteam12.data

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.support.annotation.WorkerThread
import com.naver.speech.clientapi.*
import com.naver.speech.clientapi.SpeechConfig.EndPointDetectType
import com.naver.speech.clientapi.SpeechConfig.LanguageType
import org.jetbrains.anko.*
import team.unithon12.unithonteam12.constant.NaverClientConst
import team.unithon12.unithonteam12.ext.TAG
import team.unithon12.unithonteam12.ui.main.MainActivity

/**
 * Created by baghyeongi on 2018. 1. 26..
 */


class SpeechRecognitionManager(mainActivity: MainActivity) :
        SpeechRecognitionListener, AnkoLogger, LifecycleObserver {

    interface SpeechListener {
        fun onResult(text: String)
    }

    private var listener: SpeechListener
    private val recognizer: SpeechRecognizer

    private var isStop = false
    val isRunning get() = recognizer.isRunning

    init {
        listener = mainActivity
        recognizer = SpeechRecognizer(mainActivity, NaverClientConst.CLIENT_ID)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun init() = recognizer.initialize()

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun release() = recognizer.release()

    fun stop() {
        isStop = true
        recognizer.stop()
    }

    fun start() {
        try {
            isStop = false
            recognizer.recognize(SpeechConfig(LanguageType.KOREAN, EndPointDetectType.AUTO))
        }
        catch (e: SpeechRecognitionException) {
            e.printStackTrace()
        }
    }

    @WorkerThread override fun onPartialResult(partialResult: String?) = Unit
    @WorkerThread override fun onError(errorCode: Int) = warn("$TAG onError : $errorCode")
    @WorkerThread override fun onResult(finalResult: SpeechRecognitionResult?) {
        doAsync {
            uiThread {
                finalResult?.results?.let {
                    if (it.first().isNotBlank()) listener.onResult(it.first())
                }
            }
        }
    }

    @WorkerThread override fun onReady() = info("$TAG onReady")
    @WorkerThread override fun onEndPointDetected() = info("$TAG onEndPointDetected")
    @WorkerThread override fun onInactive() = if (!isStop) start() else info("$TAG onInactive")
    @WorkerThread override fun onRecord(speech: ShortArray?) = Unit
    @WorkerThread override fun onEndPointDetectTypeSelected(epdType: SpeechConfig.EndPointDetectType?) = info("$TAG onEndPointDetectTypeSelected : $epdType")

}