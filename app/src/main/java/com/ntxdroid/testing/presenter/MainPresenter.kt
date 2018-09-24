package com.ntxdroid.testing.presenter

import com.ntxdroid.testing.domain.MainView
import com.ntxdroid.testing.domain.Repository
import com.ntxdroid.testing.model.Item
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.Scheduler
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

/**
 * Created by Al Warren on 9/24/2018.
 */

class MainPresenter(private val view: MainView, private val repository: Repository,
                    private val scheduler: Scheduler) {
    private val mCompositeDisposable: CompositeDisposable = CompositeDisposable()

    fun loadItems() {
        mCompositeDisposable.add(

                // Get the list from the repository
                repository.getItems()

                        // Run async on the io scheduler
                        .subscribeOn(Schedulers.io())

                        // Use a constructor parameter for the observer scheduler
                        // so we can change it in unit tests
                        .observeOn(scheduler)

                        // Use a disposable observer with a list that expects
                        // a single response object
                        .subscribeWith(object : DisposableSingleObserver<List<Item>>() {

                            // Call back to MainActivity via the View interface

                            override fun onSuccess(items: List<Item>) {
                                if (items.isEmpty()) {
                                    view.displayNoItems()
                                } else {
                                    view.displayItems(items)
                                }
                            }

                            override fun onError(e: Throwable) {
                                view.displayError()
                            }
                        }))
    }
}