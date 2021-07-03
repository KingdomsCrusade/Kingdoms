package net.kingdomscrusade.kingdoms.api.repository

import dagger.Provides
import net.kingdomscrusade.kingdoms.api.miscellaneous.Provider
import net.kingdomscrusade.kingdoms.api.model.KingdomsModel
import net.kingdomscrusade.kingdoms.api.table.KingdomsTable
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import java.util.*

class KingdomsRepository : IKingdomsRepository {

    companion object : Provider<IKingdomsRepository> {
        @Provides
        override fun provide() : IKingdomsRepository = KingdomsRepository()
    }

    override fun create(_obj: KingdomsModel) {
        KingdomsTable.insert {
            it[id] = _obj.id
            it[name] = _obj.name
        }
    }

    override fun readById(_id: UUID) = transaction {
        KingdomsTable
            .select { KingdomsTable.id eq _id }
            .map {
                KingdomsModel(
                    id = it[KingdomsTable.id],
                    name = it[KingdomsTable.name]
                )
            }
    }

    override fun readByName(_name: String) = transaction {
        KingdomsTable
            .select { KingdomsTable.name eq _name }
            .map {
                KingdomsModel(
                    id = it[KingdomsTable.id],
                    name = it[KingdomsTable.name]
                )
            }
    }

    override fun replaceById(_targetId: UUID, _obj: KingdomsModel) {
        KingdomsTable.update({ KingdomsTable.id eq _targetId }) {
            it[id] = _obj.id
            it[name] = _obj.name
        }
    }

    override fun replaceByName(_targetName: String, _obj: KingdomsModel) {
        KingdomsTable.update({ KingdomsTable.name eq _targetName }) {
            it[id] = _obj.id
            it[name] = _obj.name
        }
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