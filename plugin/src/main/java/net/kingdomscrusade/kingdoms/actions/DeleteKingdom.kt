package net.kingdomscrusade.kingdoms.actions

import com.mongodb.client.model.Filters
import net.kingdomscrusade.kingdoms.KingdomsMain

class DeleteKingdom {
    companion object{
        fun accept(kingdomName:String):Boolean{

            val kingdomsCollection = KingdomsMain.getKingdomsCollection()
            val playersCollection = KingdomsMain.getPlayersCollection()

            if (
                kingdomsCollection.find()
                    .toList()
                    .stream()
                    .anyMatch{
                            k -> k.getName() == kingdomName
                    }
            )
                throw IllegalArgumentException("Kingdom not found!")

            playersCollection.deleteMany(Filters.eq("kingdom.in", kingdomName))

            return kingdomsCollection.deleteOne(
                Filters.eq(
                    "name", kingdomName
                )
            ).wasAcknowledged() // If the delete is successful
        }
    }
}