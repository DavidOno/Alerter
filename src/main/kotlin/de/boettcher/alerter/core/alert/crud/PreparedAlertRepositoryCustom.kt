package de.boettcher.alerter.core.alert.crud

import java.util.*

interface PreparedAlertRepositoryCustom {

    fun getAllAlertsForNextMinute(): List<PreparedAlert>
    fun getAllAlertsForNext4Hours(): List<PreparedAlert>
    fun getAllAlertsForNextDay(): List<PreparedAlert>
    fun deleteAlertById(id: Integer)
}