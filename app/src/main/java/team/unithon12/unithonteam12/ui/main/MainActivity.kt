package team.unithon12.unithonteam12.ui.main

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import team.unithon12.unithonteam12.R
import team.unithon12.unithonteam12.data.model.Script
import team.unithon12.unithonteam12.ui._base.BaseActivity
import team.unithon12.unithonteam12.ui.adapter.ScriptListAdapter
import team.unithon12.unithonteam12.ui.speech.SpeechActivity

class MainActivity : BaseActivity() {

    override val layoutResId = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = ScriptListAdapter(listOf(Script("스크립트 제목", "내용", "2018/2/4 "))) {
            startActivity<SpeechActivity>()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
    }

}
