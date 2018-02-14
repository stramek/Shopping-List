package pl.marcinstramowski.shoppinglist.di.components

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule
import pl.marcinstramowski.shoppinglist.di.modules.*
import javax.inject.Singleton

@Singleton
@Component(modules = [
    ApplicationModule::class, ActivityBindingModule::class, FragmentBindingModule::class,
    AndroidSupportInjectionModule::class, DatabaseModule::class, DatabaseSourcesBindingModule::class
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
