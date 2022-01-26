package de.boettcher.alerter.core.register

import javax.persistence.Entity


sealed class Privileges{
    object SendEncryptedMessages: Privileges()
    object SendPlainTextMessages: Privileges()
    object CalculateBigFactorial: Privileges()
}
