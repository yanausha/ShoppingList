package com.example.shoppinglist.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shoppinglist.data.ShopListRepositoryImpl
import com.example.shoppinglist.domain.AddShopItemUseCase
import com.example.shoppinglist.domain.EditShopItemUseCase
import com.example.shoppinglist.domain.GetShopItemUseCase
import com.example.shoppinglist.domain.ShopItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class ShopItemViewModel(application: Application) : AndroidViewModel(application) {

    private val scope = CoroutineScope(Dispatchers.IO)

    private val repository = ShopListRepositoryImpl(application)

    private val getShopItemUseCase = GetShopItemUseCase(repository)
    private val addShopItemUseCase = AddShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    private val _errorInputWeight = MutableLiveData<Boolean>()
    val errorInputWeight: LiveData<Boolean>
        get() = _errorInputWeight

    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount: LiveData<Boolean>
        get() = _errorInputCount

    private val _shopItem = MutableLiveData<ShopItem>()
    val shopItem: LiveData<ShopItem>
        get() = _shopItem

    private val _checkActivity = MutableLiveData<Unit>()
    val checkActivity: LiveData<Unit>
        get() = _checkActivity

    fun getShopItem(shopItemId: Int) {
        scope.launch {
            val item = getShopItemUseCase.getItemId(shopItemId)
            _shopItem.value = item
        }
    }

    fun addShopItem(inputName: String?, inputWeight: String?, inputCount: String?) {
        val name = parseName(inputName)
        val weight = parseWeight(inputWeight)
        val count = parseCount(inputCount)
        val fieldsValid = validateInput(name, weight, count)
        if (fieldsValid) {
            scope.launch {
                val shopItem = ShopItem(name, weight, count, true)
                addShopItemUseCase.addShopItem(shopItem)
                finishWork()
            }

        }
    }

    fun editShopItem(inputName: String?, inputWeight: String?, inputCount: String?) {
        val name = parseName(inputName)
        val weight = parseWeight(inputWeight)
        val count = parseCount(inputCount)
        val fieldsValid = validateInput(name, weight, count)
        if (fieldsValid) {
            _shopItem.value?.let {
                scope.launch {
                    val item = it.copy(name = name, weight = weight, count = count)
                    editShopItemUseCase.editListItem(item)
                    finishWork()
                }
            }
        }
    }

    private fun parseName(inputName: String?): String {
        return inputName?.trim() ?: ""
    }

    private fun parseWeight(inputWeight: String?): Double {
        return try {
            inputWeight?.trim()?.toDouble() ?: 0.0
        } catch (e: Exception) {
            0.0
        }
    }

    private fun parseCount(inputCount: String?): Int {
        return try {
            inputCount?.trim()?.toInt() ?: 0
        } catch (e: Exception) {
            0
        }
    }

    private fun validateInput(name: String, weight: Double, count: Int): Boolean {
        var result = true
        if (name.isBlank()) {
            result = false
            _errorInputName.value = true

        }
        if (weight < 0.0) {
            result = false
            _errorInputWeight.value = true
        }
        if (count < 0) {
            result = false
            _errorInputCount.value = true
        }

        return result
    }

    fun resetInputErrorName() {
        _errorInputName.value = false
    }

    fun resetInputErrorWeight() {
        _errorInputWeight.value = false
    }

    fun resetInputErrorCount() {
        _errorInputCount.value = false
    }

    fun finishWork() {
        _checkActivity.value = Unit
    }

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }
}