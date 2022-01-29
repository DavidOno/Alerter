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
        preparedAlertRepository.save(preparedAlert)
        return ResponseEntity.ok("Created new Alert: ${preparedAlert.alertId}")
    }

    @DeleteMapping("/api/v1/delete_alert/")
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

//Example for calling .../new_alert
//{
//    "alertId": 11,
//    "message": "This is an alert",
//    "destinations": ["d.o@96gmail.com"],
//    "condition": "2022-01-30T22:00:00.285+0000"
//}