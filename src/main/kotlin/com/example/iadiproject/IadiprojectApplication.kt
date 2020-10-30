package com.example.iadiproject

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import kotlin.reflect.jvm.internal.impl.resolve.constants.KClassValue

@EnableGlobalMethodSecurity(prePostEnabled = true)
@SpringBootApplication()

class IadiprojectApplication

fun main(args: Array<String>) {
    runApplication<IadiprojectApplication>(*args)
}
