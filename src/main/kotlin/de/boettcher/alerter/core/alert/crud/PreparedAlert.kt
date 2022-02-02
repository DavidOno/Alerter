package de.boettcher.alerter.core.alert.crud

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import de.boettcher.alerter.core.custom.annotation.Emails
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty


@Document("PreparedAlert")
data class PreparedAlert(
        @Id
        val alertId: Int,

        @field:NotBlank(message = "There must be a message")
        @JsonProperty("message")
        val message: String,

        @field:Emails
        @field:NotEmpty(message = "There must be at least one destination (=email)")
        @JsonProperty("destinations")
        val destinations: List<String>,

        @JsonProperty("condition")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", shape = JsonFormat.Shape.STRING)
        val dateTimeForAlert: LocalDateTime
): Cacheable{
        override fun getUniqueIdentifier(): Int {
                return alertId
        }

        override fun getTimeStampForAlert(): Instant = dateTimeForAlert.atZone(ZoneId.systemDefault()).toInstant()

}

interface Cacheable {
        fun getUniqueIdentifier(): Int
        fun getTimeStampForAlert(): Instant
}
