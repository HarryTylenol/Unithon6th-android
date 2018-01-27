package team.unithon12.unithonteam12.ui.main

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast
import team.unithon12.unithonteam12.R
import team.unithon12.unithonteam12.data.SpeechRecognitionManager
import team.unithon12.unithonteam12.ui._base.BaseActivity

class MainActivity : BaseActivity(), SpeechRecognitionManager.SpeechListener {

    override val layoutResId = R.layout.activity_main

    private val srm: SpeechRecognitionManager by lazy {
        SpeechRecognitionManager(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(srm)
        speech_button.setOnClickListener {
            when {
                srm.isRunning -> srm.stop()
                else -> srm.start()
            }
        }
    }

    override fun onResult(text: String) {
        toast(text)
    }

}
