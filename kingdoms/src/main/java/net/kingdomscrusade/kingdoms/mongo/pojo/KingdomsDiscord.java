package net.kingdomscrusade.kingdoms.mongo.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class KingdomsDiscord {

    @JsonProperty("channel_id")
    public String channelID;

    @JsonProperty("role_id")
    public String roleID;

}
