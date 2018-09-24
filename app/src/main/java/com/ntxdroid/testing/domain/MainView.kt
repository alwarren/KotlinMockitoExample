package com.ntxdroid.testing.domain

import com.ntxdroid.testing.model.Item

/**
 * Created by Al Warren on 9/24/2018.
 */

interface MainView {
    fun displayItems(items: List<Item>)
    fun displayNoItems()
    fun displayError()
}