package fr.jorisfavier.a2048datavis.di

import dagger.Component
import fr.jorisfavier.a2048datavis.ui.home.MainActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [DataVisModule::class])
interface DataVisComponent {
    fun inject(mainActivity: MainActivity)
}