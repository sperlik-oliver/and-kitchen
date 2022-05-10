package tech.sperlikoliver.and_kitchen.Model.Domain

data class ShoppingListItem (
        val id : String = "",
        val name : String,
        val completed : Boolean = false,
        val userId : String = ""
    ) {
}