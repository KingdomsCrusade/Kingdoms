package net.kingdomscrusade.kingdoms.mongo.pojo.Kingdoms;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.Date;

@RequiredArgsConstructor
public class KingdomsCreation {

    @JsonProperty("creation_date")
    public final @NotNull Date creationDate;

    public final @Getter @NotNull String creator;

    @JsonProperty("creation_date")
    public @NotNull Date getCreationDate() {
        return creationDate;
    }
}
