@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package net.kingdomscrusade.kingdoms.api.repository

import net.kingdomscrusade.kingdoms.api.model.Kingdom
import net.kingdomscrusade.kingdoms.api.table.KingdomsTable
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import java.util.*

class KingdomsRepository : IKingdomsRepository {
    override fun create(_id: UUID, _name: String) {
        transaction {
            KingdomsTable.insert {
                it[id] = _id
                it[name] = _name
            }
        }
    }

    override fun readById(_id: UUID) = transaction {
        KingdomsTable
            .select { KingdomsTable.id eq _id }
            .map {
                Kingdom(
                    id = it[KingdomsTable.id],
                    name = it[KingdomsTable.name]
                )
            }
    }

    override fun readByName(_name: String) = transaction {
        KingdomsTable
            .select { KingdomsTable.name eq _name }
            .map {
                Kingdom(
                    id = it[KingdomsTable.id],
                    name = it[KingdomsTable.name]
                )
            }
    }

    override fun updateById(_targetId: UUID, _name: String?) {
        transaction {
            KingdomsTable.update({ KingdomsTable.id eq _targetId }) {
                if (!_name.isNullOrBlank()) it[name] = _name
            }
        }
    }

    override fun updateByName(_targetName: String, _name: String?) {
        transaction {
            KingdomsTable.update({ KingdomsTable.name eq _targetName }) {
                if (!_name.isNullOrBlank()) it[name] = _name
            }
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