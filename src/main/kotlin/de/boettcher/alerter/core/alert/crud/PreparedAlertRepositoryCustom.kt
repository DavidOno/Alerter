package de.boettcher.alerter.core.alert.crud

interface PreparedAlertRepositoryCustom {

    fun getAllAlertsForNextMinute(): List<PreparedAlert>
    fun getAllAlertsForNext4Hours(): List<PreparedAlert>
    fun getAllAlertsForNextDay(): List<PreparedAlert>
    fun deleteAlertById(id: Int)
    fun saveAlert(alert: PreparedAlert)
}