package de.boettcher.alerter.core.config

import de.boettcher.alerter.core.alert.crud.PreparedAlertRepository
import de.boettcher.alerter.core.alerting.EmailSender
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.scheduling.annotation.SchedulingConfigurer
import org.springframework.scheduling.config.ScheduledTaskRegistrar
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler

import java.time.LocalTime

import java.time.LocalDateTime

import java.time.LocalDate


@Configuration
@EnableScheduling
class SchedulingConfig: SchedulingConfigurer{
    private val POOL_SIZE: Int = 4

    var logger: Logger = LoggerFactory.getLogger(SchedulingConfig::class.java)
    @Autowired
    private lateinit var preparedAlertRepository: PreparedAlertRepository
    @Autowired
    private lateinit var emailSender: EmailSender

    @Scheduled(fixedRateString = "PT60S")
    fun sendAlerts(){
        val now = LocalDateTime.now()
        val allAlertsForTheNextMinute = preparedAlertRepository.getAllAlertsForTheNextMinute(now)
        logger.info("For following time stamp: $now ${allAlertsForTheNextMinute.size} alerts were found:")
        allAlertsForTheNextMinute.forEach{ optional -> logger.info(optional.toString()) }
        allAlertsForTheNextMinute.stream()
                                .filter { optional -> optional.isPresent }
                                .map { optional -> optional.get() }
                                .forEach { preparedAlert ->
                                            run {
                                                emailSender.triggerAlert(preparedAlert)
                                                preparedAlertRepository.deleteById(preparedAlert.alertId)
                                            }
                                }
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