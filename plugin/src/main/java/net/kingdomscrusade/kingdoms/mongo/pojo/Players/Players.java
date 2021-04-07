package net.kingdomscrusade.kingdoms.mongo.pojo.Players;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.mongojack.Id;

import java.util.UUID;

public class Players {

    @JsonCreator
    public Players(
            @NotNull @Id UUID uuid,
            @NotNull String name,
            @NotNull PlayersKingdom kingdom
    ) {
        this.uuid = uuid;
        this.name = name;
        this.kingdom = kingdom;
    }

    @Id
    public final @NotNull UUID uuid;
    @Getter @Setter
    public @NotNull String name;
    @Getter
    public final PlayersKingdom kingdom;


    @Id
    public @NotNull UUID getUuid() {
        return uuid;
    }
}
