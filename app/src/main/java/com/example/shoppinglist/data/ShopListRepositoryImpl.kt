package com.example.shoppinglist.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.shoppinglist.domain.ShopItem
import com.example.shoppinglist.domain.ShopListRepository

class ShopListRepositoryImpl(application: Application) : ShopListRepository {

    private val shopListDao = AppDatabase.getInstance(application).shopListDao()
    private val mapper = ShopListMapper()

    override suspend fun getItemId(itemShopId: Int): ShopItem {
        val dbModel = shopListDao.getShopItem(itemShopId)
        return mapper.mapDbModelToEntity(dbModel)
    }

    override suspend fun addShopItem(itemShop: ShopItem) {
        shopListDao.addShopItem(mapper.mapEntityToDbModel(itemShop))
    }

    override suspend fun deleteShopItem(itemShop: ShopItem) {
        shopListDao.deleteShopItem(itemShop.id)
    }

    override suspend fun editListItem(itemShop: ShopItem) {
        shopListDao.addShopItem(mapper.mapEntityToDbModel(itemShop))
    }

    override fun getShopList(): LiveData<List<ShopItem>> = Transformations.map(
        shopListDao.getShopList()
    ) {
        mapper.mapListDbModelToListEntity(it)
    }

//    override fun getShopList(): LiveData<List<ShopItem>> = MediatorLiveData<List<ShopItem>>().apply {
//        addSource(shopListDao.getShopList()) {
//            value = mapper.mapListDbModelToListEntity(it)
//        }
//    }
}
