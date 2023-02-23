package com.example.shoppinglist.data

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.example.shoppinglist.domain.ShopItem
import com.example.shoppinglist.presentation.ShopListApp
import javax.inject.Inject

class ContentProvider : ContentProvider() {

    private val component by lazy {
        (context as ShopListApp).component
    }

    @Inject
    lateinit var shopListDao: ShopListDao

    @Inject
    lateinit var mapper: ShopListMapper

    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
        addURI("com.example.shoppinglist", "shop_items", GET_SHOP_ITEMS_QUERY)
        addURI("com.example.shoppinglist", "shop_items/#", GET_SHOP_ITEM_BY_ID_QUERY)
    }

    override fun onCreate(): Boolean {
        component.inject(this)
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        return when (uriMatcher.match(uri)) {
            GET_SHOP_ITEMS_QUERY -> {
                shopListDao.getShopListCursor()
            }
            else -> null
        }
    }

    override fun getType(uri: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        when (uriMatcher.match(uri)) {
            GET_SHOP_ITEMS_QUERY -> {
                if (values == null) return null
                val id = values.getAsInteger(SHOP_ITEM_COLUMN_ID)
                val name = values.getAsString(SHOP_ITEM_COLUMN_NAME)
                val weight = values.getAsDouble(SHOP_ITEM_COLUMN_WEIGHT)
                val count = values.getAsInteger(SHOP_ITEM_COLUMN_COUNT)
                val enabled = values.getAsBoolean(SHOP_ITEM_COLUMN_ENABLED)
                val shopItem = ShopItem(name, weight, count, enabled, id)
                shopListDao.addShopItemThroughProvider(mapper.mapEntityToDbModel(shopItem))
            }
        }
        return null
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        when (uriMatcher.match(uri)) {
            GET_SHOP_ITEMS_QUERY -> {
                val id = selectionArgs?.get(0)?.toInt() ?: -1
                return shopListDao.deleteShopItemThroughProvider(id)
            }
        }
        return 0
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        return when (uriMatcher.match(uri)) {
            GET_SHOP_ITEMS_QUERY -> {
                val id = selectionArgs?.get(0)?.toInt() ?: -1
                val newItem = shopListDao.getShopItemThroughProvider(id).copy()
                shopListDao.addShopItemThroughProvider(newItem)
                newItem.toString().length
            }
            else -> 0
        }
    }

    companion object {

        private const val GET_SHOP_ITEMS_QUERY = 100
        private const val GET_SHOP_ITEM_BY_ID_QUERY = 101
        const val SHOP_ITEM_COLUMN_ID = "id"
        const val SHOP_ITEM_COLUMN_NAME = "name"
        const val SHOP_ITEM_COLUMN_COUNT = "count"
        const val SHOP_ITEM_COLUMN_WEIGHT = "weight"
        const val SHOP_ITEM_COLUMN_ENABLED = "enabled"
    }
}