package net.kingdomscrusade.kingdoms

import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import java.sql.DriverManager

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class KingdomsAPITest {

    private lateinit var testAPI: KingdomsAPI

    @BeforeAll
    fun clearTables(){
        val db = DriverManager.getConnection(
            "jdbc:mariadb://dev.kingdomscrusade.net:33061/test",
            "root",
            "test"
        ).createStatement()
        db.executeUpdate("SET FOREIGN_KEY_CHECKS = 0;")
        db.executeUpdate("DROP TABLE Users, Kingdoms, Roles, PluginInfo;")
        db.executeUpdate("SET FOREIGN_KEY_CHECKS = 1;")
    }

    @Test
    @DisplayName("API Initialization Test")
    fun initTest(){
        testAPI =  KingdomsAPI (
        url = "jdbc:mariadb://dev.kingdomscrusade.net:33061/test",
        usr = "root",
        pwd = "test"
        )
        val countQuery = testAPI.statement.executeQuery(
            """
            SELECT COUNT(*)
            FROM INFORMATION_SCHEMA.TABLES
            WHERE TABLE_SCHEMA = 'test'
            """.trimIndent()
        )
        countQuery.next()
        assertEquals(4, countQuery.getInt("count(*)"))
    }

    @AfterAll
    fun cleanUp(){
        val statement = testAPI.statement
        statement.executeUpdate("SET FOREIGN_KEY_CHECKS = 0;")
        statement.executeUpdate("DROP TABLE Users, Kingdoms, Roles, PluginInfo;")
        statement.executeUpdate("SET FOREIGN_KEY_CHECKS = 1;")
        testAPI.databaseInit()
    }

}