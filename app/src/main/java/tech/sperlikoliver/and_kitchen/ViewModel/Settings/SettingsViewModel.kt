package tech.sperlikoliver.and_kitchen.ViewModel.Settings

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import tech.sperlikoliver.and_kitchen.Model.Firebase.Repository.UserRepositoryFirebase
import tech.sperlikoliver.and_kitchen.Model.Interface.IUserRepository

class SettingsViewModel() : ViewModel() {
    private val userRepositoryImpl: IUserRepository = UserRepositoryFirebase()
    private val _emailStateFlow = MutableStateFlow(FirebaseAuth.getInstance().currentUser?.email!!)
    val emailState: StateFlow<String> get() = _emailStateFlow

    private fun setEmailState(email: String) {
        _emailStateFlow.value = email
    }


    fun signOut(){
        userRepositoryImpl.signOut()
    }
    fun deleteAccount(){
        userRepositoryImpl.deleteAccount()
    }
    fun changePassword(password: String, oldPassword : String, email : String){
        userRepositoryImpl.changePassword(password = password, oldPassword = oldPassword, email = email)
    }
    fun changeEmail(email : String, oldEmail: String, password: String){
        userRepositoryImpl.changeEmail(email, oldEmail, password)
        setEmailState(email = email); }
    }
