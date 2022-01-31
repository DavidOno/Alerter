package de.boettcher.alerter.core.common

import de.boettcher.alerter.core.config.SchedulingConfig
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import java.time.Instant

@Component
@Scope("prototype")
class CacheByList<T>(val lifeTimeForCacheValidity: CacheLifeTime) {
    private var cachedData = listOf<T>()
    private var timeStampTillCacheIsValid: Instant = Instant.now()
    private var noAlertsFoundForPeriod: Boolean = false

    private var logger: Logger = LoggerFactory.getLogger(CacheByList::class.java)


    fun getFromCacheOrElse(fromDatabase: () -> List<T>): List<T>{
        logger.info("Cache active with $lifeTimeForCacheValidity and cache is valid ${isCacheValid()}")
        return if(isCacheValid()){
            cachedData
        }else{
            return getFromDatabase(fromDatabase)
        }
    }

    private fun getFromDatabase(fromDatabase: () -> List<T>): List<T> {
        val alerts = fromDatabase()
        cachedData = alerts
        timeStampTillCacheIsValid = Instant.now().plus(lifeTimeForCacheValidity.timeAmount, lifeTimeForCacheValidity.timeUnit)
        noAlertsFoundForPeriod = alerts.isEmpty()
        return alerts
    }

    fun replaceData(newData: List<T>){
        cachedData = newData
    }

    private fun isCacheValid(): Boolean {
        return cacheDataIsValid() and timeStampTillCacheIsValid.isBefore(Instant.now())
    }

    private fun cacheDataIsValid() = (cachedData.isNotEmpty() or (cachedData.isEmpty() and noAlertsFoundForPeriod))
}