package team.unithon12.unithonteam12.ui._base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import org.jetbrains.anko.AnkoLogger

/**
 * Created by baghyeongi on 2018. 1. 26..
 */
abstract class BaseActivity : AppCompatActivity(), AnkoLogger {

    abstract val layoutResId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResId)
    }

}