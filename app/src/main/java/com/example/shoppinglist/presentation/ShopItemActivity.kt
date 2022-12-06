package com.example.shoppinglist.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShopItem
import com.google.android.material.textfield.TextInputLayout

class ShopItemActivity : AppCompatActivity() {

    private lateinit var viewModel: ShopItemViewModel

    private lateinit var tilName: TextInputLayout
    private lateinit var tilWeight: TextInputLayout
    private lateinit var tilCount: TextInputLayout

    private lateinit var edName: EditText
    private lateinit var edWeight: EditText
    private lateinit var edCount: EditText

    private lateinit var buttonSave: Button

    private var screenMode = MODE_UNKNOWN
    private var shopItemId = ShopItem.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)
        parseIntent()
        viewModel = ViewModelProvider(this).get(ShopItemViewModel::class.java)
        initViews()
        when (screenMode) {
            MODE_EDIT -> launchEditMode()
            MODE_ADD -> launchAddMode()
        }
    }

    private fun launchEditMode() {
        viewModel.getShopItem(shopItemId)
        viewModel.shopItem.observe(this) {
            edName.setText(it.name)
            edWeight.setText(it.weight.toString())
            edCount.setText(it.count.toString())
        }

        buttonSave.setOnClickListener {
            viewModel.editShopItem(
                edName.text?.toString(),
                edWeight.text?.toString(),
                edCount.text.toString()
            )
        }
    }

    private fun launchAddMode() {
        buttonSave.setOnClickListener {
            viewModel.addShopItem(
                edName.text?.toString(),
                edWeight.text?.toString(),
                edCount.text.toString()
            )
        }
    }

    private fun parseIntent() {
        if (!intent.hasExtra(EXTRA_SCREEN_MODE))
            throw RuntimeException("Param screen mode is absent")
        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)
        if (mode != MODE_ADD && mode != MODE_EDIT)
            throw RuntimeException("Unknown screen mode  $mode")
        screenMode = mode
        if (mode == MODE_EDIT) {
            if (!intent.hasExtra(EXTRA_SHOP_ITEM_ID))
                throw RuntimeException("Param shop item id is absent")
            shopItemId = intent.getIntExtra(EXTRA_SHOP_ITEM_ID, ShopItem.UNDEFINED_ID)
        }
    }

    private fun initViews() {
        tilName = findViewById(R.id.textInputName)
        tilWeight = findViewById(R.id.textInputWeight)
        tilCount = findViewById(R.id.textInputLayoutCount)
        edName = findViewById(R.id.editTextName)
        edWeight = findViewById(R.id.editTextWeight)
        edCount = findViewById(R.id.editTextCount)
        buttonSave = findViewById(R.id.buttonSave)
    }

    companion object {

        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val EXTRA_SHOP_ITEM_ID = "extra_shop_item_id"
        private const val MODE_ADD = "mode_add"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_UNKNOWN = ""

        fun newIntentAddItem(context: Context): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
            return intent
        }

        fun newIntentEditIntent(context: Context, shopItemId: Int): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_SHOP_ITEM_ID, shopItemId)
            return intent
        }
    }
}