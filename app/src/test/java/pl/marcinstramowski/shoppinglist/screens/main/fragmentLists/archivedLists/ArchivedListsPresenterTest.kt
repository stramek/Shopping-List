package pl.marcinstramowski.shoppinglist.screens.main.fragmentLists.archivedLists

import com.nhaarman.mockito_kotlin.*
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test
import pl.marcinstramowski.shoppinglist.database.model.ShoppingItem
import pl.marcinstramowski.shoppinglist.database.model.ShoppingList
import pl.marcinstramowski.shoppinglist.database.model.ShoppingListWithItems
import pl.marcinstramowski.shoppinglist.database.sources.ShoppingListDataSource
import pl.marcinstramowski.shoppinglist.rxSchedulers.TrampolineSchedulerProvider

class ArchivedListsPresenterTest {

    private val view = mock<ArchivedListsContract.View>()
    private val shoppingListDataSource = mock<ShoppingListDataSource>()

    private lateinit var presenter: ArchivedListsPresenter

    private lateinit var mockShoppingListWithItems: ShoppingListWithItems
    private lateinit var mockShoppingLists: List<ShoppingListWithItems>

    @Before
    fun prepareTest() {
        presenter = ArchivedListsPresenter(
            view, TrampolineSchedulerProvider(), shoppingListDataSource
        )
        prepareMockValues()
    }

    private fun prepareMockValues() {
        mockShoppingListWithItems = ShoppingListWithItems().apply {
            shoppingList = ShoppingList("List name").apply { id = 123 }
            shoppingItems = listOf(
                ShoppingItem(1, "Item name 1"),
                ShoppingItem(2, "Item name 2")
            )
        }

        mockShoppingLists = listOf(
            mockShoppingListWithItems,
            mockShoppingListWithItems
        )
    }

    @Test
    fun testUpdateArchivedShoppingListsCalledOnAttach() {

        shoppingListDataSource.stub {
            on { observeArchivedListsWithItems() } doReturn Flowable.just(mockShoppingLists)
        }

        presenter.onAttach()

        verify(view).updateShoppingLists(mockShoppingLists)
    }

    @Test
    fun testUpdateArchivedShoppingListsNotCalledOnAttachWhenException() {

        shoppingListDataSource.stub {
            on { observeArchivedListsWithItems() } doReturn Flowable.error<List<ShoppingListWithItems>>(
                Throwable()
            )
        }

        presenter.onAttach()

        verify(view, never()).updateShoppingLists(mockShoppingLists)
    }


    @Test
    fun testDeleteShoppingListCalledWhenItemIsNotNull() {

        val shoppingList = ShoppingList("List name")

        presenter.deleteList(shoppingList)

        verify(shoppingListDataSource).deleteShoppingList(shoppingList)
    }

    @Test
    fun testDeleteShoppingListNotCalledWhenItemIsNull() {

        val shoppingList: ShoppingList? = null

        presenter.deleteList(shoppingList)

        verify(shoppingListDataSource, never()).deleteShoppingList(any())
    }

    @Test
    fun testShowDetailsScreenAfterShoppingListClick() {

        presenter.onShoppingListClick(mockShoppingListWithItems)

        verify(view).showListDetailsScreen(mockShoppingListWithItems.getUniqueId())
    }

    @Test
    fun testShowContextMenuOnLongShoppingListClickWhenListIsNotNull() {

        presenter.onLongShoppingListClick(mockShoppingListWithItems)

        verify(view).showContextMenu(mockShoppingListWithItems.shoppingList!!)
    }

    @Test
    fun testDontShowContextMenuOnLongShoppingListClickWhenListIsNull() {

        val nullShoppingListWithItems = ShoppingListWithItems().apply {
            shoppingList = null
        }

        presenter.onLongShoppingListClick(nullShoppingListWithItems)

        verify(view, never()).showContextMenu(any())
    }

}