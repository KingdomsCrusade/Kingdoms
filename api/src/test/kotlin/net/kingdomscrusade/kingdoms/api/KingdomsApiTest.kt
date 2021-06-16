package net.kingdomscrusade.kingdoms.api

import io.kotest.common.ExperimentalKotest
import io.kotest.core.script.context
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe
import org.jetbrains.exposed.sql.transactions.transaction
import net.kingdomscrusade.kingdoms.api.KingdomsApi as api

@ExperimentalKotest
class KingdomsApiTest : BehaviorSpec({

    Given("database credentials") {
        val url = Config.url
        val usr = Config.usr
        val pwd = Config.pwd
        When("KingdomsAPI.connect is called") {
            api.connect(url, usr, pwd)
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
            Then ("user should be able to execute CRUD operations"){
                context("create") { TODO() }
                context("read") { TODO() }
                context("update") { TODO() }
                context("delete") { TODO() }
            }
        }
        When("KingdomAPI.connect is not called") {
            Then ("user should not be able to execute CRUD operations"){
                context("create") { TODO() }
                context("read") { TODO() }
                context("update") { TODO() }
                context("delete") { TODO() }
            }
        }
    }

})
