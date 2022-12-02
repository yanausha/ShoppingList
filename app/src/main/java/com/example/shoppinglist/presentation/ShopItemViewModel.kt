package com.example.shoppinglist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppinglist.data.ShopListRepositoryImpl
import com.example.shoppinglist.domain.AddShopItemUseCase
import com.example.shoppinglist.domain.EditShopItemUseCase
import com.example.shoppinglist.domain.GetShopItemUseCase
import com.example.shoppinglist.domain.ShopItem

class ShopItemViewModel : ViewModel() {

    private val repository = ShopListRepositoryImpl

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

    fun getShopItem(shopItemId: Int) {
        val item = getShopItemUseCase.getItemId(shopItemId)
    }

    fun addShopItem(inputName: String?, inputWeight: String?, inputCount: String?) {
        val name = parseName(inputName)
        val weight = parseWeight(inputWeight)
        val count = parseCount(inputCount)
        val fieldsValid = validateInput(name, weight, count)
        if (fieldsValid) {
            val shopItem = ShopItem(name, weight, count, true)
            addShopItemUseCase.addShopItem(shopItem)
        }
    }

    fun editShopItem(inputName: String?, inputWeight: String?, inputCount: String?) {
        val name = parseName(inputName)
        val weight = parseWeight(inputWeight)
        val count = parseCount(inputCount)
        val fieldsValid = validateInput(name, weight, count)
        if (fieldsValid) {
            val shopItem = ShopItem(name, weight, count, true)
            editShopItemUseCase.editListItem(shopItem)
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

}