package com.example.shoppinglist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shoppinglist.domain.ShopItem
import com.example.shoppinglist.domain.ShopListRepository
import kotlin.random.Random

object ShopListRepositoryImpl : ShopListRepository {

    private val shopListLD = MutableLiveData<List<ShopItem>>()

    private val shopList = sortedSetOf<ShopItem>({ p0, p1 -> p0.id.compareTo(p1.id) })

    private var autoIncrementId = 0

    init {
        for (i in 0 until 100   ) {
            val item = ShopItem("Name $i", i.toDouble(), i, Random.nextBoolean())
            addShopItem(item)
        }
    }

    override fun getShopList(): LiveData<List<ShopItem>> {
        return shopListLD
    }

    override fun getItemId(itemShopId: Int): ShopItem {
        return shopList.find {
            it.id == itemShopId
        } ?: throw RuntimeException("Element with id $itemShopId not found")
    }

    override fun addShopItem(itemShop: ShopItem) {
        if (itemShop.id == ShopItem.UNDEFINED_ID) itemShop.id = autoIncrementId++
        shopList.add(itemShop)
        updateList()
    }

    override fun deleteShopItem(itemShop: ShopItem) {
        shopList.remove(itemShop)
        updateList()
    }

    override fun editListItem(itemShop: ShopItem) {
        val oldElement = getItemId(itemShop.id)
        shopList.remove(oldElement)
        addShopItem(itemShop)
    }

    private fun updateList() {
        shopListLD.value = shopList.toList()
    }
}
