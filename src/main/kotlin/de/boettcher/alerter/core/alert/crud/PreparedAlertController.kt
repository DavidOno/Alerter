package de.boettcher.alerter.core.alert.crud

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/alert/crud/")
class PreparedAlertController {

    @Autowired
    private lateinit var preparedAlertRepository: PreparedAlertRepository

    @PostMapping("api/v1/new_alert")
    fun createNewAlert(@RequestBody preparedAlert: PreparedAlert): ResponseEntity<String>{
        preparedAlertRepository.save(preparedAlert)
        return ResponseEntity.ok("Created new Alert: ${preparedAlert.alertId}")
    }

    @DeleteMapping("api/v1/delete_alert/")
    fun deleteAlert(@RequestParam id: Integer): ResponseEntity<String>{
       return if(alertIdExists(id)) {
            preparedAlertRepository.deleteById(id)
            ResponseEntity.ok("Deleted Alert with $id")
        } else {
            ResponseEntity.badRequest().body("Alert with id: $id not found")
        }
    }

    private fun alertIdExists(id: Integer): Boolean {
        return preparedAlertRepository.findById(id).isPresent
    }
}