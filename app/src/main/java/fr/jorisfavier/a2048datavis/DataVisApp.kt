package fr.jorisfavier.a2048datavis

import android.app.Application
import fr.jorisfavier.a2048datavis.di.DaggerDataVisComponent
import fr.jorisfavier.a2048datavis.di.DataVisComponent
import fr.jorisfavier.a2048datavis.di.DataVisModule

class DataVisApp: Application() {

    companion object {
        var currentInstance: DataVisApp? = null
    }

    var appModule: DataVisComponent? = null
    private set



    override fun onCreate() {
        super.onCreate()
        currentInstance = this
        appModule = DaggerDataVisComponent
            .builder()
            .dataVisModule(DataVisModule(this))
            .build()

    }
}