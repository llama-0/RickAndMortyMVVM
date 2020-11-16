package com.llama.rick_and_morty_mvvm.domain.utils

// for reference, not used
open class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set // Allow external read but not write

    /**
     * Let an event handler function handle the event, if
     * not handled already and set the handled status
     */
    fun handleBy(eventHandler: (T) -> Boolean) {
        if (!hasBeenHandled) {
            hasBeenHandled = eventHandler(content)
        }
    }
}