package de.boettcher.alerter.core.register

import javax.persistence.*

@Entity
@Table
data class Participant(
    @Id
    @SequenceGenerator(
        name = "participant_sequence",
        sequenceName = "participant_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "participant_sequence"
    )
    private val userId: Long,
    private val firstName: String,
    private val lastName: String,
    private val email: Email,
    private val authLevel: AuthLevel
) {

}