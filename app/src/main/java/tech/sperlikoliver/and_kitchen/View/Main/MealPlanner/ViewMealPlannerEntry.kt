package tech.sperlikoliver.and_kitchen.View.Main.MealPlanner

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import tech.sperlikoliver.and_kitchen.View.Main.MealPlanner.Utility.DateTimeFormat
import tech.sperlikoliver.and_kitchen.ViewModel.MealPlanner.ViewMealPlannerViewModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ViewMealPlannerEntry(navController: NavController, mealPlannerEntryId : String) {
    val viewModel : ViewMealPlannerViewModel = remember {
        ViewMealPlannerViewModel(mealPlannerEntryId)
    }

    val mealPlannerEntry = viewModel.mealPlannerEntry.collectAsState()
    val recipeId = mealPlannerEntry.value.recipeId

    Column(
        modifier = Modifier
            .padding(horizontal = 15.dp, vertical = 15.dp)
            .padding(bottom = 60.dp), verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Column() {
            ViewMealPlannerTitle(title = "Date")
            Text(DateTimeFormat.parseEpochToStringSeparate(mealPlannerEntry.value.dateTime)["date"]!!, Modifier.padding(top = 10.dp))
        }
        Column() {
            ViewMealPlannerTitle(title = "Time")
            Text(DateTimeFormat.parseEpochToStringSeparate(mealPlannerEntry.value.dateTime)["time"]!!, Modifier.padding(top = 10.dp))
        }
        Column() {
            ViewMealPlannerTitle(title = "Recipe")
            Button(onClick = {navController.navigate("viewRecipe/$recipeId")}, Modifier.padding(top = 10.dp)){
                Text("Recipe")
            }

        }

    }
}