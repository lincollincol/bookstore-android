package com.linc.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.linc.model.Book
import com.linc.network.api.BooksApiService
import com.linc.network.model.book.BookApiModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import javax.inject.Inject

class BooksPagingSource @AssistedInject constructor(
    @Assisted private val query: String,
    private val booksApiService: BooksApiService
) : PagingSource<Int, BookApiModel>() {
    override fun getRefreshKey(state: PagingState<Int, BookApiModel>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BookApiModel> {
        if(query.isEmpty()) {
            return LoadResult.Page(emptyList(), null, null)
        }
        try {
            val page = params.key ?: 0
            val pageSize = params.loadSize
            val result = booksApiService.getPagedBooks(
                startIndex = page,
                maxResults = pageSize,
                query = query
            ).items
            return LoadResult.Page(
                data = result,
                nextKey = if(result.size < pageSize) null else page + 1,
                prevKey = if(page == 0) null else page - 1
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}