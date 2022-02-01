package de.boettcher.alerter.core.extensions

import java.time.Instant
import java.time.ZoneOffset

class InstantUtility {
    fun getNowAtUTC() = Instant.now().atOffset(ZoneOffset.UTC)
}