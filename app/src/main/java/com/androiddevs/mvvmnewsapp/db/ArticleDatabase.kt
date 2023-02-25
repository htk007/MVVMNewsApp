package com.androiddevs.mvvmnewsapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.androiddevs.mvvmnewsapp.models.Article

@Database(
    entities = [Article::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class ArticleDatabase : RoomDatabase() {

    abstract fun getArticleDao(): ArticleDao

    companion object {
        // that means: threats can immediately see when a threat changes this instance.
        @Volatile
        private var instance: ArticleDatabase? = null   //singleton instance
        // We'll use that to synchronize setting that instance
        private val LOCK = Any()
        //when we write ArticleDatabase on another places, invoke method will call
        operator fun invoke(context: Context)= instance ?: synchronized(LOCK){
            //if instance is null in that case we want to start a synchronized block with
            //out lock object object so that means that everthing that happens inside of this block of code here
            // that happens inside of this block of code here cant be accessed by other threads at the same time
            // by this way we sure, is not another threat that sets instance
            instance ?: createDatabase(context).also { instance = it}
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ArticleDatabase::class.java,
                "article_db.db"
            ).build()

    }
}