package tech.sperlikoliver.and_kitchen.Model.DAO

import androidx.room.*
import tech.sperlikoliver.and_kitchen.Model.Domain.DAO.ShoppingListItemRoom

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