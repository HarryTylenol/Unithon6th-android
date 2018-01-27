package team.unithon12.unithonteam12.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.row_script.*
import team.unithon12.unithonteam12.R
import team.unithon12.unithonteam12.data.model.Script
import team.unithon12.unithonteam12.extension.toStringByFormat
import team.unithon12.unithonteam12.ui._base.BaseAdapter
import team.unithon12.unithonteam12.ui._base.BaseViewHolder

class ScriptListViewHolder(override val containerView: View) : BaseViewHolder<Script>(containerView) {
    override fun bind(model: Script) {
        tv_title.text = model.title
        tv_subtitle.text = model.content
        tv_date.text = model.date.toStringByFormat("yyyy년 MM월 dd")
    }
}

class ScriptListAdapter(override val list: List<Script>, override  val onItemClickListener: (Script) -> Unit) : BaseAdapter<Script, ScriptListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScriptListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.row_script, parent, false)
        return ScriptListViewHolder(view)
    }
}
