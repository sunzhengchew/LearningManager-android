package org.setu.learningmanager.models

data class SubjectModel(
    var id: Long = 0,
    var name: String = "",
    )

data class AssignmentModel(
    var id: Long = 0,
    var subjectId: Long = 0,
    var title: String = "",
    var description: String = "",
    var deadline: String = "",      // "2026-10-20"
    var isCompleted: Boolean = false
)

data class ExamModel(
    var id: Long = 0,
    var subjectId: Long = 0,
    var title: String = "",
    var date: String = "",
    var time: String = "",
    var location: String = ""
)
