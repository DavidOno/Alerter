package de.boettcher.alerter.core.register

import lombok.ToString
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import javax.validation.constraints.Email

@ToString
@Document(collection = "Participant")
data class Participant(
    @Id
    val userId: Integer,
    val firstName: String,
    val lastName: String,
    @Email
    val email: String,
    val privileges: List<Privileges>
) {

}