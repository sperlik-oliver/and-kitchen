package tech.sperlikoliver.and_kitchen.Model.Room.DAO

import androidx.room.*
import tech.sperlikoliver.and_kitchen.Model.Room.Entity.ShoppingListItemRoom

@Dao
interface ShoppingListDao {
    @Query("SELECT * FROM shopping_list")
    suspend fun getShoppingList() : MutableList<ShoppingListItemRoom>

    @Insert
    suspend fun createShoppingListItem(shoppingListItem: ShoppingListItemRoom)

    @Update
    suspend fun editShoppingListItem(shoppingListItem: ShoppingListItemRoom)

    @Delete
    suspend fun deleteShoppingListItem(shoppingListItem: ShoppingListItemRoom)
}