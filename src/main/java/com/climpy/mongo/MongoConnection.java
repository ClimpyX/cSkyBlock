package com.climpy.mongo;

import com.climpy.SkyBlockC;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientException;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import lombok.Getter;
import org.bukkit.Bukkit;

import java.util.Collections;
import java.util.logging.Level;

@Getter
public class MongoConnection {
    private MongoCredential credential;
    private MongoClient mongoClient;
    private CollectionManager collectionManager;

    public void setupDatabaseConnection() {
        try {
            this.credential = MongoCredential.createCredential("root", "admin", "ananyanimdakarsm".toCharArray());
            this.mongoClient = new MongoClient(new ServerAddress("localhost", 27017), Collections.singletonList(this.credential));

            this.collectionManager = new CollectionManager(this.mongoClient);
            this.collectionManager.createDataConnection();
            SkyBlockC.getInstance().getLogger().log(Level.INFO, "[MongoDB] Veritabanı sunucusuna başarıyla bağlanıldı.");
        } catch (MongoClientException ex) {
            SkyBlockC.getInstance().getLogger().log(Level.SEVERE, "[MongoDB] Çalışan, yerel Mongo Veritabanı sunucusuna bağlanılamıyor!");
            Bukkit.getServer().shutdown();
        }
    }

    public void disableDatabaseConnection() {
        if (this.mongoClient != null) {
            SkyBlockC.getInstance().getLogger().log(Level.INFO, "[MongoDB] Veritabanı bağlantısı kapatılıyor..");

            try {
                this.mongoClient.close();
                SkyBlockC.getInstance().getLogger().log(Level.INFO, "[MongoDB] Veritabanı bağlantısı başarıyla kapatıldı.");
            } catch (Exception ex) {
                SkyBlockC.getInstance().getLogger().log(Level.INFO, "[MongoDB] Veritabanı verileri kaydedilemedi.");
            }
        }
    }
}