package net.kingdomscrusade.kingdoms.mongo.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import net.kingdomscrusade.kingdoms.objects.enums.RoleFlags;
import org.bson.types.ObjectId;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

@Data
public class KingdomsRoles {

    @JsonProperty("role_id")
    public final @NotNull ObjectId roleId;

    @JsonProperty("role_name")
    public @NotNull String roleName;

    @JsonProperty("created_by")
    public final @NotNull String createdBy;

    public ArrayList<RoleFlags> flags;

    public @NotNull Boolean mutable;

}
