package de.boettcher.alerter.core.extensions

import java.time.Instant
import java.time.ZoneOffset

class InstantExtension {
    fun Instant.nowAtUTC() = Instant.now().atOffset(ZoneOffset.UTC)
}