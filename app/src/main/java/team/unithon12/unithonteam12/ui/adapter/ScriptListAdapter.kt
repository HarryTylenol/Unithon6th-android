package team.unithon12.unithonteam12.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import team.unithon12.unithonteam12.R
import team.unithon12.unithonteam12.data.model.Script
import team.unithon12.unithonteam12.ui._base.BaseAdapter
import team.unithon12.unithonteam12.ui._base.BaseViewHolder

/**
 * Created by baghyeongi on 2018. 1. 27..
 */
class ScriptListViewHolder(override val containerView: View) : BaseViewHolder<Script>(containerView) {
    override fun bind(model: Script) {

    }
}

class ScriptListAdapter(override val list: List<Script>) : BaseAdapter<Script, ScriptListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScriptListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.row_script, parent, false)
        return ScriptListViewHolder(view)
    }
}