package net.kingdomscrusade.kingdoms.actions

import com.mongodb.client.model.Filters
import net.kingdomscrusade.kingdoms.Main

class DeleteKingdom {
    companion object{
        fun accept(kingdomName:String):Boolean{

            val collection = Main.getKingdomsCollection()

            if (
                collection.find()
                    .toList()
                    .stream()
                    .anyMatch{
                            k -> k.getName() == kingdomName
                    }
            )
                throw IllegalArgumentException("Kingdom not found!")

            return collection.deleteOne(
                Filters.eq(
                    "name", kingdomName
                )
            ).wasAcknowledged() // If the delete is successful
        }
    }
}