package de.boettcher.alerter.core.alert.crud

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime


@Document("PreparedAlert")
data class PreparedAlert(
        @Id
        val alertId: Integer,
        @JsonProperty("message")
        val message: String,
        @JsonProperty("destinations")
        val destinations: List<String>,
        @JsonProperty("condition")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", shape = JsonFormat.Shape.STRING)
        private val dateTimeForAlert: LocalDateTime
){
        fun isConditionMet(): Boolean {
                return LocalDateTime.now().isAfter(dateTimeForAlert)
        }
}
