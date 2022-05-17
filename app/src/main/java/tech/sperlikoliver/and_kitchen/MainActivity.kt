package tech.sperlikoliver.and_kitchen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import tech.sperlikoliver.and_kitchen.Model.Room.Database.KitchenDatabase
import tech.sperlikoliver.and_kitchen.Model.App.AnonymousAuth

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        KitchenDatabase.initialize(this)
        AnonymousAuth.initialize(this)

        setContent {
            KitchenApp()
        }
    }
}






