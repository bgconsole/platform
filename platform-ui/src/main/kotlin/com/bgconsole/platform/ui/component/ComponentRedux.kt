package com.bgconsole.platform.ui.component

import com.bgconsole.platform.store.Action
import com.bgconsole.platform.store.Reducer
import com.bgconsole.platform.store.Service
import com.bgconsole.platform.store.Store
import com.bgconsole.platform.ui.perspective.DEFAULT_COMPONENT
import com.bgconsole.platform.ui.perspective.PerspectiveRedux

const val PLATFORM_COMPONENT = "platform.ui.component"

class ComponentRedux(store: Store) {

    class ShowComponent(val component: Component) : Action(PLATFORM_COMPONENT)

    init {
        store.registerReducer(ComponentReducer())
        store.registerService(ComponentService())
        store.addToStore(PLATFORM_COMPONENT, ComponentContent.default())
    }

    private class ComponentReducer : Reducer<ComponentContent> {
        override fun getKey(): String = PLATFORM_COMPONENT

        override fun reduce(store: Store, action: Action): ComponentContent {
            val current = store.get(PLATFORM_COMPONENT) as ComponentContent
            return when (action) {
                is ShowComponent -> current.copy(component = action.component)
                else -> current
            }
        }
    }

    private class ComponentService : Service {

        private val defaultComponent = DEFAULT_COMPONENT

        override fun getKey(): String = PLATFORM_COMPONENT

        override fun execute(store: Store, action: Action) {
            when (action) {
                is ShowComponent -> showComponent(store, action)
            }
        }

        private fun showComponent(store: Store, action: ShowComponent) {
            defaultComponent.setComponent(action.component)
            action.component.onInit()
            store.dispatch(PerspectiveRedux.SwitchPerspective(PLATFORM_COMPONENT_ID))
        }

    }
}

data class ComponentContent(val component: Component?) {
    companion object {
        fun default() = ComponentContent(null)
    }
}
