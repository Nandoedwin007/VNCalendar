package com.example.vncalendar.data

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth


//Basado en el código del repositorio https://github.com/berkeatac/Notes-App
class MiActividadRepository(application: Application,uid:String){
    private var miActividadDao:MiActividadDao
    private var allMisActividades:LiveData<List<MiActividad>>

    init{

        var database:MiActividadDatabase = MiActividadDatabase.getInstance(
            application.applicationContext,uid)!!
        miActividadDao = database.miActividadDao()
        allMisActividades = miActividadDao.getAllMisActividades()


    }

    fun closeDB(){

    }

    fun insert (miActividad: MiActividad){
        val insertMiActividadAsyncTask= InsertMiActividadAsyncTask(miActividadDao).execute(miActividad)
    }

    fun update (miActividad: MiActividad){
        val updateMiActividadAsyncTask = UpdateMiActividadAsyncTask(miActividadDao).execute(miActividad)
    }

    fun delete (miActividad: MiActividad){
        val deleteMiActividadAsyncTask = DeleteMyActividadAsyncTask(miActividadDao).execute(miActividad)
    }

    fun deleteAllMisActividades(){
        val deleteAllMisActividadesAsyncTask = DeleteAllMisActividadesAsyncTask(miActividadDao).execute()
    }

    fun getAllMisActividades():LiveData<List<MiActividad>>{
        return  allMisActividades
    }

    companion object {
        private class InsertMiActividadAsyncTask(miActividadDao: MiActividadDao):AsyncTask<MiActividad,Unit,Unit>(){
            val miActividadDao = miActividadDao
            override fun doInBackground(vararg params: MiActividad?) {
                miActividadDao.insert(params[0]!!)
            }
        }

        private class UpdateMiActividadAsyncTask(miActividadDao: MiActividadDao):AsyncTask<MiActividad,Unit,Unit>(){
            val miActividadDao = miActividadDao

            override fun doInBackground(vararg params: MiActividad?) {
                miActividadDao.update(params[0]!!)
            }
        }

        private class DeleteMyActividadAsyncTask(miActividadDao: MiActividadDao):AsyncTask<MiActividad,Unit,Unit>(){
            val miActividadDao = miActividadDao

            override fun doInBackground(vararg params: MiActividad?) {
                miActividadDao.delete(params[0]!!)
            }
        }

        private class DeleteAllMisActividadesAsyncTask(miActividadDao: MiActividadDao):AsyncTask<Unit,Unit,Unit>(){
            val miActividadDao = miActividadDao

            override fun doInBackground(vararg params: Unit?) {
                miActividadDao.deleteAllActividades()
            }
        }
    }
}