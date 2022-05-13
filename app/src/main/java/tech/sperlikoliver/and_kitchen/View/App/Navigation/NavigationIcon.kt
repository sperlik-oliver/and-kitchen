package tech.sperlikoliver.and_kitchen.View.Misc

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController

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