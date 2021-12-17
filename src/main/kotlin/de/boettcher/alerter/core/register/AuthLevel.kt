package de.boettcher.alerter.core.register

import javax.persistence.Entity

@Entity
sealed class AuthLevel{
    object SendEncryptedMessages: AuthLevel()
    object SendPlainTextMessages: AuthLevel()
}
