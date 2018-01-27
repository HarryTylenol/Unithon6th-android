package team.unithon12.unithonteam12.ui.main

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.info
import team.unithon12.unithonteam12.R
import team.unithon12.unithonteam12.data.SocketManager
import team.unithon12.unithonteam12.data.model.Script
import team.unithon12.unithonteam12.ui._base.BaseActivity
import team.unithon12.unithonteam12.ui.adapter.ScriptListAdapter

class MainActivity : BaseActivity() {

    override val layoutResId = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = ScriptListAdapter(listOf(Script("스크립트 제목", "내용", "2018/2/4 ")))
        SocketManager.connect {
            info(it)
            SocketManager.emmit("email", "아무거나")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        SocketManager.close()
    }

}
