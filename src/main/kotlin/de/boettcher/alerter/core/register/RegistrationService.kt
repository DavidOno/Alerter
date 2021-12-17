package de.boettcher.alerter.core.register

import org.springframework.stereotype.Service

@Service
class RegistrationService {
    fun getUsers(): List<Participant> {
        return listOf<Participant>(Participant(1L, "David", "Boe", Email("d@g.com"), AuthLevel.SenderAndReceiver))
    }
}