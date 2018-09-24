package com.ntxdroid.testing.domain

import com.ntxdroid.testing.model.Item
import io.reactivex.Single

/**
 * Created by Al Warren on 9/24/2018.
 */

interface Repository {
    fun getItems(): Single<List<Item>>
}