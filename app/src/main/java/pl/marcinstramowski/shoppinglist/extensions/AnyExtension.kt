package pl.marcinstramowski.shoppinglist.extensions

fun <T> T?.ifNotNull(operation: (T) -> Unit) {
    if (this != null) {
        operation(this)
    }
}

fun <T> T.doIf(condition: () -> Boolean, operationTrue: (T) -> Unit, operationFalse: (T) -> Unit) {
    if (condition()) {
        operationTrue(this)
    } else {
        operationFalse(this)
    }
}

fun <T> T?.condIfNotNull(condition: () -> Boolean, operationTrue: (T) -> Unit, operationFalse: (T) -> Unit) {
    this.ifNotNull {
        it.doIf(condition, operationTrue, operationFalse)
    }
}