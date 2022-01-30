package de.boettcher.alerter.core.alert.crud

import de.boettcher.alerter.core.common.CacheByList
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Component
import java.time.Instant
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit
import java.util.*

@Component
class PreparedAlertRepositoryImpl: PreparedAlertRepositoryCustom {

    @Autowired
    private lateinit var cache24Hour: CacheByList<PreparedAlert>
    @Autowired
    private lateinit var cache4Hour: CacheByList<PreparedAlert>
    @Autowired
    @Lazy
    private lateinit var preparedAlertRepository: PreparedAlertRepository
    var logger: Logger = LoggerFactory.getLogger(PreparedAlertRepositoryImpl::class.java)
    private val TIME_BUFFER = 1L

    override fun getAllAlertsForNextMinute(): List<PreparedAlert>{
        val nowIn1Minute = Instant.now().plus(1, ChronoUnit.MINUTES)
        logger.info("Now in 1 Minute: $nowIn1Minute")
        val allAlertsForNext4Hours = cache4Hour.getFromCacheOrElse { getAllAlertsForNext4Hours() }
        val alertsForNextMinute = allAlertsForNext4Hours.filter { alert -> nowIn1Minute.isAfter(alert.dateTimeForAlert.toInstant(ZoneOffset.UTC)) }
        logger.info("Alerts for next day: $alertsForNextMinute")
        return alertsForNextMinute
    }

    override fun getAllAlertsForNext4Hours(): List<PreparedAlert>{
        val nowIn4Hours = Instant.now().plus(4 + TIME_BUFFER, ChronoUnit.HOURS)
        logger.info("Now in 4 hours: $nowIn4Hours")
        val allAlertsForNextDay = cache24Hour.getFromCacheOrElse { getAllAlertsForNextDay() }
        val allAlertsForNext4Hours = allAlertsForNextDay.filter { alert -> nowIn4Hours.isAfter(alert.dateTimeForAlert.toInstant(ZoneOffset.UTC)) }
        logger.info("Alerts for next 4 hours: $allAlertsForNext4Hours")
        return allAlertsForNext4Hours
    }

    override fun getAllAlertsForNextDay(): List<PreparedAlert>{
        val tomorrow = Date.from(Instant.now().plus(1, ChronoUnit.DAYS))
        logger.info("Tomorrow: $tomorrow")
        val allAlertsForNextDay = preparedAlertRepository.getAllAlertsLesserOrEqualTo(tomorrow).map { it.get() }
        cache24Hour.replaceData(allAlertsForNextDay)
        logger.info("Alerts for next day: $allAlertsForNextDay")
        return allAlertsForNextDay
    }
}