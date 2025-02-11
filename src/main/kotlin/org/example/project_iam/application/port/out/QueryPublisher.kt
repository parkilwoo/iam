package org.example.project_iam.application.port.out

interface QueryPublisher {
    fun publish(query: String)
}