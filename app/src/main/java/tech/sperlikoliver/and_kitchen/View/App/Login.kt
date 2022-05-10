package tech.sperlikoliver.and_kitchen.View.Scaffold

import android.app.Activity
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract

@Composable
fun Login(navController: NavController){
    val signIn = remember { mutableStateOf(false) }
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text(text = "Welcome to Kitchen! Please sign in to continue", modifier = Modifier.padding(bottom = 10.dp))
        Button(onClick = {
            signIn.value = true
        }) {
            Text(text = "Sign In")
        }
    }
    if(signIn.value){
        signIn()
    }
}

@Composable
private fun signIn(){

    val launcher = rememberLauncherForActivityResult(contract = FirebaseAuthUIActivityResultContract(), onResult = { result ->
        val response = result.idpResponse
        if (result.resultCode == Activity.RESULT_OK){
            Log.i("Login", "Success")
        } else {
            Log.e("Login", "Error: ${response?.error?.message}")
        }
    })

    val providers = arrayListOf(
        AuthUI.IdpConfig.EmailBuilder().build()
    )
    val signInIntent = AuthUI.getInstance()
        .createSignInIntentBuilder()
        .setAvailableProviders(providers)
        .build()
    SideEffect {
        launcher.launch(signInIntent)
    }

}