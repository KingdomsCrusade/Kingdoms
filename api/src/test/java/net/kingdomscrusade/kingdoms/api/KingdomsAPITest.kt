package net.kingdomscrusade.kingdoms.api

import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import java.sql.DriverManager
import java.sql.SQLSyntaxErrorException

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class KingdomsAPITest {

    private lateinit var testAPI: KingdomsAPI
    private val info = DatabaseInfo()

    @BeforeAll
    fun clearTables(){
        val db = DriverManager.getConnection( info.url, info.usr, info.pwd ).createStatement()
        try {
            db.executeUpdate("SET FOREIGN_KEY_CHECKS = 0;")
            db.executeUpdate("DROP TABLE Users, Kingdoms, Roles, PluginInfo;")
            db.executeUpdate("SET FOREIGN_KEY_CHECKS = 1;")
        } catch (s: SQLSyntaxErrorException) {print(s)}
    }

    @Test
    @DisplayName("API Initialization Test")
    fun initTest(){
        testAPI =  KingdomsAPI ( info.url, info.usr, info.pwd, info.driver )
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