package de.boettcher.alerter.core.config

import de.boettcher.alerter.core.alert.crud.PreparedAlert
import de.boettcher.alerter.core.common.CacheByList
import de.boettcher.alerter.core.common.CacheLifeTime
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.temporal.ChronoUnit

@Configuration
class CommonConfig {
    @Bean
    fun cacheValidityFor4Hours() = CacheByList<PreparedAlert>(CacheLifeTime(4, ChronoUnit.HOURS))

    @Bean
    fun cacheValidityFor1Day() = CacheByList<PreparedAlert>(CacheLifeTime(1, ChronoUnit.DAYS))
}