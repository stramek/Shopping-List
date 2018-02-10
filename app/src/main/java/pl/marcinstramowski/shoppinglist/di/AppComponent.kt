package pl.marcinstramowski.shoppinglist.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule
import pl.marcinstramowski.shoppinglist.database.DatabaseModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    ApplicationModule::class, ActivityBindingModule::class, FragmentBindingModule::class,
    AndroidSupportInjectionModule::class, DatabaseModule::class
])
interface AppComponent : AndroidInjector<DaggerApplication> {

    override fun inject(instance: DaggerApplication)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}
