package pl.marcinstramowski.shoppinglist.utils

import android.support.v7.util.DiffUtil

class GenericDiffCallback<T : UniqueId>(
        private val oldList: List<T>,
        private val newList: List<T>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size
    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int)
            = oldList[oldItemPosition].getUniqueId() == newList[newItemPosition].getUniqueId()

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}