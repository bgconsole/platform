package com.bgconsole.platform.store

import java.util.*

class Store {

    private val store: MutableMap<String, Node<*>> = mutableMapOf()
    private val subscribers: MutableMap<String, MutableMap<String, Subscriber>> = mutableMapOf()
    private val reducers: MutableList<Reducer<*>> = mutableListOf()
    private val services: MutableList<Service> = mutableListOf()

    fun subscribe(key: String, subscriber: Subscriber): String {
        val uuid = UUID.randomUUID().toString()
        if (subscribers.containsKey(key)) {
            subscribers[key]?.put(uuid, subscriber)
            return uuid;
        } else {
            val map: MutableMap<String, Subscriber> = mutableMapOf()
            map[uuid] = subscriber
            subscribers[key] = map
        }
        return uuid
    }

//    fun unsubscribe(key: String, id: String) {
//        subscribers[key]?.remove(id)
//    }

    fun addToStore(key: String, node: Any) {
        if (!store.containsKey(key)) {
            store[key] = Node(node)
        }
    }

    fun registerService(service: Service) {
        services.add(service)
    }

    fun registerReducer(reducer: Reducer<*>) {
        reducers.add(reducer)
    }

    fun registerReducers(reducers: List<Reducer<*>>) {
        this.reducers.addAll(reducers)
    }

    fun registerServices(services: List<Service>) {
        this.services.addAll(services)
    }

    fun get(key: String): Any? {
        return store[key]?.get()
    }

    fun dispatch(action: Action) {
        reducers.find { it.getKey() == action.storeKey }?.let {
            val key = it.getKey()
            val newEntity = it.reduce(this, action)
            if (newEntity != null) {
                store[key]?.update(newEntity)
                subscribers[key]?.forEach { subscriber ->
                    subscriber.value.update(newEntity)
                }
            }
        }
        services.find { it.getKey() == action.storeKey }?.let {
            if (it.getKey() == action.storeKey) {
                it.execute(this, action)
            }
        }
    }
}