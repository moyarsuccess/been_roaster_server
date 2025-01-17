package dev.moyar.beenroaster.dev.moyar.beenroaster.di

import com.google.gson.Gson
import dev.moyar.beenroaster.dev.moyar.beenroaster.roaster.RoasterManager
import dev.moyar.beenroaster.dev.moyar.beenroaster.temp.TempHelper
import org.koin.dsl.module

val applicationModule = module {
    single { TempHelper() }
    single { Gson() }
    single { RoasterManager(get(), get()) }
}