package de.boettcher.alerter.core.common

import de.boettcher.alerter.core.alert.crud.PreparedAlert


class CacheHierarchy(val levels: List<CacheByList<PreparedAlert>>) {
    fun deleteById(id: Integer) {
        levels.forEach { cache -> cache.deleteFromCache(id) }
    }

}