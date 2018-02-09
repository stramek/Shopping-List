package pl.marcinstramowski.shoppinglist.screens.splash

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import org.jetbrains.anko.startActivity
import pl.marcinstramowski.shoppinglist.screens.main.MainActivity

/**
 * Initial activity after application is launched.
 */
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity<MainActivity>()
        finish()
    }
}