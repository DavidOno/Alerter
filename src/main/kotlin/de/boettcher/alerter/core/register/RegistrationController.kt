package de.boettcher.alerter.core.register

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/participant/")
class RegistrationController{

    @Autowired
    lateinit var participantRepository: ParticipantRepository

    @PostMapping("api/v1/addParticipant")
    fun saveParticipant(@RequestBody participant: Participant): String{
        participantRepository.save(participant)
        return "Added participant with id: ${participant.userId}"
    }

    @GetMapping("api/v1/findAllParticipants")
    fun getParticipants(): List<Participant>{
        return participantRepository.findAll()
    }

    @GetMapping("api/v1/findAllParticipants/{id}")
    fun getParticipant(@RequestParam id: Integer): Participant{
        return participantRepository.findById(id).get()
    }


}