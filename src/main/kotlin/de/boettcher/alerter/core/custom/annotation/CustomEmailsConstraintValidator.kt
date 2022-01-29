package de.boettcher.alerter.core.custom.annotation

import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class CustomEmailsConstraintValidator: ConstraintValidator<Emails, Collection<String>> {
    override fun isValid(value: Collection<String>?, context: ConstraintValidatorContext?): Boolean {
        if (value == null) {
            return false
        }
        val validator = EmailValidator()
        for (s in value) {
            if (!validator.isValid(s, context)) {
                return false
            }
        }
        return true
    }
}