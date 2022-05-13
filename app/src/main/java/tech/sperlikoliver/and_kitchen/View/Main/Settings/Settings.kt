package tech.sperlikoliver.and_kitchen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import tech.sperlikoliver.and_kitchen.ViewModel.Settings.SettingsViewModel
import androidx.compose.runtime.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import tech.sperlikoliver.and_kitchen.Model.App.AnonymousAuth
import tech.sperlikoliver.and_kitchen.View.App.Navigation.NavUtility



@Composable
fun Settings(navController: NavController){

    if(!AnonymousAuth.get()) {

        val viewModel: SettingsViewModel = remember {
            SettingsViewModel()
        }

        Column(modifier = Modifier.fillMaxSize()) {

            Email(viewModel)
            Password(viewModel)
            SignOut(viewModel, navController)
            DeleteAccount(viewModel, navController)
        }

    } else {
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {

            Button(onClick = { NavUtility.SetPopUpToNav("login", navController); AnonymousAuth.set(false) }) {
                Text("Sign In")
            }
        }
    }
}

@Composable
private fun Email(viewModel: SettingsViewModel){

    val email = viewModel.emailState.collectAsState()
    var changingEmail by remember { mutableStateOf(false) }
    var changingEmailField by remember { mutableStateOf("") }
    var oldEmailField by remember { mutableStateOf("") }
    var passwordField by remember { mutableStateOf("") }

    Text(
        text = "E-mail",
        modifier = Modifier.padding(start = 15.dp, top = 15.dp),
        fontWeight = FontWeight.Bold
    )
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp)
    ) {
        Text(text = email.value)
        IconButton(onClick = { changingEmail = !changingEmail }) {
            Icon(Icons.Filled.Edit, "Edit Email", tint = MaterialTheme.colors.primary)
        }
    }

    if (changingEmail) {
        Column(
            modifier = Modifier.padding(
                bottom = 15.dp,
                start = 15.dp,
                end = 15.dp,
                top = 7.dp
            )
        ) {
            OutlinedTextField(
                label = { Text("New Email") },
                modifier = Modifier.fillMaxWidth(1f),
                value = changingEmailField,
                onValueChange = { newValue -> changingEmailField = newValue },
                maxLines = 1
            )
            OutlinedTextField(
                label = { Text("Current Email") },
                modifier = Modifier.fillMaxWidth(1f),
                value = oldEmailField,
                onValueChange = { newValue -> oldEmailField = newValue },
                maxLines = 1

            )
            OutlinedTextField(
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(1f),
                value = passwordField,
                onValueChange = { newValue -> passwordField = newValue },
                maxLines = 1,
                visualTransformation = PasswordVisualTransformation()
            )
            Button(onClick = {
                viewModel.changeEmail(
                    changingEmailField,
                    oldEmailField,
                    passwordField
                ); changingEmailField = ""; oldEmailField = "";passwordField =
                "";changingEmail = false;
            }, modifier = Modifier.padding(top = 10.dp)) {
                Text(text = "Submit")
            }
        }
    }
}

@Composable
private fun Password(viewModel: SettingsViewModel){

    var changingPassword by remember { mutableStateOf(false) }
    var changingPasswordField by remember { mutableStateOf("") }
    var oldPasswordField by remember { mutableStateOf("") }
    var emailField by remember { mutableStateOf("") }

    Button(
        onClick = { changingPassword = !changingPassword },
        modifier = Modifier.padding(15.dp)
    ) {
        Text(text = "Change password")
    }

    if (changingPassword) {
        Column(
            modifier = Modifier.padding(
                bottom = 15.dp,
                start = 15.dp,
                end = 15.dp,
                top = 7.dp
            )
        ) {
            OutlinedTextField(
                label = { Text("New Password") },
                modifier = Modifier.fillMaxWidth(1f),
                value = changingPasswordField,
                onValueChange = { newValue -> changingPasswordField = newValue },
                maxLines = 1,
                visualTransformation = PasswordVisualTransformation()
            )
            OutlinedTextField(
                label = { Text("Current Password") },
                modifier = Modifier.fillMaxWidth(1f),
                value = oldPasswordField,
                onValueChange = { newValue -> oldPasswordField = newValue },
                maxLines = 1,
                visualTransformation = PasswordVisualTransformation()
            )
            OutlinedTextField(
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(1f),
                value = emailField,
                onValueChange = { newValue -> emailField = newValue },
                maxLines = 1
            )
            Button(onClick = {
                viewModel.changePassword(
                    password = changingPasswordField,
                    oldPassword = oldPasswordField,
                    email = emailField
                ); changingPasswordField = ""; oldPasswordField = "";emailField =
                "";changingPassword = false;
            }, modifier = Modifier.padding(top = 10.dp)) {
                Text(text = "Submit")
            }
        }
    }
}

@Composable
private fun SignOut(viewModel : SettingsViewModel, navController: NavController){
    Button(onClick = {
        NavUtility.SetPopUpToNav("login", navController)
        viewModel.signOut()
    }, modifier = Modifier.padding(15.dp)) {
        Text(text = "Sign Out")
    }
}

@Composable
private fun DeleteAccount(viewModel: SettingsViewModel, navController: NavController){
    Button(
        onClick = {
            NavUtility.SetPopUpToNav("login", navController)
            viewModel.deleteAccount()
        }, modifier = Modifier
            .padding(15.dp)
            .background(color = MaterialTheme.colors.secondary)
    ) {
        Text(text = "Delete account")
    }
}
