package tech.sperlikoliver.and_kitchen.Model.Domain

data class ShoppingListItem (
        val id : String = "",
        val name : String,
        var completed : Boolean = false
    ) {

}