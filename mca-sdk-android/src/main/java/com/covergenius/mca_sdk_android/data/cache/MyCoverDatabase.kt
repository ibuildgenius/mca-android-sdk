
package com.covergenius.mca_sdk_android.data.cache
/*
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.covergenius.mca_sdk_android.data.remote.dto.ProductDetail

@Database(entities = [ProductDetail::class], version = 1)
abstract class MyCoverDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao

    companion object {
        @Volatile
        private var INSTANCE: MyCoverDatabase? = null

        fun getDatabase(context: Context): MyCoverDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MyCoverDatabase::class.java,
                    "my_cover_android_sdk_database"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance

                instance
            }
        }
    }
}*/
