package com.test.propertymanagementapp.ui.common

import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import androidx.databinding.*
import com.bumptech.glide.Glide


@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String) = Glide.with(view).load(url).into(view)



@InverseBindingMethods(InverseBindingMethod(
    type=SpinnerValueBindingAdapter::class,
    attribute = "android:selectedValue",
    event = "android:selectedValueChanged",
    method = "android:getSelectedValue"
)
)
class SpinnerValueBindingAdapter {
    companion object {

        @JvmStatic
        @BindingAdapter(value = ["android:selectedValueAttrChanged"])
        fun setListener(view:Spinner, listener:InverseBindingListener?){
            if(listener!=null){
                view.onItemSelectedListener = object:OnItemSelectedListener{
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        listener.onChange()
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }
                }
            }
        }
        @JvmStatic
        @BindingAdapter("android:selectedValue")
        fun setSpinner(
            view: Spinner,
            newSelectedValue: String?
        ) {
            if(newSelectedValue !=null) {
                val pos = (view.adapter as? ArrayAdapter<String>)?.getPosition(newSelectedValue)
                if (pos != null) {
                    view.setSelection(pos, true)
                }
            }
        }

        @JvmStatic
        @InverseBindingAdapter(
            attribute = "android:selectedValue"
        )
        fun captureSelectedValue(view: Spinner): String? {
            return view.selectedItem as String?
        }
    }
}


