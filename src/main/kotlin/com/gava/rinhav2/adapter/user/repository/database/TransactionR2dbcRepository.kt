package com.gava.rinhav2.adapter.user.repository.database

import com.gava.rinhav2.domain.user.model.Transaction
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface TransactionR2dbcRepository : R2dbcRepository<Transaction, Long> {
    @Query("""
        SELECT * FROM transaction 
        WHERE user_id = :userId 
        ORDER BY created_at DESC LIMIT 10
        """)
    fun findByUserId(userId: Long): Flux<Transaction>
}