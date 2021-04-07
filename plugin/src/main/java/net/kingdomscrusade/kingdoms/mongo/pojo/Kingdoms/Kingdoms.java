package net.kingdomscrusade.kingdoms.mongo.pojo.Kingdoms;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.mongojack.Id;

import java.util.ArrayList;

public class Kingdoms {

    @JsonCreator
    public Kingdoms(
            @NotNull @Id String name,
            @NotNull KingdomsCreation creation,
            @NotNull ArrayList<KingdomsRoles> roles
    ) {
        this.name = name;
        this.creation = creation;
        this.roles = roles;
    }

    @Id
    public final @NotNull String name;

    @Getter
    public final @NotNull KingdomsCreation creation;

    @Getter @Setter
    public KingdomsDiscord discord;

    @Getter @Setter
    public @NotNull ArrayList<KingdomsRoles> roles;

    @Id
    public @NotNull String getName() {
        return name;
    }

    public KingdomsRoles getRole(String roleName){
        for (KingdomsRoles role: roles){
            if (role.roleName.equals(roleName)) return role;
        }
        return null;
    }
}
