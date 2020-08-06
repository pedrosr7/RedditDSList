package thevoid.whichbinds.redditdslist.di

import org.koin.androidx.viewmodel.compat.ScopeCompat.viewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import thevoid.whichbinds.redditdslist.core.MyApplication
import thevoid.whichbinds.redditdslist.presentation.MainViewModel

val appModule = module {

    // single instance of HelloRepository
    single { MyApplication() }

    // MyViewModel ViewModel
    viewModel { MainViewModel(get()) }
}