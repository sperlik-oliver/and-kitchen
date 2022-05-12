package tech.sperlikoliver.and_kitchen

import android.app.Activity.RESULT_OK
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import tech.sperlikoliver.and_kitchen.Model.KitchenDatabase
import tech.sperlikoliver.and_kitchen.Model.Utility.mAuth
import tech.sperlikoliver.and_kitchen.View.MealPlanner
import tech.sperlikoliver.and_kitchen.View.Recipes
import tech.sperlikoliver.and_kitchen.View.ShoppingList
import tech.sperlikoliver.and_kitchen.View.Theme.And_kitchenTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        KitchenDatabase.initialize(this)
        mAuth.initialize(this)

        setContent {

            KitchenApp()

        }
    }
}





