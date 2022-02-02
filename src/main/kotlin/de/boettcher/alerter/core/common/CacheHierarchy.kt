package de.boettcher.alerter.core.common

import de.boettcher.alerter.core.alert.crud.Cacheable


class CacheHierarchy<T : Cacheable>(val levels: List<CacheByList<T>>) {
    fun deleteById(id: Int) {
        levels.forEach { cache -> cache.deleteFromCache(id) }
    }

    fun save(element: T){
        levels.forEach { cache -> cache.addToCache(element) }
    }

}