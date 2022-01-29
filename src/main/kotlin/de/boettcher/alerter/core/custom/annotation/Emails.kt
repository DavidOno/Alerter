package de.boettcher.alerter.core.custom.annotation

import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target
import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

@Constraint(validatedBy = [CustomEmailsConstraintValidator::class])
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
annotation class Emails(
        val message: String = "Invalid Email",
        val groups: Array<KClass<*>> = [],
        val payload: Array<KClass<out Payload>> = []
)
