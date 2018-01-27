package team.unithon12.unithonteam12.ui.speech

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_speech.*
import org.jetbrains.anko.toast
import team.unithon12.unithonteam12.R
import team.unithon12.unithonteam12.data.SpeechRecognitionManager
import team.unithon12.unithonteam12.ui._base.BaseActivity

/**
 * Created by baghyeongi on 2018. 1. 27..
 */
class SpeechActivity : BaseActivity(), SpeechRecognitionManager.SpeechListener {

    private val srm: SpeechRecognitionManager by lazy { SpeechRecognitionManager(this) }
    override val layoutResId = R.layout.activity_speech
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        btn_speech.setOnClickListener {
            when {
                srm.isRunning -> srm.stop()
                else -> srm.start()
            }
        }
    }

    override fun onResult(text: String) {
        toast(text)
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