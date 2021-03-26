package net.kingdomscrusade.kingdoms.mongo.pojo;

import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.mongojack.Id;

import java.util.ArrayList;

@Data
public class Kingdoms {

    @Id
    public Long id;

    public final @NotNull String name;

    public final @NotNull KingdomsCreation creation;

    public KingdomsDiscord discord;

    public @NotNull ArrayList<KingdomsRoles> roles;

    public @NotNull ArrayList<KingdomsMembers> members;

}
