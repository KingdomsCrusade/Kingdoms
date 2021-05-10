package net.kingdomscrusade.kingdoms

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Order

internal class KingdomsAPITest{

    private val testDB: KingdomsAPI = KingdomsAPI(
        url = "jdbc:mariadb://dev.kingdomscrusade.net:33061/test",
        usr = "root",
        pwd = "test"
    )

    @Test
    @Order(1)
    @DisplayName("Database connection test")
    fun connectionTest(){
        assertTrue(testDB.isConnected())
    }

    @Test
    @Order(2)
    @DisplayName("Role initialization test")
    fun roleInitTest(){
        testDB.defaultRolesInit()
        val result = testDB.statement.executeQuery("SELECT * FROM Roles")
        while (result.next()){
            for (i in 1..4) {
                print("${result.getString(i)}\t")
            }
            print("\n")
        }
    }

}