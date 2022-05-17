package tech.sperlikoliver.and_kitchen.View.Main.MealPlanner

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import tech.sperlikoliver.and_kitchen.Model.Firebase.Entity.MealPlannerEntry
import tech.sperlikoliver.and_kitchen.View.Main.MealPlanner.Utility.DateTimeFormat
import tech.sperlikoliver.and_kitchen.ViewModel.MealPlanner.MealPlannerViewModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun MealPlanner(navController: NavController){
    val viewModel : MealPlannerViewModel = remember {
        MealPlannerViewModel()
    }

    val mealPlanner = viewModel.mealPlanner.collectAsState()
    LazyColumn(modifier = Modifier.padding(7.dp)) {
        items(mealPlanner.value) { mealPlannerEntry ->
            MealPlannerEntry(
                mealPlannerEntry = mealPlannerEntry,
                viewModel = viewModel,
                navController = navController
            )
        }
    }
}

@SuppressLint("SimpleDateFormat")
@Composable
private fun MealPlannerEntry(mealPlannerEntry : MealPlannerEntry, viewModel: MealPlannerViewModel, navController: NavController){
    val id = mealPlannerEntry.id
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start, modifier = Modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start, modifier = Modifier.weight(6.5f)){
            IconButton(onClick = { navController.navigate("editMealPlannerEntry/$id")}) {
                Icon(Icons.Filled.Edit, "Edit Meal Planner Entry", tint = MaterialTheme.colors.primary)
            }
            Button(onClick = {navController.navigate("viewMealPlannerEntry/$id")}) {
                Text(text = DateTimeFormat.parseEpochToString(mealPlannerEntry.dateTime), modifier= Modifier.fillMaxWidth())
            }
        }
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.End, modifier = Modifier.weight(1f)){
            IconButton(onClick = { viewModel.deleteMealPlannerEntry(mealPlannerEntry) }) {
                Icon(Icons.Filled.Delete, "Delete Meal Planner Entry", tint = MaterialTheme.colors.secondary)
            }
        }
    }
}