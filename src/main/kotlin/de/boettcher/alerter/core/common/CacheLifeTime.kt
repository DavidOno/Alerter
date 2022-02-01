package de.boettcher.alerter.core.common

import java.time.Instant
import java.time.ZoneId
import java.time.temporal.ChronoUnit

data class CacheLifeTime(val timeAmount: Long, val timeUnit: ChronoUnit) {
    fun getNewTimeStampTillCacheIsValid(): Instant {
        return Instant.now().atZone(ZoneId.systemDefault()).toInstant().plus(timeAmount, timeUnit)
    }
}