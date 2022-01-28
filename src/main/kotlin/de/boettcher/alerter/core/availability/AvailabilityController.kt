package de.boettcher.alerter.core.availability

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/general")
class AvailabilityController{

    @GetMapping("/api/v1/availability")
    fun testAvailability(): ResponseEntity<String> {
        return ResponseEntity.ok("Server is available")
    }
}