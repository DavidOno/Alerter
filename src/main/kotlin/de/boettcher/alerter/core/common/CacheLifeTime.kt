package de.boettcher.alerter.core.common

import java.time.temporal.ChronoUnit

data class CacheLifeTime(val timeAmount: Long, val timeUnit: ChronoUnit)