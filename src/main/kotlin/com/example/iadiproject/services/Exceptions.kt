package com.example.iadiproject.services

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class NotFoundException(message:String = "Not Found!!") : RuntimeException(message)

@ResponseStatus(HttpStatus.CONFLICT)
class ConflictException(message:String = "Conflict!!!") : RuntimeException(message)