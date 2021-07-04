package net.kingdomscrusade.kingdoms.api.entrypoint.dsl.target

import java.util.*

interface ApiTarget {
    val type : TargetType
    val id : UUID?
    val name : String?
}