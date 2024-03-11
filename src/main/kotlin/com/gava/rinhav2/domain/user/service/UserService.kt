package com.gava.rinhav2.domain.user.service

import com.gava.rinhav2.domain.exception.BusinessException
import com.gava.rinhav2.domain.exception.NotFoundException
import com.gava.rinhav2.domain.user.model.Transaction
import com.gava.rinhav2.domain.user.model.User
import com.gava.rinhav2.domain.user.repository.UserRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface UserService {
    fun executeTransaction(userId: Long, value: Long, transaction: Transaction): Mono<User>
    fun getStatement(userId: Long): Pair<Mono<User>, Flux<Transaction>>
}

@Service
class UserServiceImpl(
    private val userRepository: UserRepository
): UserService {

    override fun executeTransaction(userId: Long, value: Long, transaction: Transaction): Mono<User> {
        validateUser(userId)
        return userRepository.updateBalance(userId, value)
            .flatMap {
                userRepository.saveTransaction(transaction).thenReturn(it)
            }.switchIfEmpty(
                Mono.error(BusinessException("transaction error"))
            )
    }

    override fun getStatement(userId: Long): Pair<Mono<User>, Flux<Transaction>> {
        validateUser(userId)
        val userInfo = userRepository.findUserById(userId).cache()
        val transactions = userRepository.findTransactionsByUserId(userId)
        return Pair(userInfo, transactions)
    }

    private fun validateUser(userId: Long) {
        if (userId !in 1..5) {
            throw NotFoundException("User not found")
        }
    }

}