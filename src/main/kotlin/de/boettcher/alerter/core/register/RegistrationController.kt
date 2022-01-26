package de.boettcher.alerter.core.register

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/")
class RegistrationController{

    @Autowired
    lateinit var participantRepository: ParticipantRepository

    @GetMapping("api/v1/availability")
    fun testAvailability(): String {
        return "Server is available"
    }

    @PostMapping("addParticipant")
    fun saveParticipant(@RequestBody participant: Participant): String{
        participantRepository.save(participant)
        return "Added participant with id: ${participant.id}"
    }

    @GetMapping("findAllParticipants")
    fun getParticipants(): List<Participant>{
        return participantRepository.findAll()
    }

    @GetMapping("findAllParticipants/{id}")
    fun getParticipant(@RequestParam id: Integer): Participant{
        return participantRepository.findById(id).get()
    }


}