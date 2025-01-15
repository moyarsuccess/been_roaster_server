package dev.moyar.beenroaster.dev.moyar.beenroaster.di

import dev.moyar.beenroaster.dev.moyar.beenroaster.temp.TempHelper
import org.koin.dsl.module

val applicationModule = module {
    single { TempHelper() }
}