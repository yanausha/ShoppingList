package com.example.shoppinglist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShopItem
import com.google.android.material.textfield.TextInputLayout

class ShopItemFragment(
    private val screenMode: String = MODE_UNKNOWN,
    private val shopItemId: Int = ShopItem.UNDEFINED_ID
) : Fragment() {

    private lateinit var viewModel: ShopItemViewModel

    private lateinit var tilName: TextInputLayout
    private lateinit var tilWeight: TextInputLayout
    private lateinit var tilCount: TextInputLayout

    private lateinit var edName: EditText
    private lateinit var edWeight: EditText
    private lateinit var edCount: EditText

    private lateinit var buttonSave: Button


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shop_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parseParams()
        viewModel = ViewModelProvider(this).get(ShopItemViewModel::class.java)
        initViews()
        addTextChangeListeners()
        launchRightMode()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.errorInputName.observe(this) {
            val message = if (it) getString(R.string.error_input_name) else null
            tilName.error = message
        }
        viewModel.errorInputWeight.observe(this) {
            val message = if (it) getString(R.string.error_input_weght) else null
            tilName.error = message
        }
        viewModel.errorInputCount.observe(this) {
            val message = if (it) getString(R.string.error_input_count) else null
            tilName.error = message
        }
        viewModel.checkActivity.observe(this) {
            finish()
        }
    }

    private fun launchRightMode() {
        when (screenMode) {
            MODE_EDIT -> launchEditMode()
            MODE_ADD -> launchAddMode()
        }
    }

    private fun addTextChangeListeners() {
        edName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetInputErrorName()
            }

            override fun afterTextChanged(p0: Editable?) {}
        })
        edWeight.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetInputErrorWeight()
            }

            override fun afterTextChanged(p0: Editable?) {}
        })
        edCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetInputErrorCount()
            }

            override fun afterTextChanged(p0: Editable?) {}
        })
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

    private fun parseParams() {

        if (screenMode != MODE_ADD && screenMode != MODE_ADD)
            throw RuntimeException("Param screen mode is absent")

        if (screenMode == MODE_EDIT && shopItemId == ShopItem.UNDEFINED_ID)
            throw RuntimeException("Param shop item id is absent")
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