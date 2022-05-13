package tech.sperlikoliver.and_kitchen.Model.Interface

import tech.sperlikoliver.and_kitchen.Model.App.PropertyChangeAware

interface IUserRepository{

    fun signOut()
    fun deleteAccount()
    fun changePassword(password: String, oldPassword : String, email : String)
    fun changeEmail(email : String, oldEmail : String, password : String)


}