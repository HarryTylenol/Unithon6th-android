package team.unithon12.unithonteam12.ui.main

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.dip
import org.jetbrains.anko.startActivity
import team.unithon12.unithonteam12.R
import team.unithon12.unithonteam12.data.ApiServiceFactory
import team.unithon12.unithonteam12.data.RoomHelper
import team.unithon12.unithonteam12.data.model.Script
import team.unithon12.unithonteam12.ui.GridLayoutSpacingDecoration
import team.unithon12.unithonteam12.ui._base.BaseActivity
import team.unithon12.unithonteam12.ui.adapter.ScriptListAdapter
import team.unithon12.unithonteam12.ui.speech.SpeechActivity
import java.util.*

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
        }
        swipeRefreshLayout.setOnRefreshListener {
            loadData()
        }
        loadData()
    }

    fun loadData(){
        swipeRefreshLayout.isRefreshing = true
        ApiServiceFactory.apiService.getScripts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    swipeRefreshLayout.isRefreshing = false
                    recyclerview.adapter = ScriptListAdapter(it.toMutableList()) {
                        RoomHelper.appJoin(it.id)
                    }
                },
                {
                    it.printStackTrace()
                    swipeRefreshLayout.isRefreshing = false
                }
            )
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}
