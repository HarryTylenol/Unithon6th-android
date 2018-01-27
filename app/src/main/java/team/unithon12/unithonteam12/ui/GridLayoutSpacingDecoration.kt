package team.unithon12.unithonteam12.ui

import android.graphics.Rect
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by baghyeongi on 2018. 1. 27..
 */

class GridLayoutSpacingDecoration private constructor(builder: Builder) : RecyclerView.ItemDecoration() {

    private val includeEdge: Boolean
    private var horizontalSpacing: Int = 0
    private var verticalSpacing: Int = 0
    private var gridLayoutManager: GridLayoutManager? = null

    init {
        includeEdge = builder.includeEdge
        val spacing = builder.spacing
        if (spacing != 0) {
            horizontalSpacing = spacing
            verticalSpacing = spacing
        }
        else {
            horizontalSpacing = builder.horizontalSpacing
            verticalSpacing = builder.verticalSpacing
        }
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
        if (gridLayoutManager == null) {
            gridLayoutManager = parent.layoutManager as GridLayoutManager
        }

        val spanCount = gridLayoutManager!!.spanCount
        val position = parent.getChildAdapterPosition(view)
        val spanSize = gridLayoutManager!!.spanSizeLookup.getSpanSize(position)
        val column = gridLayoutManager!!.spanSizeLookup.getSpanIndex(position, spanCount)
        val totalChildCount = parent.adapter.itemCount
        val isLastRow = if (spanSize == 1)
            position + spanCount - column > totalChildCount - 1
        else
            position - column / spanSize > totalChildCount - 1
        val isFirstRow = gridLayoutManager!!.spanSizeLookup.getSpanGroupIndex(position, spanCount) == 0

        if (includeEdge) {
            outRect.left = horizontalSpacing - column * horizontalSpacing / spanCount
            outRect.right = (column + spanSize) * horizontalSpacing / spanCount
            outRect.top = verticalSpacing
            outRect.bottom = if (isLastRow) verticalSpacing else 0
        }
        else {
            outRect.left = column * horizontalSpacing / spanCount
            outRect.right = horizontalSpacing - (column + spanSize) * horizontalSpacing / spanCount
            outRect.top = if (isFirstRow) 0 else verticalSpacing
        }
    }

    class Builder {
        var includeEdge: Boolean = false
        var spacing = 0
        var verticalSpacing: Int = 0
        var horizontalSpacing: Int = 0

        fun includeEdge(includeEdge: Boolean): Builder {
            this.includeEdge = includeEdge
            return this
        }

        fun spacing(spacing: Int): Builder {
            this.spacing = spacing
            return this
        }

        fun verticalSpacing(verticalSpacing: Int): Builder {
            this.verticalSpacing = verticalSpacing
            return this
        }

        fun horizontalSpacing(horizontalSpacing: Int): Builder {
            this.horizontalSpacing = horizontalSpacing
            return this
        }

        fun build(): GridLayoutSpacingDecoration {
            return GridLayoutSpacingDecoration(this)
        }
    }

    companion object {

        fun newBuilder(): Builder {
            return Builder()
        }
    }

}

