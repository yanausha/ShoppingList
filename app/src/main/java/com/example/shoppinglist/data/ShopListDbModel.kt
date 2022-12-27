package com.example.shoppinglist.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shop_items")
data class ShopListDbModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    val name: String,
    val weight: Double,
    val count: Int,
    val enabled: Boolean
)
