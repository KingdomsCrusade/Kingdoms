package net.kingdomscrusade.kingdoms.mongo.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class KingdomsDiscord {

    @JsonProperty("channel_id")
    public String channelID;

    @JsonProperty("role_id")
    public String roleID;

    @JsonProperty("channel_id")
    public String getChannelID() {
        return channelID;
    }

    @JsonProperty("channel_id")
    public void setChannelID(String channelID) {
        this.channelID = channelID;
    }

    @JsonProperty("role_id")
    public String getRoleID() {
        return roleID;
    }

    @JsonProperty("role_id")
    public void setRoleID(String roleID) {
        this.roleID = roleID;
    }
}
