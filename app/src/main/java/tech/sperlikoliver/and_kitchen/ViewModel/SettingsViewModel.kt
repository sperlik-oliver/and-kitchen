package tech.sperlikoliver.and_kitchen.ViewModel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import tech.sperlikoliver.and_kitchen.Model.Repository.ShoppingListRepositoryImpl

class SettingsViewModel() : ViewModel() {
    private val shoppingListRepositoryImpl : ShoppingListRepositoryImpl = ShoppingListRepositoryImpl()
    private val _emailStateFlow = MutableStateFlow(FirebaseAuth.getInstance().currentUser?.email!!)
    val emailState : StateFlow<String> get() = _emailStateFlow

    fun setEmailState (email : String){
        _emailStateFlow.value = email
    }


    fun signOut(){
        shoppingListRepositoryImpl.signOut()
    }
    fun deleteAccount(){
        shoppingListRepositoryImpl.deleteAccount()
    }
    fun changePassword(password: String, oldPassword : String, email : String){
        shoppingListRepositoryImpl.changePassword(password = password, oldPassword = oldPassword, email = email)
    }
    fun changeEmail(email : String, oldEmail: String, password: String){
        shoppingListRepositoryImpl.changeEmail(email, oldEmail, password)
        setEmailState(email = email); }
    }
