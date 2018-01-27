package team.unithon12.unithonteam12.ext

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.View
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/**
 * Created by baghyeongi on 2018. 1. 26..
 */

fun Context.color(resId: Int) = ContextCompat.getColor(this, resId)

fun Context.drawable(resId: Int) = ContextCompat.getDrawable(this, resId)

fun View.isVisible(bool: Boolean) {
    doAsync {
        uiThread {
            when {
                bool -> this@isVisible.visibility = View.VISIBLE
                else -> this@isVisible.visibility = View.GONE
            }
        }
    }
}