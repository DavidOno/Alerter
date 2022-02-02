package de.boettcher.alerter.core.register

import org.springframework.data.mongodb.repository.MongoRepository

interface ParticipantRepository: MongoRepository<Participant, Int> {
}