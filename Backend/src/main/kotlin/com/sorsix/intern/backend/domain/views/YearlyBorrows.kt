package com.sorsix.intern.backend.domain.views

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import net.jcip.annotations.Immutable
import org.hibernate.annotations.Subselect


@Entity
@Table(name = "reservations_thorugh_months")
@Immutable
@Subselect("select * from reservations_thorugh_months")
data class YearlyBorrows(
    @Id
    var id: Long,
    var name: String,
    var january: Long,
    var february: Long,
    var march: Long,
    var april: Long,
    var may: Long,
    var june: Long,
    var july: Long,
    var august: Long,
    var september: Long,
    var october: Long,
    var november: Long,
    var december: Long,
)