package org.setu.learningmanager.assignment

import org.setu.learningmanager.models.AssignmentModel

interface DoneListener {
    fun onStatusLongClick(assignment: AssignmentModel)
}