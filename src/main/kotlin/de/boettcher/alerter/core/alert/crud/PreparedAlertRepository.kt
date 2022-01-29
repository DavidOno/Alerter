package de.boettcher.alerter.core.alert.crud

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import java.time.LocalDateTime
import java.util.*

interface PreparedAlertRepository: MongoRepository<PreparedAlert, Integer>{

    @Query("{'dateTimeForAlert': { '\$lte' : ?0 }}")
    fun getAllAlertsForTheNextMinute(localDateTime: LocalDateTime): List<Optional<PreparedAlert>>
}
