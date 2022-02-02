package de.boettcher.alerter.core.alert.crud

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import javax.validation.Valid


@RestController
@RequestMapping("/alert/crud")
class PreparedAlertController {

    @Autowired
    private lateinit var preparedAlertRepository: PreparedAlertRepository

    @PostMapping("/api/v1/new_alert")
    fun createNewAlert(@Valid @RequestBody preparedAlert: PreparedAlert, bindingResult: BindingResult): ResponseEntity<String>{
        if (bindingResult.hasErrors()) {
            return ResponseEntity("JSON values do not satisfy constraints.\n$bindingResult", HttpStatus.BAD_REQUEST)
        }
        preparedAlertRepository.saveAlert(preparedAlert)
        return ResponseEntity.ok("Created new Alert: ${preparedAlert.alertId}")
    }

    @DeleteMapping("/api/v1/delete_alert/")
    fun deleteAlert(@RequestParam id: Int): ResponseEntity<String>{
       return if(alertIdExists(id)) {
            preparedAlertRepository.deleteAlertById(id)
            ResponseEntity.ok("Deleted Alert with $id")
        } else {
            ResponseEntity.badRequest().body("Alert with id: $id not found")
        }
    }

    private fun alertIdExists(id: Int): Boolean {
        return preparedAlertRepository.findById(id).isPresent
    }
}