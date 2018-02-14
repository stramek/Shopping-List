package pl.marcinstramowski.shoppinglist.di.modules

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module
import pl.marcinstramowski.shoppinglist.rxSchedulers.AppSchedulerProvider
import pl.marcinstramowski.shoppinglist.rxSchedulers.SchedulerProvider

@Module
abstract class ApplicationModule {

    @Binds internal abstract fun bindContext(application: Application): Context

    @Binds internal abstract fun bindProvider(provider: AppSchedulerProvider): SchedulerProvider
}