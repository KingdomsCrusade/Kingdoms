package net.kingdomscrusade.kingdoms.api.`interface`

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import net.kingdomscrusade.kingdoms.api.Config
import net.kingdomscrusade.kingdoms.api.varclass.PermissionType
import net.kingdomscrusade.kingdoms.api.KingdomsApi as api

class CreateStatementTest : BehaviorSpec ({
    Given("a proper api connection") {
        api.connect(Config.url, Config.usr, Config.pwd)

        When("`create` function is called with some data objects") {
            val statement = api.get() create {
                val kingdom = kingdom {
                    // Id is auto generated
                    name = "Midgard"
                }.getId()
                role {
                    name = "PogChampion"
                    kingdomId = kingdom
                    permissions = setOf(PermissionType.INTERACT)
                }
            }

            Then("calling its `execute` function should return UUID list with size of 1") {
                val uuid = statement.execute() // Should print 2 lines for now to show its success state
                uuid.size shouldBe 1
            }

        }
    }
})