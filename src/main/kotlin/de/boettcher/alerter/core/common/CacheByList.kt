package de.boettcher.alerter.core.common

import de.boettcher.alerter.core.alert.crud.Identifiable
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import java.time.Instant
import java.time.ZoneId

@Component
@Scope("prototype")
class CacheByList<T : Identifiable>(val lifeTimeForCacheValidity: CacheLifeTime) {
    private var cachedData = listOf<T>()
    private var timeStampTillCacheIsValid: Instant = Instant.now().atZone(ZoneId.systemDefault()).toInstant()
    private var noAlertsFoundForPeriod: Boolean = false

    private var logger: Logger = LoggerFactory.getLogger(CacheByList::class.java)


    fun getFromCacheOrElse(fromNextLevel: () -> List<T>): List<T>{
        logger.info("Cache active with $lifeTimeForCacheValidity and cache is valid: ${isCacheValid()}")
        return if(isCacheValid()){
            cachedData
        }else{
            return get(fromNextLevel)
        }
    }

    fun replaceData(newData: List<T>){
        cachedData = newData
    }

    fun deleteFromCache(id: Integer){
        cachedData = cachedData.filter { elem -> elem.getUniqueIdentifier() != id }
    }

    private fun get(fromNextLevel: () -> List<T>): List<T> {
        val alerts = fromNextLevel()
        cachedData = alerts
        timeStampTillCacheIsValid = Instant.now().atZone(ZoneId.systemDefault()).toInstant().plus(lifeTimeForCacheValidity.timeAmount, lifeTimeForCacheValidity.timeUnit)
        noAlertsFoundForPeriod = alerts.isEmpty()
        return alerts
    }

    private fun isCacheValid(): Boolean {
        return cacheDataIsValid() and timeStampTillCacheIsValid.isAfter(Instant.now().atZone(ZoneId.systemDefault()).toInstant())
    }

    private fun cacheDataIsValid() = (cachedData.isNotEmpty() or (cachedData.isEmpty() and noAlertsFoundForPeriod))
}