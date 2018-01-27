package team.unithon12.unithonteam12.ui.main

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.dip
import org.jetbrains.anko.startActivity
import team.unithon12.unithonteam12.R
import team.unithon12.unithonteam12.data.model.Script
import team.unithon12.unithonteam12.ui.GridLayoutSpacingDecoration
import team.unithon12.unithonteam12.ui._base.BaseActivity
import team.unithon12.unithonteam12.ui.adapter.ScriptListAdapter
import team.unithon12.unithonteam12.ui.speech.SpeechActivity

class MainActivity : BaseActivity() {

    override val layoutResId = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        recyclerview.apply {
            layoutManager = GridLayoutManager(context, 1)
            addItemDecoration(GridLayoutSpacingDecoration.Builder()
                    .spacing(dip(24))
                    .includeEdge(true)
                    .build())
            adapter = ScriptListAdapter(listOf(Script("투머치토크쇼 by No.61", "이건 제가 LA에 있었을 때 겪었던 이야기입니다. 제가 미국에 왔을 때 아는... 사람이 에이전트 단 한 명밖에 없었습니다. 하지만 지금까지 많은 사람들을 알게 되었고 의미 있고 가치있었어요. 미국은 나에게 기회를 준 곳입니다.", "2018/2/4 "))) {
                startActivity<SpeechActivity>()
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
    }

}
