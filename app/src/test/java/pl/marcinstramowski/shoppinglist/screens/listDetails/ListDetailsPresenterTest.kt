package pl.marcinstramowski.shoppinglist.screens.listDetails

import com.nhaarman.mockito_kotlin.*
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test
import pl.marcinstramowski.shoppinglist.database.model.ShoppingItem
import pl.marcinstramowski.shoppinglist.database.model.ShoppingList
import pl.marcinstramowski.shoppinglist.database.sources.ShoppingListDataSource
import pl.marcinstramowski.shoppinglist.rxSchedulers.TrampolineSchedulerProvider

class ListDetailsPresenterTest {

    private val view = mock<ListDetailsContract.View>()
    private val shoppingListDataSource = mock<ShoppingListDataSource>()

    private lateinit var presenter: ListDetailsPresenter

    private lateinit var mockShoppingItem: ShoppingItem
    private lateinit var mockShoppingList: ShoppingList
    private lateinit var mockShoppingListItems: List<ShoppingItem>

    @Before
    fun prepareTest() {
        presenter = ListDetailsPresenter(
            view, TrampolineSchedulerProvider(), shoppingListDataSource
        )
        prepareMockValues()
    }

    private fun prepareMockValues() {
        mockShoppingItem = ShoppingItem(123, "Name 1123123")
        mockShoppingList = ShoppingList("List name").apply { id = 123 }
        mockShoppingListItems = listOf(
            ShoppingItem(1, "Name 1"),
            ShoppingItem(2, "Name 2"),
            ShoppingItem(3, "Name 3")
        )
    }

    @Test
    fun testShowListNameWhenObserveShoppingListCalled() {

        shoppingListDataSource.stub {
            on { observeShoppingListById(mockShoppingList.getUniqueId()) } doReturn Flowable.just(
                mockShoppingList
            )
            on { observeShoppingItemsByListId(mockShoppingList.getUniqueId()) } doReturn Flowable.just(
                mockShoppingListItems
            )
        }

        presenter.observeShoppingListId(mockShoppingList.getUniqueId())

        verify(view).showShoppingListName(mockShoppingList.listName)
    }

    @Test
    fun testShowListNotCalledWhenObserveShoppingListThrows() {

        shoppingListDataSource.stub {
            on {
                observeShoppingListById(mockShoppingList.getUniqueId())
            } doReturn Flowable.error<ShoppingList>(Throwable())
            on { observeShoppingItemsByListId(mockShoppingList.getUniqueId()) } doReturn Flowable.just(
                mockShoppingListItems
            )
        }

        presenter.observeShoppingListId(mockShoppingList.getUniqueId())

        verify(view, never()).showShoppingListName(mockShoppingList.listName)
    }

    @Test
    fun testUpdateListWhenObserveShoppingItemsCalled() {

        shoppingListDataSource.stub {
            on { observeShoppingListById(mockShoppingList.getUniqueId()) } doReturn Flowable.just(
                mockShoppingList
            )
            on { observeShoppingItemsByListId(mockShoppingList.getUniqueId()) } doReturn Flowable.just(
                mockShoppingListItems
            )
        }

        presenter.observeShoppingListId(mockShoppingList.getUniqueId())

        verify(view).updateList(mockShoppingListItems)
    }

    @Test
    fun testUpdateListNotCalledWhenObserveShhoppingItemsThrows() {

        shoppingListDataSource.stub {
            on { observeShoppingListById(mockShoppingList.getUniqueId()) } doReturn Flowable.just(
                mockShoppingList
            )
            on { observeShoppingItemsByListId(mockShoppingList.getUniqueId())
            } doReturn Flowable.error<List<ShoppingItem>>(Throwable())
        }

        presenter.observeShoppingListId(mockShoppingList.getUniqueId())

        verify(view, never()).updateList(mockShoppingListItems)
    }

    @Test
    fun testCleanAddNewItemFieldWhenAddShoppingItemCalled() {

        presenter.addShoppingItem(123, "Name 1")

        verify(view).cleanAddNewItemField()
    }

    @Test
    fun testInsertOrUpdateShoppingItemCalledWhenShoppingNameNotBlank() {

        presenter.addShoppingItem(123, "Name")

        verify(shoppingListDataSource).insertOrUpdateShoppingItem(ShoppingItem(123, "Name"))
    }

    @Test
    fun testInsertOrUpdateShoppingItemNotCalledWHenShoppingNameBlank() {

        presenter.addShoppingItem(123, "")

        verify(shoppingListDataSource, never()).insertOrUpdateShoppingItem(any())
    }

    @Test
    fun testDeleteScoppingItemCalledAfterRemoveShoppingItemCalled() {

        presenter.removeShoppingItem(mockShoppingItem)

        verify(shoppingListDataSource).deleteShoppingItem(mockShoppingItem)
    }

    @Test
    fun testSetShoppingItemCompletedWhenChangeShoppingItemNameCalled() {

        presenter.changeShoppingListCompletedState(mockShoppingItem)

        verify(shoppingListDataSource).setShoppingItemCompleted(mockShoppingItem, !mockShoppingItem.isCompleted)
    }

    @Test
    fun testShowContextMenuCalledOnLongShoppingItemClick() {

        presenter.onLongShoppingItemClick(mockShoppingItem)

        verify(view).showContextMenu(mockShoppingItem)
    }

    @Test
    fun testUpdateShoppingItemNameCalledWhenChangeShoppingItemName() {

        val newName = "new name123"

        presenter.changeShoppingItemName(mockShoppingItem, newName)

        verify(shoppingListDataSource).updateShoppingItemName(mockShoppingItem, newName)
    }

    @Test
    fun testUpdateShoppingItemNameNotCalledWhenChangeShoppingItemNullPassed() {

        presenter.changeShoppingItemName(null, "new name")

        verify(shoppingListDataSource, never()).updateShoppingItemName(any(), any())

    }
}