package tech.sperlikoliver.and_kitchen

import androidx.compose.material.BottomAppBar
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.materialIcon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import tech.sperlikoliver.and_kitchen.Model.Utility.mAuth
import tech.sperlikoliver.and_kitchen.View.Misc.NavigationIcon

@Composable
fun BottomBarView (navController: NavController, currentRoute: String?) {
    BottomAppBar(backgroundColor = MaterialTheme.colors.background){
        if (FirebaseAuth.getInstance().currentUser != null || mAuth.get()) {
            NavigationIcon(
                navController = navController,
                currentRoute = currentRoute,
                navigationRoute = "recipes",
                contentDescription = "Recipes",
                icon = Icons.Filled.List,
                modifier = Modifier.weight(1f)
            )

            NavigationIcon(
                navController = navController,
                currentRoute = currentRoute,
                navigationRoute = "shoppingList",
                contentDescription = "Shopping List",
                icon = Icons.Filled.ShoppingCart,
                modifier = Modifier.weight(1f)
            )

            NavigationIcon(
                navController = navController,
                currentRoute = currentRoute,
                navigationRoute = "mealPlanner",
                contentDescription = "Meal Planner",
                icon = Icons.Filled.DateRange,
                modifier = Modifier.weight(1f)
            )
        } else{

        }
    }
}