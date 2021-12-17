package de.boettcher.alerter.core.register

import java.util.regex.Matcher
import java.util.regex.Pattern
import javax.persistence.Entity

@Entity
data class Email(val email: String){

    init {
        checkEmail()
    }

    private fun checkEmail(){
        val emailPattern = ".+@.+\\..+"
        val pattern: Pattern = Pattern.compile(emailPattern)
        val matcher: Matcher = pattern.matcher(email)
        if(!matcher.matches()){
            throw IllegalArgumentException("Invalid email adress")
        }
    }
}
