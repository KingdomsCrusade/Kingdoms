package net.kingdomscrusade.kingdoms.mongo.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.Date;

@Data
public class KingdomsCreation {

    @JsonProperty("creation_date")
    public final @NotNull Date creationDate;

    public final @NotNull String creator;

}
