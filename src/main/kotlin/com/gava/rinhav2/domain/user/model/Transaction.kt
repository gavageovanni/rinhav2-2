package com.gava.rinhav2.domain.user.model

import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("transaction")
data class Transaction(
    val userId: Long,
    val value: Long,
    val createdAt: LocalDateTime,
    val description: String,
    val type: String
)