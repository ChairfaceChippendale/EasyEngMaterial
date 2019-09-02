package com.ujujzk.ee.ui.tools

import java.lang.IllegalStateException
import kotlin.reflect.KProperty

/**
 * Custom delegate that allows to assign a variable only ones
 *
 * @param <T> type of variable.
 *
 */
class Ones<T> {
    private var value: T? = null

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return value ?: throw IllegalStateException("Must be initialized before use")
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
        this.value = this.value ?: value
    }
}