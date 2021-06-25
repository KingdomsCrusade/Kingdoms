package net.kingdomscrusade.kingdoms.api

import io.kotest.common.ExperimentalKotest
import io.kotest.core.script.context
import io.kotest.core.spec.Spec
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.shouldContainAll
import net.kingdomscrusade.kingdoms.api.util.TestUtil
import net.kingdomscrusade.kingdoms.api.util.TestUtil.injectClearance
import net.kingdomscrusade.kingdoms.api.KingdomsApi as api

@ExperimentalKotest
class KingdomsApiTest : BehaviorSpec({

    val specInstance = this as Spec
    injectClearance(this)

    Given("database credentials") {
        val url = Config.url
        val usr = Config.usr
        val pwd = Config.pwd

        When("KingdomsAPI.connect is called") {
            api.connect(url, usr, pwd)
            TestUtil.lock(specInstance.javaClass.kotlin)

            Then("system should perform database migration") {
                val tableList: MutableList<String> = mutableListOf()
                TestUtil.api.getConnectionObject().createStatement().run {
                    executeQuery("SHOW TABLES").run {
                        while (next())
                            tableList += getString(1)
                    }
                }
                println("Table list: $tableList")

                tableList.shouldContainAll("Kingdoms", "Users", "Roles", "Permissions")
            }

            xThen ("user should be able to execute CRUD operations"){ TODO() }

        }

    }

})
