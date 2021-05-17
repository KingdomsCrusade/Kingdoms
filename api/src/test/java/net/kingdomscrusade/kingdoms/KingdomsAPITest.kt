package net.kingdomscrusade.kingdoms

import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class KingdomsAPITest: TestCommons(){

    @Test
    @DisplayName("Database connection test")
    fun connectionTest(){
        assertTrue(testAPI.isConnected())
    }

    @Test
    @AfterAll
    @DisplayName("Test clean up")
    fun cleanUp(){
        restoreData(testStatement)
    }

    // TODO Class init test

}