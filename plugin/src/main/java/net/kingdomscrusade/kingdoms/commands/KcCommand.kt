package net.kingdomscrusade.kingdoms.commands

import dev.jorel.commandapi.CommandAPICommand

class KcCommand {

    fun kc(){

        CommandAPICommand("kc")
            .withAliases("kingdoms")
            .withSubcommand(CreateKingdomCommand().createKingdom)
            .register()

    }

}