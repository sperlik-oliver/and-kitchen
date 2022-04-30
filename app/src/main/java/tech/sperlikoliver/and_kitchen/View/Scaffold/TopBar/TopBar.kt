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
import androidx.compose.ui.draw.scale
import tech.sperlikoliver.and_kitchen.View.Scaffold.NavigationIcon
import tech.sperlikoliver.and_kitchen.View.Scaffold.TopBarTitle


@Composable
fun TopBarView(navController : NavController, currentRoute: String?) {


    TopAppBar(backgroundColor = Color(255, 255, 255)){
        Row(modifier = Modifier.weight(2f), horizontalArrangement = Arrangement.Start){
            TopBarTitle(currentRoute = currentRoute)
        }

        Row(modifier = Modifier.weight(3f), horizontalArrangement = Arrangement.End) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Filled.Search, "Search")
            }
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



