package de.boettcher.alerter.core.register

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/participant/")
class ParticipantController{

    @Autowired
    lateinit var participantRepository: ParticipantRepository

    @PostMapping("api/v1/addParticipant")
    fun saveParticipant(@RequestBody participant: Participant): ResponseEntity<String>{
        participantRepository.save(participant)
        return ResponseEntity.ok("Added participant with id: ${participant.userId}")
    }

    @GetMapping("api/v1/findAllParticipants")
    fun getParticipants(): ResponseEntity<List<Participant>>{
        return ResponseEntity.ok(participantRepository.findAll())
    }

    @GetMapping("api/v1/findAllParticipants/{id}")
    fun getParticipant(@RequestParam id: Integer): ResponseEntity<Participant>{
        return ResponseEntity.ok(participantRepository.findById(id).get())
    }


}