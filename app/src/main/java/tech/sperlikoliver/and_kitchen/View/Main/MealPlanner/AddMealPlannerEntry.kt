package tech.sperlikoliver.and_kitchen.View.Main.MealPlanner

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavController
import tech.sperlikoliver.and_kitchen.Model.Firebase.Entity.Recipe
import tech.sperlikoliver.and_kitchen.View.Main.MealPlanner.Utility.DateTimeFormat
import tech.sperlikoliver.and_kitchen.ViewModel.MealPlanner.AddMealPlannerViewModel
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
@Composable
fun AddMealPlannerEntry(navController: NavController){

    val viewModel : AddMealPlannerViewModel = remember { AddMealPlannerViewModel() }

    val mContext = LocalContext.current

    val recipes = viewModel.recipes.collectAsState()

    // UI State for select field
    var mExpanded by remember { mutableStateOf(false) }
    var mSelectedText by remember { mutableStateOf("") }
    var mTextFieldSize by remember { mutableStateOf(Size.Zero) }
    // Declaring integer values
    // for year, month, day, hour and minute
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
            mDate.value = DateTimeFormat.parseDayMonthYearToDate(mDayOfMonth, mMonth, mYear)
        }, mYear, mMonth, mDay
    )
    val mTimePickerDialog = TimePickerDialog(
        mContext,
        {_, mHour : Int, mMinute: Int ->
            mTime.value = DateTimeFormat.parseHourAndMinToTime(mHour, mMinute)
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
                        label = { Text("Label") },
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
            val dateTime = DateTimeFormat.parseStringToEpoch(mDate.value, mTime.value)
            var selectedRecipe = Recipe()
            for (recipe in recipes.value){
                if (recipe.name == mSelectedText){
                    selectedRecipe = recipe
                    break
                }
            }
            val mealPlannerEntry = tech.sperlikoliver.and_kitchen.Model.Firebase.Entity.MealPlannerEntry(
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