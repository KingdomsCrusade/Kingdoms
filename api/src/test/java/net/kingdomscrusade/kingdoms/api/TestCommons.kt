package net.kingdomscrusade.kingdoms.api

import java.sql.Statement

open class TestCommons {
    // API VARIABLE
    protected val testAPI: KingdomsAPI = KingdomsAPI(
        url = "jdbc:mariadb://dev.kingdomscrusade.net:33061/test",
        usr = "root",
        pwd = "test"
    )
    protected val testStatement = testAPI.statement

    // OPERATIONS
    protected val exists: (table: String, column: String, value: String, statement: Statement) -> Boolean =
        { table: String, column: String, value: String, statement: Statement ->
            val query = statement.executeQuery("SELECT $column FROM $table")
            var result = false
            if (query.next()) {
                do if (query.getString(column) == value) result = true
                while (query.next())
            }
            result
        }

    protected fun restoreData(statement: Statement){
        // TODO Code update after database upgrade is implemented
        statement.executeUpdate("SET FOREIGN_KEY_CHECKS = 0;")
        statement.executeUpdate("DROP TABLE Users, Kingdoms, Roles, PluginInfo;")
        statement.executeUpdate("SET FOREIGN_KEY_CHECKS = 1;")
        testAPI.databaseInit()
    }

}