package team.unithon12.unithonteam12.ui._base

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.extensions.LayoutContainer

/**
 * Created by baghyeongi on 2018. 1. 27..
 */

abstract class BaseViewHolder<M>(view: View) : RecyclerView.ViewHolder(view), LayoutContainer {
    abstract fun bind(model: M)
}

abstract class BaseAdapter<M, VH : BaseViewHolder<M>> : RecyclerView.Adapter<VH>() {

    abstract val list: List<M>

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind(list[position])

    override fun getItemCount() = list.size

}