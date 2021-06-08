package net.kingdomscrusade.kingdoms.api

object KingdomsApiManager {
    private var instance : KingdomsApi? = null

    fun set(api: KingdomsApi) = let { instance = api }
    fun get(): KingdomsApi = instance ?: throw IllegalStateException("No database connected.")
}