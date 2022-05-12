package tech.sperlikoliver.and_kitchen.View.Scaffold

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.*
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
import androidx.navigation.NavOptions
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.google.firebase.auth.FirebaseAuth
import tech.sperlikoliver.and_kitchen.Model.Utility.mAuth
import tech.sperlikoliver.and_kitchen.View.Utility.NavUtility

private const val TAG : String = "Login"

@Composable
fun Login(navController: NavController){

    if (FirebaseAuth.getInstance().currentUser != null || mAuth.get()) {
        NavUtility.SetPopUpToNav("recipes", navController)
    }

    val signIn = remember { mutableStateOf(false) }
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text(text = "Welcome to Kitchen! Please sign in to continue. You may also use the app anonymously, however your data will not be backed up and accessible from other devices", modifier = Modifier.padding(bottom = 10.dp).padding(horizontal = 10.dp))
        Row(){
            Button(onClick = {
                signIn.value = true
            }, Modifier.padding(horizontal = 10.dp)) {
                Text(text = "Sign In")
            }
            Button(onClick = {
                mAuth.set(true)
                NavUtility.SetPopUpToNav("recipes", navController)
            }, Modifier.padding(horizontal = 10.dp)){
                Text(text = "Continue anonymously")
            }
        }

    }
    if(signIn.value){
        signIn.value = !signIn.value
        signIn()
    }
}

@Composable
fun signIn(){
    val launcher = rememberLauncherForActivityResult(contract = FirebaseAuthUIActivityResultContract(), onResult = { result ->
        val response = result.idpResponse
        if (result.resultCode != RESULT_OK) {
            Log.i(TAG, "Error: ${response?.error?.message}")
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