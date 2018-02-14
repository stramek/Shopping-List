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

    private lateinit var mockShoppingList: ShoppingList
    private lateinit var mockShoppingListWithItems: ShoppingListWithItems
    private lateinit var mockShoppingLists: List<ShoppingListWithItems>

    @Before
    fun prepareTest() {
        presenter = CurrentListsPresenter(
            view, TrampolineSchedulerProvider(), shoppingListDataSource
        )
        prepareMockValues()
    }

    private fun prepareMockValues() {
        mockShoppingList = ShoppingList("List name").apply { id = 123 }
        mockShoppingListWithItems = ShoppingListWithItems().apply {
            shoppingList = mockShoppingList
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

    @Test
    fun testListArchivedWhenNotNull() {

        presenter.archiveList(mockShoppingList)

        verify(shoppingListDataSource).archiveShoppingList(mockShoppingList)
    }

    @Test
    fun testListNotArchivedWhenNull() {

        presenter.archiveList(null)

        verify(shoppingListDataSource, never()).archiveShoppingList(any())
    }

    @Test
    fun testListEditedWhenNotNull() {
        val newName = "newName"

        presenter.editListName(mockShoppingList, newName)

        verify(shoppingListDataSource).updateShoppingListName(mockShoppingList, newName)
    }

    @Test
    fun testListNotEditedWhenNull() {
        val newName = "newName"

        presenter.editListName(null, newName)

        verify(shoppingListDataSource, never()).updateShoppingListName(any(), any())
    }

    @Test
    fun testShowListDetailsOnShoppingListClick() {
        presenter.onShoppingListClick(mockShoppingListWithItems)

        verify(view).showListDetailsScreen(mockShoppingListWithItems.getUniqueId())
    }

    @Test
    fun testShowContextMenuOnListLongClickWhenNotNull() {
        presenter.onShoppingListLongClick(mockShoppingListWithItems)

        verify(view).showContextMenu(mockShoppingListWithItems.shoppingList!!)
    }

    @Test
    fun testDontShowContextMenuOnListLongClickWhenNull() {
        mockShoppingListWithItems.apply { shoppingList = null }

        presenter.onShoppingListLongClick(mockShoppingListWithItems)

        verify(view, never()).showContextMenu(any())
    }
}