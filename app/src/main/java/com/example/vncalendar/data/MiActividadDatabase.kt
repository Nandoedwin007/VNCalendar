package com.example.vncalendar.data

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import java.security.AccessControlContext

@Database(entities = [MiActividad::class], version = 1)
abstract class MiActividadDatabase:RoomDatabase(){
    abstract fun miActividadDao(): MiActividadDao

    companion object {
        private var instance: MiActividadDatabase? = null

        fun getInstance(context: Context):MiActividadDatabase?{
            if (instance == null){
                synchronized(MiActividadDatabase::class){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MiActividadDatabase::class.java,
                        "miactividad_database"
                    )
                        .fallbackToDestructiveMigration()
                        .addCallback(roomCalback)
                        .build()
                }
            }
            return instance
        }
        fun destroyInstance(){
            instance = null
        }
        private val roomCalback = object : RoomDatabase.Callback(){
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                PopulateDbAsyncTask(instance).execute()
            }
        }
    }

    class PopulateDbAsyncTask(db:MiActividadDatabase?):AsyncTask<Unit,Unit,Unit>(){
        private val miActividadDao = db?.miActividadDao()

        override fun doInBackground(vararg params: Unit?) {
            miActividadDao?.insert(MiActividad("Titulo 1","Descripción 1",1,1,1,1,"fecha 1",1))
            miActividadDao?.insert(MiActividad("Titulo 2","Descripción 2",2,2,2,2,"fecha 2",2))
            miActividadDao?.insert(MiActividad("Titulo 3","Descripción 3",3,3,3,3,"fecha 3",3))
        }
    }
}