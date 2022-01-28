package de.boettcher.alerter.core.alert.creation

import org.springframework.data.mongodb.repository.MongoRepository

interface AlertCreationRepository: MongoRepository<PreparedAlert, Integer>
