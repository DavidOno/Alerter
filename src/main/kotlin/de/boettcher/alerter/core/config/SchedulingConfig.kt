package de.boettcher.alerter.core.config

import de.boettcher.alerter.core.alert.crud.PreparedAlert
import de.boettcher.alerter.core.alert.crud.PreparedAlertRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.scheduling.annotation.SchedulingConfigurer
import org.springframework.scheduling.config.ScheduledTaskRegistrar
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler
import java.time.ZoneOffset

import java.time.Instant

import java.time.LocalTime

import java.time.LocalDateTime

import java.time.LocalDate
import java.util.*


@Configuration
@EnableScheduling
class SchedulingConfig: SchedulingConfigurer{
    private val POOL_SIZE: Int = 4

    @Autowired
    private lateinit var preparedAlertRepository: PreparedAlertRepository

    @Scheduled(fixedRateString = "PT10S")
    fun sendAlerts(){
        //Search through MongoDB
        val localDate = LocalDate.now()
        val localDateTime = LocalDateTime.of(localDate, LocalTime.MIDNIGHT)
        val allAlertsForTheNextMinute = preparedAlertRepository.getAllAlertsForTheNextMinute(localDateTime)
        System.out.println(allAlertsForTheNextMinute)
    }


//    @Scheduled(fixedRateString = "PT1M")
//    fun sendAlerts(){
//        //Search through Redis Cache1
//    }

//    @Scheduled(fixedRateString = "PT1M") //Every 4 Hours
//    fun checkForAlertsForNext5Hours(){
//        //Search through Redis Cache2
//    }
//
//    @Scheduled(fixedRateString = "PT1M") //Every 24 Hours
//    fun checkForAlertsForNext25Hours(){
//        //Search through MongoDB
//    }



    override fun configureTasks(taskRegistrar: ScheduledTaskRegistrar) {
        val taskScheduler = ThreadPoolTaskScheduler()
        taskScheduler.poolSize = POOL_SIZE
        taskScheduler.initialize()
        taskRegistrar.setTaskScheduler(taskScheduler)
    }
}