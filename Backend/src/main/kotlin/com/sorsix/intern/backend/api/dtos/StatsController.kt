package com.sorsix.intern.backend.api.dtos

import com.sorsix.intern.backend.config.CurrentUser
import com.sorsix.intern.backend.domain.views.*
import com.sorsix.intern.backend.security.UserPrincipal
import com.sorsix.intern.backend.service.StatsService
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/stats")
@CrossOrigin
class StatsController(
    private val statsService: StatsService
) {
    @GetMapping("/inventory")
    fun getInventory(@CurrentUser userPrincipal: UserPrincipal): Double {
        return statsService.getInventoryForStore(userPrincipal.id)
    }

    @GetMapping("/ratio")
    fun getRatio(@CurrentUser userPrincipal: UserPrincipal): Double {
        return statsService.getRatioForStore(userPrincipal.id)
    }

    @GetMapping("/loansPerDays")
    fun getLoansPerDays(@CurrentUser userPrincipal: UserPrincipal): LoansInLastDays {
        return statsService.getLoansInLastDays(userPrincipal.id)
    }

    @GetMapping("/reservationsPerDays")
    fun getReservationsPerDays(@CurrentUser userPrincipal: UserPrincipal): ReservationsInLastDays {
        return statsService.getReservationsInLastDays(userPrincipal.id)
    }

    @GetMapping("/yearlyReservations")
    fun getYearlyReservations(@CurrentUser userPrincipal: UserPrincipal): YearlyReservations {
        return statsService.getYearlyReservations(userPrincipal.id)
    }

    @GetMapping("/yearlyBorrows")
    fun getYearlyBorrows(@CurrentUser userPrincipal: UserPrincipal): YearlyBorrows {
        return statsService.getYearlyBorrows(userPrincipal.id)
    }
}
