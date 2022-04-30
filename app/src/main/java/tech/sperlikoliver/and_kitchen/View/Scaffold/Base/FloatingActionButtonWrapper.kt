package tech.sperlikoliver.and_kitchen.View.Scaffold.Base

import android.util.Log
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import java.lang.reflect.Modifier

@Composable
fun FloatingActionButtonWrapper (navController: NavController, fabPressed : Boolean, onPressedChange : (Boolean) -> Unit) {

    FloatingActionButton(onClick = { onPressedChange(!fabPressed) }, backgroundColor = Color(251,140,0)) {
        Icon(Icons.Filled.Add, "Add", tint = Color(0,0,0))
    }
}