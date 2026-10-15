package org.setu.learningmanager.models

import java.util.concurrent.atomic.AtomicLong

class LearnMemStore {
    private val subjects = ArrayList<SubjectModel>()
    private val assignments = ArrayList<AssignmentModel>()
    private val exams = ArrayList<ExamModel>()
    private val lastId = AtomicLong(0L)

    //Subject CRUD
    fun createSubject(subject: SubjectModel) {
        subject.id = lastId.incrementAndGet()
        subjects.add(subject)
    }

    fun updateSubject(subject: SubjectModel): Boolean {
        val foundSubject = findOneSubject(subject.id)
        return if (foundSubject != null) {
            foundSubject.name = subject.name
            true
        } else {
            false
        }
    }

    fun deleteSubject(id: Long): Boolean {
        val foundSubject = findOneSubject(id)
        return if (foundSubject != null) {
            subjects.remove(foundSubject)
            true
        } else {
            false
        }
    }

    fun findAllSubject(): List<SubjectModel> {
        return subjects
    }

    fun findOneSubject(id: Long): SubjectModel? {
        return subjects.find { p -> p.id == id }
    }


    //Assignment CRUD
    fun findAllAssignments(): List<AssignmentModel> {
        return assignments
    }

    fun createAssignment(assignment: AssignmentModel) {
        assignment.id = lastId.incrementAndGet()
        assignments.add(assignment)
    }

    fun updateAssignment(assignment: AssignmentModel): Boolean {
        val foundAssignment = findOneAssignment(assignment.id)
        return if (foundAssignment != null) {
            foundAssignment.title = assignment.title
            true
        } else {
            false
        }
    }

    fun deleteAssignment(id: Long): Boolean {
        val foundAssignment = findOneAssignment(id)
        return if (foundAssignment != null) {
            assignments.remove(foundAssignment)
            true
        } else {
            false
        }
    }

    fun findAllAssignment(): List<SubjectModel> {
        return subjects
    }

    fun findOneAssignment(id: Long): AssignmentModel? {
        return assignments.find { p -> p.id == id }
    }



    // Exam CRUD
    fun findAllExams(): List<ExamModel> {
        return exams
    }

    fun createExam(exam: ExamModel) {
        exam.id = lastId.incrementAndGet()
        exams.add(exam)
    }

    fun updateExam(exam: ExamModel): Boolean {
        val foundExam = findOneExam(exam.id)
        return if (foundExam != null) {
            foundExam.subjectId = exam.subjectId
            foundExam.title = exam.title
            foundExam.date = exam.date
            foundExam.time = exam.time
            foundExam.location = exam.location
            true
        } else {
            false
        }
    }

    fun deleteExam(id: Long): Boolean {
        val foundExam = findOneExam(id)
        return if (foundExam != null) {
            exams.remove(foundExam)
            true
        } else {
            false
        }
    }

    fun findOneExam(id: Long): ExamModel? {
        return exams.find { exam -> exam.id == id }
    }










}