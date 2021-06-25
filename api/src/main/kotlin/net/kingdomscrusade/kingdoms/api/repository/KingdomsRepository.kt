package net.kingdomscrusade.kingdoms.api.repository

import dagger.Provides
import net.kingdomscrusade.kingdoms.api.miscellaneous.Provider
import net.kingdomscrusade.kingdoms.api.model.KingdomModel
import net.kingdomscrusade.kingdoms.api.table.KingdomsTable
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class KingdomsRepository : IKingdomsRepository {

    companion object : Provider<IKingdomsRepository> {
        @Provides
        override fun provide() : IKingdomsRepository = KingdomsRepository()
    }

    override fun create(_obj: KingdomModel) {
        TODO("Not yet implemented")
    }

    override fun readById(_id: UUID) = transaction {
        KingdomsTable
            .select { KingdomsTable.id eq _id }
            .map {
                KingdomModel(
                    id = it[KingdomsTable.id],
                    name = it[KingdomsTable.name]
                )
            }
    }

    override fun readByName(_name: String) = transaction {
        KingdomsTable
            .select { KingdomsTable.name eq _name }
            .map {
                KingdomModel(
                    id = it[KingdomsTable.id],
                    name = it[KingdomsTable.name]
                )
            }
    }

    override fun replaceById(_targetId: UUID, _obj: KingdomModel) {
        TODO("Not yet implemented")
    }

    override fun replaceByName(_targetName: String, _obj: KingdomModel) {
        TODO("Not yet implemented")
    }

    override fun deleteById(_id: UUID) {
        transaction {
            KingdomsTable.deleteWhere { KingdomsTable.id eq _id }
        }
    }

    override fun deleteByName(_name: String) {
        transaction {
            KingdomsTable.deleteWhere { KingdomsTable.name eq _name }
        }
    }
}