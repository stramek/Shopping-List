package pl.marcinstramowski.shoppinglist.screens.main

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Test
import pl.marcinstramowski.shoppinglist.database.model.ShoppingList
import pl.marcinstramowski.shoppinglist.database.sources.ShoppingListDataSource

class MainPresenterTest {

    private val view = mock<MainContract.View>()
    private val shoppingListDataSource = mock<ShoppingListDataSource>()

    private lateinit var presenter: MainPresenter

    @Before
    fun prepareTest() {
        presenter = MainPresenter(view, shoppingListDataSource)
    }

    @Test
    fun testShowNewListDialogOnFabClick() {

        presenter.onFabButtonClick()

        verify(view).showAddNewListDialog()
    }

    @Test
    fun testInsertOrUpdateShoppingListWhenCreateNewListCalled() {

        val shoppingList = ShoppingList("New list name")

        presenter.createNewList(shoppingList.listName)

        verify(shoppingListDataSource).insertOrUpdateShoppingList(shoppingList)
    }
}