package app.africa.autocheck.core.framework.ui

import android.util.Log.d
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class PaginationScrollListener (val layoutManager: RecyclerView.LayoutManager) : RecyclerView.OnScrollListener() {

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        var firstVisibleItemPosition = 0
        if (layoutManager is LinearLayoutManager) {
            firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
        }

        if (!isLoading() && !isLastPage()) {
            val totalVisibleCount = layoutManager.childCount + firstVisibleItemPosition
            if (totalVisibleCount >= layoutManager.itemCount) {
                d("PaginationScroll", "onScrolled: firstVisibleItemPosition (${firstVisibleItemPosition})")
                d("PaginationScroll", "onScrolled: loading More")
                loadMoreItems()
            }
        }
    }

    protected abstract fun loadMoreItems()
    abstract fun getTotalPageCount(): Int
    abstract fun isLastPage(): Boolean
    abstract fun isLoading(): Boolean

}

data class LayoutPagination(
    val PAGE_START: Int = 0,
    var isLoading: Boolean = false,
    var isLastPage: Boolean = false,
    var hasNextPage: Boolean = false,
    var lastLoadingPosition: Int = -1,
    var PER_PAGE: Int = 8,
    var CURRENT_PAGE: Int = 1,
    var TOTAL_PAGES : Int = 1
)
