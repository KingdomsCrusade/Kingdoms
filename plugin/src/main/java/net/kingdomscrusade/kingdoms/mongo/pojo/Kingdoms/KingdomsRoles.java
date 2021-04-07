package net.kingdomscrusade.kingdoms.mongo.pojo.Kingdoms;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.kingdomscrusade.kingdoms.objects.enums.RoleFlags;
import org.bson.types.ObjectId;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

@RequiredArgsConstructor
public class KingdomsRoles {

    @JsonProperty("role_id")
    public final @NotNull ObjectId roleId;

    @JsonProperty("role_name")
    public @NotNull String roleName;

    @JsonProperty("created_by")
    public final @NotNull String createdBy;

    public @Getter @Setter ArrayList<RoleFlags> flags;

    public @Getter @Setter @NotNull Boolean mutable;

    @JsonProperty("role_id")
    public @NotNull ObjectId getRoleId() {
        return roleId;
    }

    @JsonProperty("role_name")
    public @NotNull String getRoleName() {
        return roleName;
    }

    @JsonProperty("role_name")
    public void setRoleName(@NotNull String roleName) {
        this.roleName = roleName;
    }

    @JsonProperty("created_by")
    public @NotNull String getCreatedBy() {
        return createdBy;
    }
}
