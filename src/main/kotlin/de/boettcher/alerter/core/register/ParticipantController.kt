package de.boettcher.alerter.core.register

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/participant")
class ParticipantController{

    @Autowired
    lateinit var participantRepository: ParticipantRepository

    @PostMapping("/api/v1/addParticipant")
    fun saveParticipant(@Valid @RequestBody participant: Participant, bindingResult: BindingResult): ResponseEntity<String>{
        if (bindingResult.hasErrors()) {
            return ResponseEntity("JSON values do not satisfy constraints.\n$bindingResult", HttpStatus.BAD_REQUEST)
        }
        participantRepository.save(participant)
        return ResponseEntity.ok("Added participant with id: ${participant.userId}")
    }

    @GetMapping("/api/v1/findAllParticipants")
    fun getParticipants(): ResponseEntity<List<Participant>>{
        return ResponseEntity.ok(participantRepository.findAll())
    }

    @GetMapping("/api/v1/findAllParticipants/")
    fun getParticipant(@RequestParam id: Int): ResponseEntity<Participant>{
        return ResponseEntity.ok(participantRepository.findById(id).get())
    }
}