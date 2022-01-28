package de.boettcher.alerter.core.alert.crud

import org.springframework.data.mongodb.repository.MongoRepository

interface PreparedAlertRepository: MongoRepository<PreparedAlert, Integer>
