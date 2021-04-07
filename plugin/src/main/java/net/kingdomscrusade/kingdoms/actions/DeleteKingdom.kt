package net.kingdomscrusade.kingdoms.actions

import com.mongodb.client.model.Filters
import net.kingdomscrusade.kingdoms.Main

class DeleteKingdom {
    companion object{
        fun accept(kingdomName:String):Boolean{

            val kingdomsCollection = Main.getKingdomsCollection()
            val playersCollection = Main.getPlayersCollection()

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