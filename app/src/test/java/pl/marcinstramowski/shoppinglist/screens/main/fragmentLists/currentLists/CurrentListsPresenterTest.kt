package pl.marcinstramowski.shoppinglist.screens.main.fragmentLists.currentLists

import com.nhaarman.mockito_kotlin.*
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test
import pl.marcinstramowski.shoppinglist.database.model.ShoppingItem
import pl.marcinstramowski.shoppinglist.database.model.ShoppingList
import pl.marcinstramowski.shoppinglist.database.model.ShoppingListWithItems
import pl.marcinstramowski.shoppinglist.database.sources.ShoppingListDataSource
import pl.marcinstramowski.shoppinglist.rxSchedulers.TrampolineSchedulerProvider

class CurrentListsPresenterTest {

    private val view = mock<CurrentListsContract.View>()
    private val shoppingListDataSource = mock<ShoppingListDataSource>()

    private lateinit var presenter: CurrentListsPresenter

    private val mockShoppingListWithItems = ShoppingListWithItems().apply {
        shoppingList = ShoppingList("List name").apply { id = 123 }
        shoppingItems = listOf(
            ShoppingItem(1, "Item name 1"),
            ShoppingItem(2, "Item name 2")
        )
    }

    private val mockShoppingLists = listOf(
        mockShoppingListWithItems,
        mockShoppingListWithItems
    )

    @Before
    fun prepareTest() {
        presenter = CurrentListsPresenter(
            view, TrampolineSchedulerProvider(), shoppingListDataSource
        )
    }

    @Test
    fun testUpdateCurrentShoppingListsCalledOnAttach() {

        shoppingListDataSource.stub {
            on { observeCurrentListsWithItems() } doReturn Flowable.just(mockShoppingLists)
        }

        presenter.onAttach()

        verify(view).updateShoppingLists(mockShoppingLists)
    }

    @Test
    fun testUpdateArchivedShoppingListsNotCalledOnAttachWhenException() {

        shoppingListDataSource.stub {
            on { observeCurrentListsWithItems() } doReturn Flowable.error<List<ShoppingListWithItems>>(
                Throwable()
            )
        }

        presenter.onAttach()

        verify(view, never()).updateShoppingLists(mockShoppingLists)
    }



}