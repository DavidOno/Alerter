package de.boettcher.alerter.core.register

import com.fasterxml.jackson.annotation.JsonProperty
import lombok.ToString
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

@ToString
@Document(collection = "Participant")
data class Participant(
    @Id
    @JsonProperty("userId")
    val userId: Int,

    @field:NotBlank(message = "First name cannot be blank")
    @JsonProperty("firstName")
    val firstName: String,

    @field:NotBlank(message = "Last name cannot be blank")
    @JsonProperty("lastName")
    val lastName: String,

    @field:Email(message = "Given email is not a valid one")
    @JsonProperty("email")
    val email: String,

    @JsonProperty("privileges")
    val privileges: List<Privileges>?
)