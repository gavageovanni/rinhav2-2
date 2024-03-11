package com.gava.rinhav2.adapter.advice

import com.gava.rinhav2.domain.exception.BusinessException
import com.gava.rinhav2.domain.exception.NotFoundException
import org.springframework.core.codec.DecodingException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.support.WebExchangeBindException


@ControllerAdvice
class ControllerExceptionHandler {

    @ExceptionHandler(value = [NotFoundException::class])
    fun handleNotFoundException(ex: NotFoundException): ResponseEntity<ErrorResponse> {
        val response = ErrorResponse(
            message = ex.message ?: "Not found",
            status = 404,
            code = "NOT_FOUND"
        )

        return ResponseEntity(response, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(value = [
        DecodingException::class,
        MethodArgumentNotValidException::class,
        WebExchangeBindException::class,
        BusinessException::class
    ])
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    fun handleUnprocessableEntity(e: Exception): ResponseEntity<ErrorResponse> {
        val response = ErrorResponse(
            message = "Unprocessable Entity.",
            status = 422,
            code = "UNPROCESSABLE_ENTITY",
        )
        return ResponseEntity(response, HttpStatus.UNPROCESSABLE_ENTITY)
    }
}


data class ErrorResponse(
    val message: String,
    val status: Int,
    val code: String,
    val fieldsErrors: List<String>? = null,
)