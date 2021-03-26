package net.kingdomscrusade.kingdoms.mongo.pojo;

import lombok.Data;
import org.bson.types.ObjectId;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

@Data
public class KingdomsMembers {

    public final @NotNull UUID uuid;

    public final @NotNull String username;

    public ArrayList<ObjectId> role;

    public final @NotNull Date since;

}
