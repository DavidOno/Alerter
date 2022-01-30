package de.boettcher.alerter.core.common

import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

@Component
@Scope("prototype")
class CacheByList<T> {
    private var cachedData = listOf<T>()

    fun getFromCacheOrElse(fromDatabase: () -> List<T>): List<T>{
        return if(cachedData.isNotEmpty()){
            cachedData
        }else{
            val alerts = fromDatabase()
            cachedData = alerts
            return alerts
        }
    }

    fun replaceData(newData: List<T>){
        cachedData = newData
    }
}