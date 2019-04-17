package com.nsr.osama.assetmanager

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import com.nsr.osama.assetmanager.database.EntryModel
import com.nsr.osama.assetmanager.database.MyRoomConverters
import kotlinx.android.synthetic.main.fragment_add_entry_bottom_sheet.*
import kotlinx.android.synthetic.main.fragment_add_entry_bottom_sheet.view.*
import java.util.*

class EntryBSDF : BottomSheetDialogFragment() {

    private lateinit var aftermath: () -> Unit

    enum class OperationType(val value: Byte) {
        Insert(0), Update(1), Delete(2)
    }

    companion object {
        private const val OPERATION_TYPE = "OPERATION_TYPE"

        fun newInstance(operationType: OperationType, entryModelBundle: Bundle? = null, aftermath: () -> Unit = { Log.d(">>S", "Suck it") }): EntryBSDF =
                EntryBSDF().also {
                    it.aftermath = aftermath
                    it.arguments = Bundle().apply {
                        putByte(OPERATION_TYPE, operationType.value)
                        entryModelBundle?.let { putAll(entryModelBundle) }
                    }
                }
    }

    // as an override for no reason than to hide the TAG ;p
    fun show(manager: FragmentManager) = show(manager, "TAG_fragment_add_entry_bottom_sheet")

    private fun Bundle.toEntryModel() = EntryModel(
            getInt("id"),
            MyRoomConverters.fromTimestamp(getLong("time"))!!,
            getDouble("price"),
            getString("description")!!,
            getInt("quantity"),
            getByte("category"),
            getBoolean("isIncrease")
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            if (arguments?.getByte(EntryBSDF.OPERATION_TYPE) == OperationType.Delete.value) {
                MainActivity.entryViewModel.delete(arguments!!.toEntryModel())
                dismiss()
                showSnackBar("Deleted!")
                null
                //TODO: update list adaptor , all beneath the delete entry
            } else inflater.inflate(R.layout.fragment_add_entry_bottom_sheet, container, false).apply {
                numberPicker.minValue = 1
                numberPicker.maxValue = 99
                numberPicker.wrapSelectorWheel = true
                submitBtn.setOnClickListener(::submitBtnClicked)
                if (arguments?.getByte(EntryBSDF.OPERATION_TYPE) == OperationType.Update.value) {
                    priceEditText.setText(arguments?.getDouble("price").toString())
                    descriptionEditText.setText((arguments?.getString("description").toString()))
                    numberPicker.value = arguments?.getInt("quantity")!!
                    toggleButton.isChecked = arguments?.getBoolean("isIncrease")!!
                }
            }

    private fun submitBtnClicked(view: View) {
        val price = priceEditText.text.toString()
        val description = descriptionEditText.text.toString()
        val quantity = numberPicker.value
        val isIncrease = toggleButton.isChecked
        val time = MyRoomConverters.fromTimestamp(arguments?.getLong("time")) ?: Date()
        if (price.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(activity, "Empty Price / Description!", Toast.LENGTH_SHORT).show()
        } else {
            val id: Int? = arguments?.getInt("id").let { return@let if (it == 0) null else it }
            val entryModel = EntryModel(id, time, price.toDouble(), description, quantity,
                    arguments?.getByte("category")!!, isIncrease)
            val actionType: String =
                    when (arguments?.getByte(EntryBSDF.OPERATION_TYPE)) {
                        OperationType.Insert.value -> {
                            MainActivity.entryViewModel.insert(entryModel)
                            if (arguments?.getBoolean("isNewCategory") == true) this.aftermath()
                            "Inserted"
                        }
                        OperationType.Update.value -> {
                            //get the entry by id, keep it the same along with the Date, category
                            MainActivity.entryViewModel.update(entryModel)
                            "Updated"
                            //TODO: update list adaptor , detailsEntry
                        }
                        else -> "Internal Error! Undefined operation code ${arguments?.getByte(EntryBSDF.OPERATION_TYPE)}"
                    }
            showSnackBar(if (actionType.startsWith("Internal Error", true)) actionType else "Done $actionType $price x$quantity")
        }
        dismiss()
    }

    private fun showSnackBar(msg: String) =
            Snackbar.make(activity!!.findViewById(R.id.fab), msg, Snackbar.LENGTH_LONG)
                    .setAction("Action", null)
                    .show()

}