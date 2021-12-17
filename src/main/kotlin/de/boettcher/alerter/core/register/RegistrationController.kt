package de.boettcher.alerter.core.register

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class RegistrationController
@Autowired constructor(val registrationService: RegistrationService){

    @GetMapping("api/v1/availability")
    fun testAvailability(): String {
        return "Server is available"
    }

    @GetMapping("api/v1/test")
    fun test(): List<Participant>{
        return registrationService.getUsers()
    }


}