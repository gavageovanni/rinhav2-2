package com.gava.rinhav2.adapter.user.repository

import com.gava.rinhav2.adapter.user.repository.database.TransactionR2dbcRepository
import com.gava.rinhav2.adapter.user.repository.database.UserR2dbcRepository
import com.gava.rinhav2.domain.user.model.Transaction
import com.gava.rinhav2.domain.user.model.User
import com.gava.rinhav2.domain.user.repository.UserRepository
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class UserRepositoryImpl(
    private val userR2dbcRepository: UserR2dbcRepository,
    private val transactionR2dbcRepository: TransactionR2dbcRepository
): UserRepository {
    override fun updateBalance(id: Long, value: Long): Mono<User> {
        return userR2dbcRepository.updateBalance(id, value)
    }

    override fun findTransactionsByUserId(id: Long): Flux<Transaction> {
        return transactionR2dbcRepository.findByUserId(id)
    }

    override fun saveTransaction(transaction: Transaction): Mono<Transaction> {
        return transactionR2dbcRepository.save(transaction)
    }

    override fun findUserById(id: Long): Mono<User> {
        return userR2dbcRepository.findById(id)
    }
}