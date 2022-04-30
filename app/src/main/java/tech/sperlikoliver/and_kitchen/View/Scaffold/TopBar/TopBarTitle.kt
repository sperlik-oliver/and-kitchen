package tech.sperlikoliver.and_kitchen.View.Scaffold

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TopBarTitle(currentRoute : String?){
    val title : String
    when(currentRoute){
        "settings" -> title = "settings"
        "shoppingList" -> title = "shopping list"
        "recipes" -> title = "recipes"
        "mealPlanner" -> title = "meal planner"
        else -> {
            title = "error"
        }
    }

    Text(text = title.uppercase(), color = Color.Black, modifier = Modifier.padding(5.dp))
}