package com.example.vncalendar.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.vncalendar.data.MiActividad
import com.example.vncalendar.data.MiActividadRepository

class MiActividadViewModelMain(application: Application) : AndroidViewModel(application){
    private var repository: MiActividadRepository =
        MiActividadRepository(application)

    private var allMisActividades : LiveData<List<MiActividad>> = repository.getAllMisActividades()

    fun insert (miActividad: MiActividad){
        repository.insert(miActividad)
    }

    fun update(miActividad: MiActividad){
        repository.update(miActividad)
    }

    fun getAllMisActividades(): LiveData<List<MiActividad>>{
        return allMisActividades
    }

    fun deleteAllMisActividades(){
        repository.deleteAllMisActividades()
    }

    fun delete(miActividad: MiActividad){
        repository.delete(miActividad)
    }
}