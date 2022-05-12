package tech.sperlikoliver.and_kitchen.View

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import tech.sperlikoliver.and_kitchen.Model.Domain.Repository.MealPlannerEntry
import tech.sperlikoliver.and_kitchen.ViewModel.MealPlannerViewModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.runtime.*
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.toSize
import com.google.firebase.auth.FirebaseAuth
import tech.sperlikoliver.and_kitchen.Model.Domain.Repository.Recipe

@Composable
fun MealPlanner(navController: NavController){
    val viewModel : MealPlannerViewModel = remember {
        MealPlannerViewModel()
    }

    val mealPlanner = viewModel.mealPlanner.collectAsState()
    LazyColumn(modifier = Modifier.padding(7.dp)) {
        items(mealPlanner.value) { mealPlannerEntry ->
            tech.sperlikoliver.and_kitchen.View.MealPlannerEntry(
                mealPlannerEntry = mealPlannerEntry,
                viewModel = viewModel,
                navController = navController
            )
        }
    }
}

@SuppressLint("SimpleDateFormat")
@Composable
fun MealPlannerEntry(mealPlannerEntry : MealPlannerEntry, viewModel: MealPlannerViewModel, navController: NavController){
    val id = mealPlannerEntry.id
    val date : Date = Date(mealPlannerEntry.dateTime * 1000)
    val format : DateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm");
    val formattedDate : String = format.format(date)
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start, modifier = Modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start, modifier = Modifier.weight(6.5f)){
            IconButton(onClick = { navController.navigate("editMealPlannerEntry/$id")}) {
                Icon(Icons.Filled.Edit, "Edit Meal Planner Entry", tint = MaterialTheme.colors.primary)
            }
            Button(onClick = {navController.navigate("viewMealPlannerEntry/$id")}) {
                Text(text = formattedDate, modifier=Modifier.fillMaxWidth())
            }
        }
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.End, modifier = Modifier.weight(1f)){
            IconButton(onClick = { viewModel.deleteMealPlannerEntry(mealPlannerEntry) }) {
                Icon(Icons.Filled.Delete, "Delete Meal Planner Entry", tint = MaterialTheme.colors.secondary)
            }
        }
    }
}

@Composable
fun ViewMealPlannerEntry(navController: NavController, mealPlannerEntryId : String) {
    val viewModel : MealPlannerViewModel = remember {
        MealPlannerViewModel(mealPlannerEntryId)
    }
    val mealPlannerEntry = viewModel.mealPlannerEntry.collectAsState()
    val date : Date = Date(mealPlannerEntry.value.dateTime * 1000)
    val dateFormat : DateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm")
    val timeFormat : DateFormat = SimpleDateFormat("HH:mm")
    val formattedDate : String = dateFormat.format(date)
    val formattedTime : String = timeFormat.format(date)
    val recipeId = mealPlannerEntry.value.recipeId
    Column(
        modifier = Modifier
            .padding(horizontal = 15.dp, vertical = 15.dp)
            .padding(bottom = 60.dp), verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Column() {
            ViewMealPlannerTitle(title = "Date")
            Text(formattedDate, Modifier.padding(top = 10.dp))
        }
        Column() {
            ViewMealPlannerTitle(title = "Time")
            Text(formattedTime, Modifier.padding(top = 10.dp))
        }
        Column() {
            ViewMealPlannerTitle(title = "Recipe")
            Button(onClick = {navController.navigate("viewRecipe/$recipeId")}, Modifier.padding(top = 10.dp)){
                Text("Recipe")
            }

        }

    }
}
@Composable
fun EditMealPlannerEntry(navController: NavController, mealPlannerEntryId: String){
    val viewModel : MealPlannerViewModel = remember {
        MealPlannerViewModel(mealPlannerEntryId)
    }

    var mExpanded by remember { mutableStateOf(false) }

    val mDate = viewModel.mDate.collectAsState()
    val mTime = viewModel.mTime.collectAsState()
    val recipeId = viewModel.recipeId.collectAsState()
    val recipes = viewModel.recipes.collectAsState()
    var mSelectedText = viewModel.selectedText.collectAsState()

    // Create a string value to store the selected city


    var mTextFieldSize by remember { mutableStateOf(androidx.compose.ui.geometry.Size.Zero)}

    val mContext = LocalContext.current

    // Declaring integer values
    // for year, month and day
    val mYear: Int
    val mMonth: Int
    val mDay: Int
    val mHour : Int
    val mMinute : Int

    // Initializing a Calendar
    val mCalendar = Calendar.getInstance()

    // Fetching current year, month and day
    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)
    mHour = mCalendar[Calendar.HOUR_OF_DAY]
    mMinute = mCalendar[Calendar.MINUTE]

    mCalendar.time = Date()

    // Declaring a string value to
    // store date in string format


    // Declaring DatePickerDialog and setting
    // initial values as current values (present year, month and day)
    val mDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            if(mDayOfMonth < 10 && mMonth < 10){
                viewModel.setMDate("0$mDayOfMonth/0${mMonth+1}/$mYear")
            } else if (mDayOfMonth < 10 && mMonth >= 10){
                viewModel.setMDate("0$mDayOfMonth/${mMonth+1}/$mYear")
            } else if (mDayOfMonth >= 10 && mMonth < 10){
                viewModel.setMDate("$mDayOfMonth/0${mMonth+1}/$mYear")
            } else {
                viewModel.setMDate("$mDayOfMonth/${mMonth+1}/$mYear")
            }
        }, mYear, mMonth, mDay
    )

    val mTimePickerDialog = TimePickerDialog(
        mContext,
        {_, mHour : Int, mMinute: Int ->
            if(mMinute < 10 && mHour < 10){
                viewModel.setMTime("0$mHour:0$mMinute")
            } else if (mMinute < 10 && mHour >= 10){
                viewModel.setMTime("$mHour:0$mMinute")
            } else if (mMinute >= 10 && mHour < 10){
                viewModel.setMTime("0$mHour:$mMinute")
            } else {
                viewModel.setMTime("$mHour:$mMinute")
            }
        }, mHour, mMinute, true
    )



    Column(
        modifier = Modifier
            .padding(horizontal = 15.dp, vertical = 15.dp)
            .padding(bottom = 60.dp)
        ,verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Column() {
            ViewMealPlannerTitle(title = "Date")
            Column {
                Button(onClick = { mDatePickerDialog.show() }, Modifier.padding(top = 10.dp)) {
                    Text("Choose date")
                }
                Text(mDate.value)
            }
        }
        Column() {
            ViewMealPlannerTitle(title = "Time")
            Column {
                Button(onClick = {mTimePickerDialog.show()}, Modifier.padding(top = 10.dp)){
                    Text("Choose time")
                }
                Text(mTime.value)
            }
        }
        Column() {
            ViewMealPlannerTitle(title = "Recipe")
            Column {

                // Up Icon when expanded and down icon when collapsed
                val icon = if (mExpanded)
                    Icons.Filled.KeyboardArrowUp
                else
                    Icons.Filled.KeyboardArrowDown

                Column(Modifier.padding(20.dp)) {

                    // Create an Outlined Text Field
                    // with icon and not expanded
                    OutlinedTextField(
                        value = mSelectedText.value,
                        onValueChange = { viewModel.setSelectedText(it)},
                        modifier = Modifier
                            .fillMaxWidth()
                            .onGloballyPositioned { coordinates ->
                                // This value is used to assign to
                                // the DropDown the same width
                                mTextFieldSize = coordinates.size.toSize()
                            },
                        label = {Text("Label")},
                        readOnly = true,
                        trailingIcon = {
                            Icon(icon,"contentDescription",
                                Modifier.clickable { mExpanded = !mExpanded })
                        }
                    )

                    // Create a drop-down menu with list of cities,
                    // when clicked, set the Text Field text as the city selected
                    DropdownMenu(
                        expanded = mExpanded,
                        onDismissRequest = { mExpanded = false },
                        modifier = Modifier
                            .width(with(LocalDensity.current){mTextFieldSize.width.toDp()})
                    ) {
                        recipes.value.forEach { recipe ->
                            DropdownMenuItem(onClick = {
                                viewModel.setSelectedText(recipe.name)
                                mExpanded = false
                            }) {
                                Text(text = recipe.name)
                            }
                        }
                    }
                }
            }
        }
        Button(onClick = {
            val date = mDate.value + " " + mTime.value
            val dateFormatted : Date = SimpleDateFormat("dd/MM/yyyy HH:mm").parse(date)
            val dateTime : Long = dateFormatted.time/1000
            var selectedRecipe : Recipe = Recipe()
            for (recipe in recipes.value){
                if (recipe.name == mSelectedText.value){
                    selectedRecipe = recipe
                    break
                }
            }
            val mealPlannerEntry = MealPlannerEntry(
                id = mealPlannerEntryId,
                dateTime = dateTime,
                recipeId = selectedRecipe.id
            )

            viewModel.editMealPlannerEntry(mealPlannerEntry)
            navController.navigateUp()
        }){
            Text("Submit")
        }
    }
}

@SuppressLint("SimpleDateFormat")
@Composable
fun AddMealPlannerEntry(navController: NavController){

    val viewModel : MealPlannerViewModel = remember {
        MealPlannerViewModel()
    }

    var mExpanded by remember { mutableStateOf(false) }

    // Create a list of cities

    val recipes = viewModel.recipes.collectAsState()


    // Create a string value to store the selected city
    var mSelectedText by remember { mutableStateOf("") }

    var mTextFieldSize by remember { mutableStateOf(androidx.compose.ui.geometry.Size.Zero)}

    val mContext = LocalContext.current

    // Declaring integer values
    // for year, month and day
    val mYear: Int
    val mMonth: Int
    val mDay: Int
    val mHour : Int
    val mMinute : Int

    // Initializing a Calendar
    val mCalendar = Calendar.getInstance()

    // Fetching current year, month and day
    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)
    mHour = mCalendar[Calendar.HOUR_OF_DAY]
    mMinute = mCalendar[Calendar.MINUTE]

    mCalendar.time = Date()

    // Declaring a string value to
    // store date in string format
    val mDate = remember { mutableStateOf("") }
    val mTime = remember { mutableStateOf("") }

    // Declaring DatePickerDialog and setting
    // initial values as current values (present year, month and day)
    val mDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            if(mDayOfMonth < 10 && mMonth < 10){
                mDate.value = "0$mDayOfMonth/0${mMonth+1}/$mYear"
            } else if (mDayOfMonth < 10 && mMonth >= 10){
                mDate.value = "0$mDayOfMonth/${mMonth+1}/$mYear"
            } else if (mDayOfMonth >= 10 && mMonth < 10){
                mDate.value = "$mDayOfMonth/0${mMonth+1}/$mYear"
            } else {
                mDate.value = "$mDayOfMonth/${mMonth+1}/$mYear"
            }
        }, mYear, mMonth, mDay
    )

    val mTimePickerDialog = TimePickerDialog(
        mContext,
        {_, mHour : Int, mMinute: Int ->
            if(mMinute < 10 && mHour < 10){
                mTime.value = "0$mHour:0$mMinute"
            } else if (mMinute < 10 && mHour >= 10){
                mTime.value = "$mHour:0$mMinute"
            } else if (mMinute >= 10 && mHour < 10){
                mTime.value = "0$mHour:$mMinute"
            } else {
                mTime.value = "$mHour:$mMinute"
            }
        }, mHour, mMinute, true
    )



    Column(
        modifier = Modifier
            .padding(horizontal = 15.dp, vertical = 15.dp)
            .padding(bottom = 60.dp)
        ,verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Column() {
            ViewMealPlannerTitle(title = "Date")
            Column {
                Button(onClick = { mDatePickerDialog.show() }, Modifier.padding(top = 10.dp)) {
                    Text("Choose date")
                }
                Text(mDate.value)
            }
        }
        Column() {
            ViewMealPlannerTitle(title = "Time")
            Column {
                Button(onClick = {mTimePickerDialog.show()}, Modifier.padding(top = 10.dp)){
                    Text("Choose time")
                }
                Text(mTime.value)
            }
        }
        Column() {
            ViewMealPlannerTitle(title = "Recipe")
            Column {

                // Up Icon when expanded and down icon when collapsed
                val icon = if (mExpanded)
                    Icons.Filled.KeyboardArrowUp
                else
                    Icons.Filled.KeyboardArrowDown

                Column(Modifier.padding(20.dp)) {

                    // Create an Outlined Text Field
                    // with icon and not expanded
                    OutlinedTextField(
                        value = mSelectedText,
                        onValueChange = { mSelectedText = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .onGloballyPositioned { coordinates ->
                                // This value is used to assign to
                                // the DropDown the same width
                                mTextFieldSize = coordinates.size.toSize()
                            },
                        label = {Text("Label")},
                        readOnly = true,
                        trailingIcon = {
                            Icon(icon,"contentDescription",
                                Modifier.clickable { mExpanded = !mExpanded })
                        }
                    )

                    // Create a drop-down menu with list of cities,
                    // when clicked, set the Text Field text as the city selected
                    DropdownMenu(
                        expanded = mExpanded,
                        onDismissRequest = { mExpanded = false },
                        modifier = Modifier
                            .width(with(LocalDensity.current){mTextFieldSize.width.toDp()})
                    ) {
                        recipes.value.forEach { recipe ->
                            DropdownMenuItem(onClick = {
                                mSelectedText = recipe.name
                                mExpanded = false
                            }) {
                                Text(text = recipe.name)
                            }
                        }
                    }
                }
            }
        }
        Button(onClick = {
            val date = mDate.value + " " + mTime.value
            val dateFormatted : Date = SimpleDateFormat("dd/MM/yyyy HH:mm").parse(date)
            val dateTime : Long = dateFormatted.time/1000
            var selectedRecipe : Recipe = Recipe()
            for (recipe in recipes.value){
               if (recipe.name == mSelectedText){
                   selectedRecipe = recipe
                   break
               }
            }
            val mealPlannerEntry = MealPlannerEntry(
                dateTime = dateTime,
                recipeId = selectedRecipe.id
            )
            viewModel.createMealPlannerEntry(mealPlannerEntry)
            navController.navigateUp()
        }){
            Text("Submit")
        }
    }
}
@Composable
fun ViewMealPlannerTitle(title : String){
    Text(title.uppercase(), fontWeight = FontWeight.Bold, color = MaterialTheme.colors.primary, )
}