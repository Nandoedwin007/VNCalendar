package com.example.vncalendar.data

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.google.firebase.auth.FirebaseAuth
import java.security.AccessControlContext


//Basado en el código del repositorio https://github.com/berkeatac/Notes-App
@Database(entities = [MiActividad::class], version = 2)
abstract class MiActividadDatabase:RoomDatabase(){
    abstract fun miActividadDao(): MiActividadDao

    companion object {
        private var instance: MiActividadDatabase? = null

        fun getInstance(context: Context,uid:String):MiActividadDatabase?{
            if (instance == null){
                synchronized(MiActividadDatabase::class){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MiActividadDatabase::class.java,
                        uid
                    )
                        .fallbackToDestructiveMigration()
                        .addCallback(roomCalback)
                        .build()
                }
                return instance
            }
            else if (instance != null){
                Log.d("Database","Instance not null")

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
            miActividadDao?.insert(MiActividad("Titulo 1","Descripción 1",1,1,1,1,0,1,1,"1"))
            miActividadDao?.insert(MiActividad("Titulo 2","Descripción 2",2,2,2,2,1,1,2,"2"))
            miActividadDao?.insert(MiActividad("Titulo 3","Descripción 3",3,3,3,3,0,3,3,"3"))
        }
    }
}