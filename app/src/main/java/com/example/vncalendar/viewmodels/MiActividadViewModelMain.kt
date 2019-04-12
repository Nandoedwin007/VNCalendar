package com.example.vncalendar.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.vncalendar.data.MiActividad
import com.example.vncalendar.data.MiActividadRepository
import com.google.firebase.auth.FirebaseAuth

class MiActividadViewModelMain(application: Application) : AndroidViewModel(application){

    //Nótese que si se cambia algo en la clase MiActividad aquí también se debe considerar el cambio
    companion object CompanionViewModel {

        //private var uid:String = FirebaseAuth.getInstance().uid.toString()

    }

    var uid1:String = FirebaseAuth.getInstance().uid.toString()

    var repository: MiActividadRepository =
        MiActividadRepository(application,uid1)

    private var allMisActividades : LiveData<List<MiActividad>> = repository.getAllMisActividades()

    fun setUid(uid:String){
        uid1 = uid
        repository.closeDB()
    }

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