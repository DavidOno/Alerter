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
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler
import org.springframework.scheduling.config.ScheduledTaskRegistrar
import java.time.Instant
import java.util.*


@Configuration
@EnableScheduling
class SchedulingConfig: SchedulingConfigurer{
    private val POOL_SIZE: Int = 1

    var logger: Logger = LoggerFactory.getLogger(SchedulingConfig::class.java)
    @Autowired
    private lateinit var preparedAlertRepository: PreparedAlertRepository
    @Autowired
    private lateinit var emailSender: EmailSender

    @Scheduled(fixedRateString = "PT60S")
    fun sendAlerts(){
        val now = Date.from(Instant.now())
        val allAlertsForTheNextMinute = preparedAlertRepository.getAllAlertsForNextMinute()
        logger.info("${allAlertsForTheNextMinute.size} alerts were found for following timestamp: $now")
        allAlertsForTheNextMinute.forEach{ optional -> logger.info(optional.toString()) }
        allAlertsForTheNextMinute.stream()
                                .forEach { preparedAlert ->
                                            run {
                                                emailSender.triggerAlert(preparedAlert)
                                                preparedAlertRepository.deleteAlertById(preparedAlert.alertId)
                                            }
                                }
    }



    override fun configureTasks(taskRegistrar: ScheduledTaskRegistrar) {
        val taskScheduler = ThreadPoolTaskScheduler()
        taskScheduler.poolSize = POOL_SIZE
        taskScheduler.initialize()
        taskRegistrar.setTaskScheduler(taskScheduler)
    }
}