package tech.sperlikoliver.and_kitchen.Model.Repository.Implementation

import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import tech.sperlikoliver.and_kitchen.Model.Repository.Interface.IUserRepository

class UserRepositoryImpl : IUserRepository{

    override fun signOut (){
        FirebaseAuth.getInstance().signOut()
    }

    override fun deleteAccount(){
        FirebaseAuth.getInstance().currentUser?.delete()
        FirebaseAuth.getInstance().signOut()
    }

    override fun changePassword(password: String, oldPassword : String, email : String){
        val credential = EmailAuthProvider.getCredential(email, oldPassword)
        FirebaseAuth.getInstance().currentUser!!.reauthenticate(credential).addOnSuccessListener { FirebaseAuth.getInstance().currentUser?.updatePassword(password) }
    }

    override fun changeEmail(email : String, oldEmail : String, password : String){
        val credential = EmailAuthProvider.getCredential(oldEmail, password)
        FirebaseAuth.getInstance().currentUser!!.reauthenticate(credential).addOnSuccessListener { FirebaseAuth.getInstance().currentUser?.updateEmail(email)}
    }

}