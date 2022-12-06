package com.linc.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.linc.database.converters.StringListConverter
import com.linc.database.dao.BookOrdersDao
import com.linc.database.dao.BooksDao
import com.linc.database.dao.SubjectDao
import com.linc.database.entity.book.BookEntity
import com.linc.database.entity.book.BookOrderEntity
import com.linc.database.entity.subject.SubjectBookCrossRef
import com.linc.database.entity.subject.SubjectEntity

private const val BOOKSTORE_DATABASE = "bookstore_database"

@Database(
    version = 1,
    exportSchema = false,
    entities = [
        BookEntity::class,
        BookOrderEntity::class,
        SubjectEntity::class,
        SubjectBookCrossRef::class
    ]
)
@TypeConverters(
    StringListConverter::class
)
abstract class BookstoreDatabase : RoomDatabase() {
    abstract val bookOrdersDao: BookOrdersDao
    abstract val booksDao: BooksDao
    abstract val subjectDao: SubjectDao

    companion object {
        @JvmStatic
        fun create(context: Context): BookstoreDatabase = Room
            .databaseBuilder(
                context,
                BookstoreDatabase::class.java,
                BOOKSTORE_DATABASE
            )
            .build()
    }
}