package net.kingdomscrusade.kingdoms.api.`interface`

import io.kotest.core.spec.style.StringSpec
import net.kingdomscrusade.kingdoms.api.KingdomsApi as api
import net.kingdomscrusade.kingdoms.api.model.Kingdom
import net.kingdomscrusade.kingdoms.api.model.PermissionType
import java.util.*

class DslInterfaceTest : StringSpec({

    // Below are the desired DSLs I'm trying to make

    val create = api.get() create {
        kingdom {
            id = UUID.randomUUID()
            name = "Midgard"
        }
        user {
            id = UUID.randomUUID()
            name = ".DarkGenerale"
            kingdomId = UUID.randomUUID()
            roleId = UUID.randomUUID()
        }
        role {
            id = UUID.randomUUID()
            name = "Manager"
            kingdomId = UUID.randomUUID()
            permissions = setOf(PermissionType.MANAGE)
        }
    }

    val read = api.get() read {
        target = Kingdom(name = "Midgard")
    }

    val update = api.get() update {
        target = Kingdom(name = "Midgard")
        change = Kingdom(name = "Starguard")
    }

    val delete = api.get() delete {
        target = Kingdom(name = "Midgard")
    }

})
