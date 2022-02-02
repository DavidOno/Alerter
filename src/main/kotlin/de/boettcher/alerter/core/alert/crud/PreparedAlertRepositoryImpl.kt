package de.boettcher.alerter.core.alert.crud

import de.boettcher.alerter.core.common.CacheHierarchy
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Component
import java.time.Instant
import java.time.ZoneId
import java.time.temporal.ChronoUnit

@Component
class PreparedAlertRepositoryImpl: PreparedAlertRepositoryCustom {

    @Autowired
    private lateinit var cacheHierarchy: CacheHierarchy<PreparedAlert>
    @Autowired
    @Lazy
    private lateinit var preparedAlertRepository: PreparedAlertRepository
    var logger: Logger = LoggerFactory.getLogger(PreparedAlertRepositoryImpl::class.java)

    override fun getAllAlertsForNextMinute(): List<PreparedAlert>{
        val nowIn1Minute = Instant.now().atZone(ZoneId.systemDefault()).toInstant().plus(1, ChronoUnit.MINUTES)
        val allAlertsForNext4Hours = cacheHierarchy.levels[0].getFromCacheOrElse { getAllAlertsForNext4Hours() }
        val alertsForNextMinute = allAlertsForNext4Hours.filter { alert: PreparedAlert -> nowIn1Minute.isAfter(alert.dateTimeForAlert.atZone(ZoneId.systemDefault()).toInstant()) }
        logger.info("Alerts for next minute($nowIn1Minute): $alertsForNextMinute")
        return alertsForNextMinute
    }

    override fun getAllAlertsForNext4Hours(): List<PreparedAlert>{
        val nowIn4Hours = Instant.now().atZone(ZoneId.systemDefault()).toInstant().plus(4, ChronoUnit.HOURS)
        val allAlertsForNextDay = cacheHierarchy.levels[1].getFromCacheOrElse { getAllAlertsForNextDay() }
        val allAlertsForNext4Hours = allAlertsForNextDay.filter { alert: PreparedAlert -> nowIn4Hours.isAfter(alert.dateTimeForAlert.atZone(ZoneId.systemDefault()).toInstant()) }
        logger.info("Alerts for next 4 hours($nowIn4Hours): $allAlertsForNext4Hours")
        return allAlertsForNext4Hours
    }

    override fun getAllAlertsForNextDay(): List<PreparedAlert>{
        val tomorrow = Instant.now().atZone(ZoneId.systemDefault()).toInstant().plus(1, ChronoUnit.DAYS)
        val allAlertsForNextDay = preparedAlertRepository.getAllAlertsLesserOrEqualTo(tomorrow).map { it.get() }
        cacheHierarchy.levels[1].replaceData(allAlertsForNextDay)
        logger.info("Alerts for next day($tomorrow): $allAlertsForNextDay")
        return allAlertsForNextDay
    }

    override fun deleteAlertById(id: Int) {
        preparedAlertRepository.deleteById(id)
        cacheHierarchy.deleteById(id)
    }

    override fun saveAlert(alert: PreparedAlert){
        preparedAlertRepository.save(alert)
        cacheHierarchy.save(alert)
    }


}