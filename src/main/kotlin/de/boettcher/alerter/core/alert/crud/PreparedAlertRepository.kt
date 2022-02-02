package de.boettcher.alerter.core.alert.crud

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import java.time.Instant
import java.util.*

interface PreparedAlertRepository: MongoRepository<PreparedAlert, Int>, PreparedAlertRepositoryCustom{

    @Query("{'dateTimeForAlert': { '\$lte' : ?0 }}")
    fun getAllAlertsLesserOrEqualTo(localDateTime: Instant): List<Optional<PreparedAlert>>
}