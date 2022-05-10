package tech.sperlikoliver.and_kitchen

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.key.Key.Companion.Home
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
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
import org.intellij.lang.annotations.JdkConstants
import tech.sperlikoliver.and_kitchen.View.MealPlanner
import tech.sperlikoliver.and_kitchen.View.Recipes
import tech.sperlikoliver.and_kitchen.View.Scaffold.Login
import tech.sperlikoliver.and_kitchen.View.ShoppingList
import tech.sperlikoliver.and_kitchen.ui.theme.And_kitchenTheme

class MainActivity : ComponentActivity() {
    private var user : FirebaseUser? = FirebaseAuth.getInstance().currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        var startDestination = ""
        if (user != null){
            startDestination = "recipes"
        } else {
            startDestination = "login"
        }
        super.onCreate(savedInstanceState)


        setContent {
            And_kitchenTheme {
                val systemUiController = rememberSystemUiController()
                if (isSystemInDarkTheme()) {
                    systemUiController.setSystemBarsColor(
                        color = Color.Black
                    )
                } else {
                    systemUiController.setSystemBarsColor(
                        color = Color.White
                    )
                }

                val navController = rememberNavController();
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                Scaffold(
                    topBar = { TopBarView(navController = navController, currentRoute = currentRoute)},
                    bottomBar = { BottomBarView(navController = navController, currentRoute = currentRoute)},
                ) {
                    NavHost(navController = navController, startDestination = startDestination) {
                        composable("login")
                        {
                            if(FirebaseAuth.getInstance().currentUser != null){
                                navController.navigate("recipes")
                            } else {
                                Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                                    Text(text = "Welcome to Kitchen! Please sign in to continue", modifier = Modifier.padding(bottom = 10.dp))
                                    Button(onClick = { signIn() }) {
                                        Text(text = "Sign In")
                                    }
                                }

                            }
                        }
                        composable("recipes") { Recipes(navController = navController) }
                        composable("shoppingList") { ShoppingList(navController = navController) }
                        composable("mealPlanner") { MealPlanner(navController = navController) }
                        composable("settings") { Settings(navController = navController) }
                    }
                }
            }

        }


    }



    fun signIn(){
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build()
        )
        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()
        signInLauncher.launch(signInIntent)
    }

    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    )   {
            res -> this.signInResult(res)
    }


    private fun signInResult(result: FirebaseAuthUIAuthenticationResult){
        val response = result.idpResponse
        if (result.resultCode == RESULT_OK){
            user = FirebaseAuth.getInstance().currentUser
        } else {
            Log.e("Login Repository", "Error" + response?.error?.message)
        }


}



}

@Composable
fun NavigationIcon(navController : NavController, currentRoute : String?, navigationRoute : String, contentDescription: String, icon : ImageVector, modifier: Modifier = Modifier.scale(1f)){
    IconButton(onClick = { navController.navigate(navigationRoute) }, modifier = modifier) {
        if (currentRoute == navigationRoute){
            Icon(icon, contentDescription, tint = MaterialTheme.colors.primary, modifier = Modifier.scale(1.2f) )
        }else {
            Icon(icon, contentDescription)
        }
    }
}
