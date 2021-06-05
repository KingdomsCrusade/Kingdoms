package net.kingdomscrusade.kingdoms.api

import io.kotest.common.ExperimentalKotest
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe
import org.jetbrains.exposed.sql.transactions.transaction

@ExperimentalKotest
class KingdomsAPITest : BehaviorSpec({

    Given("database credentials") {
        val url = Config.url
        val usr = Config.usr
        val pwd = Config.pwd
        When("KingdomsAPI.connect is called") {
            KingdomsAPI.connect(url, usr, pwd)
            Then("system should perform database migration") {
                val tableList: MutableList<String> = mutableListOf()
                transaction { exec("SHOW TABLES;") {
                    while (it.next()) {
                        tableList += it.getString(1)
                    }
                } }
                withData(listOf( "Kingdoms", "Users", "Roles", "Permissions" )) { table ->
                    tableList.contains(table) shouldBe true
                }
            }
        }
    }

})
