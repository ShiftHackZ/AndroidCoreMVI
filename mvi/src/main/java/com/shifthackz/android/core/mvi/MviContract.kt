@file:Suppress("unused")

package com.shifthackz.android.core.mvi

/**
 * MVI State is a data model that stores information about current state of UI components
 * (for example Button enabled/disabled state, visible/invisible state, etc).
 */
interface MviState

/**
 * MVI Intent is a data model that describe events/actions that happen on UI layer (for example
 * Button clicks, CheckBox updates, etc) and deliver them to the [MviViewModel].
 *
 * It is responsibility of [MviViewModel] to handle the intents and decide if UI state should be
 * updated, or some effect should be delivered back to UI.
 */
interface MviIntent

/**
 * MVI Effect is data model that define some business logic events that should be handled by UI
 * layer, but the changes does not actually affect UI state change of the UI component (for example
 * it can be events like keyboard hide or some navigation action, etc).
 */
interface MviEffect

/**
 * Final instance of empty [MviState].
 *
 * This should be used as a generic type when ViewModel does not need to handle UI states.
 */
object EmptyState : MviState

/**
 * Final instance of empty [MviIntent].
 *
 * This should be used as a generic type when ViewModel does not need to handle UI intents (actions
 * or events from UI layer).
 */
object EmptyIntent : MviIntent

/**
 * Final instance of empty [MviEffect].
 *
 * This should be used as a generic type when UI does not need to handle effects.
 */
object EmptyEffect : MviEffect
