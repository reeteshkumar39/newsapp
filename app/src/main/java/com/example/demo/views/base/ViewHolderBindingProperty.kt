package com.example.demo.views.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class ViewHolderBindingProperty<out T : ViewBinding>(val viewBindingFactory: (View) -> T) :
    ReadOnlyProperty<RecyclerView.ViewHolder, T> {

    private var binding: T? = null

    override operator fun getValue(thisRef: RecyclerView.ViewHolder, property: KProperty<*>): T =
        binding ?: viewBindingFactory(thisRef.itemView).also { binding = it }
}
