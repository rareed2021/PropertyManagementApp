package com.test.propertymanagementapp.ui.common

import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import androidx.databinding.*
import com.bumptech.glide.Glide
import com.test.propertymanagementapp.data.models.Todo
import com.test.propertymanagementapp.data.models.enums.TodoPriority


@BindingAdapter("android:customVisibility")
fun setVisibility(view: View, visible: Boolean) {
    view.visibility = if (visible) View.VISIBLE else View.GONE
}

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String) = Glide.with(view).load(url).into(view)


abstract class SpinnerBindingAdapter {
    companion object {
        @JvmStatic
        @BindingAdapter(value = ["android:selectedValueAttrChanged"])
        fun setListener(view: Spinner, listener: InverseBindingListener?) {
            if (listener != null) {
                view.onItemSelectedListener = object : OnItemSelectedListener {
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
    }
}

@InverseBindingMethods(
    InverseBindingMethod(
        type = SpinnerStringBindingAdapter::class,
        attribute = "android:selectedValue",
        event = "android:selectedValueChanged",
        method = "android:getSelectedValue"
    )
)
class SpinnerStringBindingAdapter : SpinnerBindingAdapter() {
    companion object {
//
//        @JvmStatic
//        @BindingAdapter(value = ["android:selectedValueAttrChanged"])
//        fun setListener(view: Spinner, listener: InverseBindingListener?) {
//            if (listener != null) {
//                view.onItemSelectedListener = object : OnItemSelectedListener {
//                    override fun onItemSelected(
//                        parent: AdapterView<*>?,
//                        view: View?,
//                        position: Int,
//                        id: Long
//                    ) {
//                        listener.onChange()
//                    }
//
//                    override fun onNothingSelected(parent: AdapterView<*>?) {
//                    }
//                }
//            }
//        }

        @JvmStatic
        @BindingAdapter("android:selectedValue")
        fun setSpinner(
            view: Spinner,
            newSelectedValue: String?
        ) {
            if (newSelectedValue != null) {
                val pos = (view.adapter as? ArrayAdapter<String>)?.getPosition(newSelectedValue)
                if (pos != null) {
                    view.setSelection(pos, true)
                }
            }
        }

        @JvmStatic
        @BindingAdapter("android:selectedValue")
        fun setSpinner(
            view: Spinner,
            newSelectedValue: TodoPriority?
        ) {
            if (newSelectedValue != null) {
                val pos =
                    (view.adapter as? ArrayAdapter<TodoPriority>)?.getPosition(newSelectedValue)
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



@InverseBindingMethods(
    InverseBindingMethod(
        type = SpinnerStringBindingAdapter::class,
        attribute = "android:selectedValue",
        event = "android:selectedValueChanged",
        method = "android:getSelectedValue"
    )
)
class SpinnerEnumBindingAdapter : SpinnerBindingAdapter() {
    companion object {


        @JvmStatic
        @InverseBindingAdapter(
            attribute = "android:selectedValue"
        )
        fun captureSelectedValue(view: Spinner): TodoPriority? {
            return view.selectedItem as TodoPriority
        }

        @JvmStatic
        @BindingAdapter("android:selectedValue")
        fun setSpinner(
            view: Spinner,
            newSelectedValue: TodoPriority?
        ) {
            if (newSelectedValue != null) {
                val pos =
                    (view.adapter as? ArrayAdapter<TodoPriority>)?.getPosition(newSelectedValue)
                if (pos != null) {
                    view.setSelection(pos, true)
                }
            }
        }

    }
}
