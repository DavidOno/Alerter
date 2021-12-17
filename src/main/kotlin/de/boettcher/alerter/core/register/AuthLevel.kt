package de.boettcher.alerter.core.register

import javax.persistence.Entity

@Entity
sealed class AuthLevel{
    object SenderAndReceiver: AuthLevel()
    object Receiver: AuthLevel()
}
