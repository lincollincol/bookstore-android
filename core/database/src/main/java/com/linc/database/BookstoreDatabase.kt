package com.linc.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.linc.database.converters.StringListConverter
import com.linc.database.dao.*
import com.linc.database.entity.book.BookEntity
import com.linc.database.entity.bookmark.BookmarkEntity
import com.linc.database.entity.localization.LocaleEntity
import com.linc.database.entity.localization.LocaleStringEntity
import com.linc.database.entity.order.OrderEntity
import com.linc.database.entity.payment.CustomerEntity
import com.linc.database.entity.payment.EphemeralKeyEntity
import com.linc.database.entity.payment.PaymentIntentEntity
import com.linc.database.entity.subject.SubjectBookCrossRef
import com.linc.database.entity.subject.SubjectEntity
import com.linc.database.entity.user.UserEntity
import java.util.Locale

private const val BOOKSTORE_DATABASE = "bookstore_database.db"
private const val INITIAL_ASSET_DATABASE = "database/initial_bookstore_database.db"

@Database(
    version = 1,
    exportSchema = false,
    entities = [
        UserEntity::class,
        CustomerEntity::class,
        BookEntity::class,
        SubjectEntity::class,
        SubjectBookCrossRef::class,
        OrderEntity::class,
        BookmarkEntity::class,
        LocaleEntity::class,
        LocaleStringEntity::class,
        EphemeralKeyEntity::class,
        PaymentIntentEntity::class,
    ]
)
@TypeConverters(
    StringListConverter::class
)
abstract class BookstoreDatabase : RoomDatabase() {
    abstract val usersDao: UsersDao
    abstract val paymentsDao: PaymentsDao
    abstract val ordersDao: OrdersDao
    abstract val booksDao: BooksDao
    abstract val subjectDao: SubjectDao
    abstract val bookmarksDao: BookmarksDao
    abstract val localeDao: LocaleDao

    suspend fun clearUserDataTables() {
        paymentsDao.clearTable()
        bookmarksDao.clearTable()
        ordersDao.clearTable()
        localeDao.clearTable()
        booksDao.clearTable()
        usersDao.clearTable()
        subjectDao.resetSubjects()
    }

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