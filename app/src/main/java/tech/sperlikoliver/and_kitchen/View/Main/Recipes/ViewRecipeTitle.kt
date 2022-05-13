package tech.sperlikoliver.and_kitchen.View.Main.Recipes

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight

@Composable
fun ViewRecipeTitle(title : String){
    Text(title.uppercase(), fontWeight = FontWeight.Bold, color = MaterialTheme.colors.primary, )
}