package de.boettcher.alerter.core.alert.crud

import de.boettcher.alerter.core.common.CacheByList
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Component
import java.time.Instant
import java.time.ZoneId
import java.time.temporal.ChronoUnit

@Component
class PreparedAlertRepositoryImpl: PreparedAlertRepositoryCustom {

    @Autowired
    @Qualifier("cacheValidityFor1Day")
    private lateinit var cache24Hour: CacheByList<PreparedAlert>
    @Autowired
    @Qualifier("cacheValidityFor4Hours")
    private lateinit var cache4Hour: CacheByList<PreparedAlert>
    @Autowired
    @Lazy
    private lateinit var preparedAlertRepository: PreparedAlertRepository
    var logger: Logger = LoggerFactory.getLogger(PreparedAlertRepositoryImpl::class.java)

    override fun getAllAlertsForNextMinute(): List<PreparedAlert>{
        val nowIn1Minute = Instant.now().atZone(ZoneId.systemDefault()).toInstant().plus(1, ChronoUnit.MINUTES)
        val allAlertsForNext4Hours = cache4Hour.getFromCacheOrElse { getAllAlertsForNext4Hours() }
        val alertsForNextMinute = allAlertsForNext4Hours.filter { alert -> nowIn1Minute.isAfter(alert.dateTimeForAlert.atZone(ZoneId.systemDefault()).toInstant()) }
        logger.info("Alerts for next minute($nowIn1Minute): $alertsForNextMinute")
        return alertsForNextMinute
    }

    override fun getAllAlertsForNext4Hours(): List<PreparedAlert>{
        val nowIn4Hours = Instant.now().atZone(ZoneId.systemDefault()).toInstant().plus(4, ChronoUnit.HOURS)
        val allAlertsForNextDay = cache24Hour.getFromCacheOrElse { getAllAlertsForNextDay() }
        val allAlertsForNext4Hours = allAlertsForNextDay.filter { alert -> nowIn4Hours.isAfter(alert.dateTimeForAlert.atZone(ZoneId.systemDefault()).toInstant()) }
        logger.info("Alerts for next 4 hours($nowIn4Hours): $allAlertsForNext4Hours")
        return allAlertsForNext4Hours
    }

    override fun getAllAlertsForNextDay(): List<PreparedAlert>{
        val tomorrow = Instant.now().atZone(ZoneId.systemDefault()).toInstant().plus(1, ChronoUnit.DAYS)
        val allAlertsForNextDay = preparedAlertRepository.getAllAlertsLesserOrEqualTo(tomorrow).map { it.get() }
        cache24Hour.replaceData(allAlertsForNextDay)
        logger.info("Alerts for next day($tomorrow): $allAlertsForNextDay")
        return allAlertsForNextDay
    }
}