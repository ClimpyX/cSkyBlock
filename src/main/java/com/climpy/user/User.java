package com.climpy.user;

import com.climpy.SkyAPI;
import com.mongodb.client.model.Filters;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.bson.Document;

import java.util.UUID;

@Data @Getter @Setter
public class User {
    private final UUID uniqueUUID;
    private String name;
    private long playTime;
    private double money;

    public User(UUID uniqueUUID) {
        this.uniqueUUID = uniqueUUID;
    }

    public User(Document document) {
        this.uniqueUUID = UUID.fromString(document.getString("uniqueUUID"));
        this.loadData(document);
    }

    private void loadData(Document document) {
       this.name = document.getString("name");
       this.playTime = document.getLong("playTime");
       this.money = document.getDouble("money");
    }

    public void save() {
        Document document = new Document();
        document.put("uniqueUUID", this.uniqueUUID.toString());
        document.put("playTime", this.playTime);
        document.put("money", this.money);

        SkyAPI.replaceOne("skyblock", Filters.eq("uniqueUUID", this.uniqueUUID.toString()), document, true);
    }
}
