package de.boettcher.alerter.core.alerting

import de.boettcher.alerter.core.alert.crud.PreparedAlert
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Component

@Component
class EmailSender {

    var logger: Logger = LoggerFactory.getLogger(EmailSender::class.java)
    @Autowired
    private lateinit var javaMailSender: JavaMailSender

    fun triggerAlert(preparedAlert: PreparedAlert){
        val email = constructEmail(preparedAlert)
        javaMailSender.send(email)
        logger.info("Send email to ${preparedAlert.destinations} for alert-Id: ${preparedAlert.alertId}")
    }

    private fun constructEmail(preparedAlert: PreparedAlert): SimpleMailMessage {
        return SimpleMailMessage().apply {
            setTo(*(preparedAlert.destinations.toTypedArray()))
            setSubject("Email sent from Alerter")
            setText(preparedAlert.message)
        }
    }
}