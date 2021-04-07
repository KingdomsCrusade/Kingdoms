package net.kingdomscrusade.kingdoms.mongo.pojo.Players;

import org.bson.types.ObjectId;
import org.jetbrains.annotations.NotNull;

import java.util.Date;

public class PlayersKingdom {
    public PlayersKingdom(
            @NotNull String in,
            @NotNull ObjectId role,
            @NotNull Date since
    ) {
        this.in = in;
        this.role = role;
        this.since = since;
    }

    public final @NotNull String in;
    public @NotNull ObjectId role;
    public final @NotNull Date since;
}
