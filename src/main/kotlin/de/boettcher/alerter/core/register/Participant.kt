package de.boettcher.alerter.core.register

import com.fasterxml.jackson.annotation.JsonProperty
import lombok.ToString
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import javax.validation.constraints.Email

@ToString
@Document(collection = "Participant")
data class Participant(
    @Id
    @JsonProperty("userId")
    val userId: Integer,
    @JsonProperty("firstName")
    val firstName: String,
    @JsonProperty("lastName")
    val lastName: String,
    @Email
    @JsonProperty("email")
    val email: String,
    @JsonProperty("privileges")
    val privileges: List<Privileges>
) {

}