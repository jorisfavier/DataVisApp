package fr.jorisfavier.a2048datavis.di

import android.app.Application
import dagger.Module
import dagger.Provides
import fr.jorisfavier.a2048datavis.api.AppAnnieService
import javax.inject.Singleton


@Module
class DataVisModule(val application: Application) {

    @Singleton
    @Provides
    fun applicationProvider(): Application {
        return application
    }

    @Singleton
    @Provides
    fun appAnnieServiceProvider(): AppAnnieService {
        return AppAnnieService.create()
    }
}