package de.boettcher.alerter.core.alert.crud

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/alert/crud/")
class PreparedAlertController {

    @Autowired
    private lateinit var preparedAlertRepository: PreparedAlertRepository

    @PostMapping("new_alert")
    fun createNewAlert(@RequestBody preparedAlert: PreparedAlert): ResponseEntity<String>{
        preparedAlertRepository.save(preparedAlert)
        return ResponseEntity.ok("Created new Alert: ${preparedAlert.alertId}")
    }
}