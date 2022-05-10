package tech.sperlikoliver.and_kitchen.Model.Repository.Interface

interface IUserRepository {

    fun signOut()
    fun deleteAccount()
    fun changePassword(password: String, oldPassword : String, email : String)
    fun changeEmail(email : String, oldEmail : String, password : String)


}