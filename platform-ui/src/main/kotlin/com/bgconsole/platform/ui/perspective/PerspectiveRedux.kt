package com.bgconsole.platform.ui.perspective

import com.bgconsole.platform.store.Action
import com.bgconsole.platform.store.Reducer
import com.bgconsole.platform.store.Service
import com.bgconsole.platform.store.Store
import com.bgconsole.platform.ui.Perspective
import com.bgconsole.platform.ui.component.Component
import com.bgconsole.platform.ui.component.ComponentPerspective
import com.bgconsole.platform.ui.component.PLATFORM_COMPONENT_ID
import com.bgconsole.platform.ui.project.PLATFORM_PERSPECTIVE_PROJECT

const val PLATFORM_PERSPECTIVE = "platform.ui.perspective"

val DEFAULT_COMPONENT: ComponentPerspective = ComponentPerspective()

class PerspectiveRedux(store: Store) {

    class RegisterPerspective(val perspective: Perspective) : Action(PLATFORM_PERSPECTIVE)
    class SwitchPerspective(val perspectiveId: String) : Action(PLATFORM_PERSPECTIVE)
    private class SwitchPerspectiveSucceeded(val perspectiveId: String) : Action(PLATFORM_PERSPECTIVE)
    class ClosePerspective(val perspectiveId: String) : Action(PLATFORM_PERSPECTIVE)
    private class ClosePerspectiveSucceeded(val perspectiveId: String) : Action(PLATFORM_PERSPECTIVE)
    class ShowComponent(val component: Component) : Action(PLATFORM_PERSPECTIVE_PROJECT)

    init {
        store.registerReducer(PerspectiveReducer())
        store.registerService(PerspectiveService())
        store.addToStore(PLATFORM_PERSPECTIVE, PerspectiveContent.default())
    }

    private class PerspectiveReducer : Reducer<PerspectiveContent> {
        override fun getKey(): String = PLATFORM_PERSPECTIVE

        override fun reduce(store: Store, action: Action): PerspectiveContent {
            val current = store.get(PLATFORM_PERSPECTIVE) as PerspectiveContent
            return when (action) {
                is RegisterPerspective -> current.copy(perspectives = current.perspectives + (action.perspective.getId() to action.perspective))
                is SwitchPerspectiveSucceeded -> switchPerspectiveSucceeded(store, action, current)
                is ClosePerspectiveSucceeded -> closePerspectiveSucceeded(store, action, current)
                else -> current
            }
        }

        private fun closePerspectiveSucceeded(
            store: Store,
            action: ClosePerspectiveSucceeded,
            current: PerspectiveContent
        ): PerspectiveContent {
            val newMap = current.perspectives.filterKeys { it != action.perspectiveId }
            val newStack = current.perspectiveStack.filter { it.getId() != action.perspectiveId }
            return current.copy(perspectives = newMap, perspectiveStack = newStack)
        }

        private fun switchPerspectiveSucceeded(
            store: Store,
            action: SwitchPerspectiveSucceeded,
            current: PerspectiveContent
        ): PerspectiveContent {
            val newStack: MutableList<Perspective> =
                current.perspectiveStack.filter { it.getId() != action.perspectiveId }.toMutableList()
            newStack.add(0, current.perspectives.getValue(action.perspectiveId))
            return current.copy(perspectiveStack = newStack.toList())
        }
    }

    private class PerspectiveService : Service {
        override fun getKey(): String = PLATFORM_PERSPECTIVE
        override fun execute(store: Store, action: Action) {
            when (action) {
                is ClosePerspective -> closePerspective(store, action)
                is SwitchPerspective -> switchPerspective(store, action)
                is SwitchPerspectiveSucceeded -> switchPerspectiveSucceeded(store, action)
            }
        }

        private fun switchPerspectiveSucceeded(store: Store, action: SwitchPerspectiveSucceeded) {
            val perspective =
                (store.get(PLATFORM_PERSPECTIVE) as PerspectiveContent).perspectives.getValue(action.perspectiveId)
            perspective.onGetVisibility()
        }

        private fun switchPerspective(store: Store, action: SwitchPerspective) {
            val perspective =
                (store.get(PLATFORM_PERSPECTIVE) as PerspectiveContent)
            if (perspective.perspectiveStack.isNotEmpty()) {
                perspective.perspectiveStack.first().onLoseVisibility()
            }
            store.dispatch(SwitchPerspectiveSucceeded(action.perspectiveId))
        }

        private fun closePerspective(store: Store, action: ClosePerspective) {
            val perspective =
                (store.get(PLATFORM_PERSPECTIVE) as PerspectiveContent).perspectives.getValue(action.perspectiveId)
            perspective.onClose()
            store.dispatch(ClosePerspectiveSucceeded(action.perspectiveId))
        }

    }
}

data class PerspectiveContent(val perspectives: Map<String, Perspective>, val perspectiveStack: List<Perspective>) {
    companion object {
        fun default() = PerspectiveContent(mapOf(PLATFORM_COMPONENT_ID to DEFAULT_COMPONENT), emptyList())
    }
}