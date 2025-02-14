package org.example.project_iam.adapter.outbound.kafka

import java.io.Serializable

data class AnswerMessage(
    var relationId: String = "",
    var content: String = ""
) : Serializable