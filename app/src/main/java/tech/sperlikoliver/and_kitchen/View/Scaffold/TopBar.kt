package tech.sperlikoliver.and_kitchen

import android.content.res.Resources
import android.util.Log
import android.util.Log.INFO
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import java.util.logging.Level.INFO
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.scale
import com.google.firebase.auth.FirebaseAuth
import tech.sperlikoliver.and_kitchen.View.Misc.NavigationIcon


@Composable
fun TopBarView(navController : NavController, currentRoute: String?) {


    TopAppBar(backgroundColor = MaterialTheme.colors.background){
        Row(modifier = Modifier.weight(2f), horizontalArrangement = Arrangement.Start){
            TopBarTitle(navController = navController, currentRoute = currentRoute)
        }
        if (FirebaseAuth.getInstance().currentUser != null) {
            Row(modifier = Modifier.weight(3f), horizontalArrangement = Arrangement.End) {

                NavigationIcon(
                    navController = navController,
                    currentRoute = currentRoute,
                    navigationRoute = "settings",
                    contentDescription = "Settings",
                    icon = Icons.Filled.Settings
                )


            }
        }

        }

    }

@Composable
private fun TopBarTitle(navController: NavController, currentRoute : String?){
    val title = when(currentRoute){
        "settings" -> "settings"
        "shoppingList" -> "shopping list"
        "recipes" -> "recipes"
        "mealPlanner" -> "meal planner"
        "login" -> "Kitchen"
        "viewRecipe/{id}" -> "recipes"
        "editRecipe/{id}" -> "recipes"
        "addRecipe" -> "recipes"
        else -> {
            "error"
        }
    }
    Row(verticalAlignment = Alignment.CenterVertically){
        if (currentRoute == "addRecipe" || currentRoute == "editRecipe/{id}" || currentRoute == "viewRecipe/{id}"){
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(Icons.Filled.ArrowBack, "Go back", tint = MaterialTheme.colors.primary)
            }
        }
        Text(text = title.uppercase(), color = MaterialTheme.colors.primaryVariant, modifier = Modifier.padding(5.dp))
    }

}



