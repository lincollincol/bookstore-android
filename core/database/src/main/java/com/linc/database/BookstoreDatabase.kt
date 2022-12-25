package com.linc.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.linc.database.converters.StringListConverter
import com.linc.database.dao.BookmarksDao
import com.linc.database.dao.OrdersDao
import com.linc.database.dao.BooksDao
import com.linc.database.dao.SubjectDao
import com.linc.database.entity.book.BookEntity
import com.linc.database.entity.bookmark.BookmarkEntity
import com.linc.database.entity.order.OrderEntity
import com.linc.database.entity.subject.SubjectBookCrossRef
import com.linc.database.entity.subject.SubjectEntity

private const val BOOKSTORE_DATABASE = "bookstore_database.db"
private const val INITIAL_ASSET_DATABASE = "database/initial_bookstore_database.db"

@Database(
    version = 1,
    exportSchema = false,
    entities = [
        BookEntity::class,
        SubjectEntity::class,
        SubjectBookCrossRef::class,
        OrderEntity::class,
        BookmarkEntity::class,
    ]
)
@TypeConverters(
    StringListConverter::class
)
abstract class BookstoreDatabase : RoomDatabase() {
    abstract val ordersDao: OrdersDao
    abstract val booksDao: BooksDao
    abstract val subjectDao: SubjectDao
    abstract val bookmarksDao: BookmarksDao

    companion object {
        @JvmStatic
        fun create(context: Context): BookstoreDatabase = Room
            .databaseBuilder(
                context,
                BookstoreDatabase::class.java,
                BOOKSTORE_DATABASE
            )
            .createFromAsset(INITIAL_ASSET_DATABASE)
            .build()
    }
}