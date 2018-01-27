package team.unithon12.unithonteam12.data

import android.support.annotation.WorkerThread
import com.naver.speech.clientapi.*
import com.naver.speech.clientapi.SpeechConfig.EndPointDetectType
import com.naver.speech.clientapi.SpeechConfig.LanguageType
import org.jetbrains.anko.*
import team.unithon12.unithonteam12.constant.NaverClientConst
import team.unithon12.unithonteam12.ext.TAG
import team.unithon12.unithonteam12.ui.speech.SpeechActivity

/**
 * Created by baghyeongi on 2018. 1. 26..
 */


class SpeechRecognitionManager(activity: SpeechActivity) : SpeechRecognitionListener, AnkoLogger {

    interface SpeechListener {
        fun onResult(text: String)
    }

    private var listener: SpeechListener = activity
    private val recognizer: SpeechRecognizer by lazy { SpeechRecognizer(activity, NaverClientConst.CLIENT_ID) }

    private var isStop = false
    val isRunning get() = recognizer.isRunning

    fun init() {
        recognizer.initialize()
    }

    fun release() {
        recognizer.release()
    }

    fun stop() {
        isStop = true
        recognizer.stop()
    }

    fun start() {
        try {
            isStop = false
            recognizer.recognize(SpeechConfig(LanguageType.KOREAN, EndPointDetectType.AUTO))
            recognizer.setSpeechRecognitionListener(this)
        }
        catch (e: SpeechRecognitionException) {
            e.printStackTrace()
        }
    }

    @WorkerThread override fun onPartialResult(partialResult: String?) {
        doAsync {
            uiThread {
                partialResult?.let { listener.onResult(it) }
            }
        }
    }

    @WorkerThread override fun onError(errorCode: Int) = warn("$TAG onError : $errorCode")
    @WorkerThread override fun onResult(finalResult: SpeechRecognitionResult?) = Unit

    @WorkerThread override fun onReady() = info("$TAG onReady")
    @WorkerThread override fun onEndPointDetected() = info("$TAG onEndPointDetected")
    @WorkerThread override fun onInactive() = if (!isStop) start() else info("$TAG onInactive")
    @WorkerThread override fun onRecord(speech: ShortArray) = Unit
    @WorkerThread override fun onEndPointDetectTypeSelected(epdType: SpeechConfig.EndPointDetectType?) = info("$TAG onEndPointDetectTypeSelected : $epdType")

}