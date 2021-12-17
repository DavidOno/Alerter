package de.boettcher.alerter.core.register

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test


class EmailTest {

    @Test
    fun `valid email should pass without exception`(){
        Email("abc.def@gmail.com")
    }

    @Test
    fun `valid minimalistic email should pass without exception`(){
        Email("a.d@g.c")
    }

    @Test
    fun `email without prefix should throw exception`(){
        Assertions.assertThrows(IllegalArgumentException::class.java){
            Email("@gmail.com")
        }
    }

    @Test
    fun `email without postfix should throw exception`(){
        Assertions.assertThrows(IllegalArgumentException::class.java){
            Email("abc@.com")
        }
    }

    @Test
    fun `email without postfix and dot should throw exception`(){
        Assertions.assertThrows(IllegalArgumentException::class.java){
            Email("abc@com")
        }
    }

    @Test
    fun `email without ending should throw exception`(){
        Assertions.assertThrows(IllegalArgumentException::class.java){
            Email("abc@")
        }
    }

    @Test
    fun `empty email should throw exception`(){
        Assertions.assertThrows(IllegalArgumentException::class.java){
            Email("")
        }
    }

    @Test
    fun `only at-symbol should throw exception`(){
        Assertions.assertThrows(IllegalArgumentException::class.java){
            Email("")
        }
    }
}