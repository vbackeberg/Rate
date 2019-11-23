package com.example.myapplication.entities

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ApplicantTest {

    @Test
    fun When_contentEquals_Should_equal() {
        val applicant = Applicant(2, 1, 1)
        val otherApplicant = Applicant(1, 1, 1)

        assertTrue(applicant.contentEquals(otherApplicant))
    }

    @Test
    fun When_contentEquals_Should_not_equal() {
        val applicant = Applicant(2, 2, 1)
        val otherApplicant = Applicant(1, 1, 1)

        assertFalse(applicant.contentEquals(otherApplicant))
    }
}