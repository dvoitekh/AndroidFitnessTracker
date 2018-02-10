package com.example.dvoitekh.fitnesstracker.step_detection

import junit.framework.Assert.assertEquals
import org.junit.Test


class TestSensorFilter {
    @Test
    fun test_cross_product() {
        val a = floatArrayOf(1f, 2f, 3f)
        val b = floatArrayOf(4f, 5f, 6f)
        val res = floatArrayOf(-3f,  6f, -3f)
        assert(SensorFilter.cross(a, b) contentEquals res)
    }

    @Test
    fun test_norm() {
        val a = floatArrayOf(0f, 0f, 1f)
        val res = 1f
        assertEquals(SensorFilter.norm(a), res)
    }

    @Test
    fun test_dot() {
        val a = floatArrayOf(1f, 2f, 3f)
        val b = floatArrayOf(4f, 5f, 6f)
        val res = 32f
        assertEquals(SensorFilter.dot(a, b), res)
    }

    @Test
    fun test_normalize() {
        val a = floatArrayOf(1f, 1f, 1f, 1f)
        val res = floatArrayOf(0.5f, 0.5f, 0.5f, 0.5f)
        assert(SensorFilter.normalize(a) contentEquals res)
    }
}