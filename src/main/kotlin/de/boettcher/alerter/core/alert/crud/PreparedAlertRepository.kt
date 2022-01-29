package de.boettcher.alerter.core.alert.crud

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import java.util.*

interface PreparedAlertRepository: MongoRepository<PreparedAlert, Integer>{

    @Query("{'dateTimeForAlert': { '\$lte' : ?0 }}")
    fun getAllAlertsForTheNextMinute(localDateTime: Date): List<Optional<PreparedAlert>>
}
//Example MongoDb-Query: {dateTimeForAlert: {$lte: ISODate("2022-01-29T19:40:00.285+00:00")}}