package de.boettcher.alerter.core.alert.crud

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import java.util.*

interface PreparedAlertRepository: MongoRepository<PreparedAlert, Integer>{

    @Query("{'dateTimeForAlert': { '\$lte' : ?0 }}")
    fun getAllAlertsForTheNextMinute(localDateTime: Date): List<Optional<PreparedAlert>>
}
