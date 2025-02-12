package org.example.project_iam.application.port.out

import org.example.project_iam.domain.model.Query

interface QueryPublisher {
    fun publish(query: Query)
}