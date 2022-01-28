package de.boettcher.alerter.core.alert.creation

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/alert/creation/")
class AlertCreationController {

    @Autowired
    private lateinit var alertCreationRepository: AlertCreationRepository

    @PostMapping("new_alert")
    fun createNewAlert(@RequestBody preparedAlert: PreparedAlert): ResponseEntity<String>{
        alertCreationRepository.save(preparedAlert)
        return ResponseEntity.ok("Created new Alert: ${preparedAlert.alertId}")
    }
}