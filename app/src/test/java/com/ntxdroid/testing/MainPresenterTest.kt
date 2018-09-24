package com.ntxdroid.testing

import com.ntxdroid.testing.domain.MainView
import com.ntxdroid.testing.domain.Repository
import com.ntxdroid.testing.model.Item
import com.ntxdroid.testing.presenter.MainPresenter
import org.junit.Test
import org.junit.After
import org.junit.Before

import io.reactivex.Single
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.mockito.Mockito.*
import java.util.*
import org.mockito.Mockito.`when`

/**
 * MVP integration test
 *
 * This test verifies integration of all three MVP components without actually implementing
 * any of those components in the application.
 *
 * Passing on 9/24/2018
 */
class MainPresenterTest {
    private val repository: Repository = mock(Repository::class.java)
    private val view: MainView = mock(MainView::class.java)

    private val manyItems = Arrays.asList(Item(), Item())
    private val noItems = Collections.emptyList<Item>()

    private lateinit var presenter: MainPresenter

    @Before
    fun setup() {
        // Pass the trampoline scheduler to the observer for testing
        presenter = MainPresenter(view, repository, Schedulers.trampoline())
        // Change subscriber scheduler to match observer for testing
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
    }

    @After
    fun cleanUp() {
        RxJavaPlugins.reset()
    }

    @Test
    fun shouldPassItemsToView() {
        `when`(repository.getItems())
                .thenReturn(Single.just(manyItems))

        presenter.loadItems()

        verify(view).displayItems(manyItems)
    }

    @Test
    fun shouldPassNoItemsToView() {
        `when`(repository.getItems())
                .thenReturn(Single.just(noItems))

        presenter.loadItems()

        verify(view).displayNoItems()
    }

    @Test
    fun shouldHandleError() {
        `when`(repository.getItems())
                .thenReturn(Single.error { Throwable("fail") })

        presenter.loadItems()

        verify(view).displayError()
    }
}
