package com.climpy.mongo;

import com.climpy.SkyBlockC;
import com.climpy.mongo.extnds.SkyBlockCollection;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;

@Getter @Setter
public class CollectionManager {
    private Set<DataCollection> collections;
    private MongoClient mongoClient;

    public CollectionManager(MongoClient mongoClient) {
        this.collections = new HashSet<>();
        this.mongoClient = mongoClient;

        this.collections.add(new SkyBlockCollection());
    }

    public void createDataConnection() {

        try {
            SkyBlockC.getInstance().getLogger().log(Level.INFO, "[MongoDB] Collection işlemde şu anda kuruluyor..");

            try {
                MongoDatabase mongoDatabase = this.mongoClient.getDatabase("network");

                String[] names = new String[this.collections.size()];
                for (int index = 0; index < names.length;) {
                    for (DataCollection collection : this.collections) {
                        SkyBlockC.getInstance().getLogger().log(Level.INFO, "[MongoDB] [" + (index + 1) + "] - " + collection.getRegisteredName() + " collections sisteme işleniyor.");
                        names[index++] = collection.getRegisteredName();
                    }
                }

                try {
                    Thread collectionThread = new Thread(() -> {
                        Arrays.stream(names).forEach(name -> {
                            if (!mongoDatabase.listCollectionNames().into(new ArrayList<>()).contains(name)) {
                                mongoDatabase.createCollection(name);
                            }
                        });
                    });

                    collectionThread.setName("Collection-Create-Task");
                    collectionThread.start();
                } catch (Exception ex) {
                    SkyBlockC.getInstance().getLogger().log(Level.WARNING, ex.getMessage());
                }

                SkyBlockC.getInstance().getLogger().log(Level.INFO, "[MongoDB] Collection bağlantıları kuruldu, bağlantı bekleniyor!");
            } catch (IndexOutOfBoundsException | NullPointerException ex) {
                ex.printStackTrace();

                SkyBlockC.getInstance().getLogger().log(Level.SEVERE, "[MongoDB] Collection hatası, 5 saniye içinde kurtarılmaya çalışılıyor.");
                Bukkit.getScheduler().runTaskLater(SkyBlockC.getInstance(), this::createDataConnection, 5 * 20L);
            }

        } catch (Exception ex) {
           SkyBlockC.getInstance().getLogger().log(Level.SEVERE, "[MongoDB] Collection hatası, 5 saniye içinde kurtarılmaya çalışılıyor.");
            Bukkit.getScheduler().runTaskLater(SkyBlockC.getInstance(), this::createDataConnection, 5 * 20L);
        }
    }

    public MongoCollection getMongoCollection(String registeredName) {
        MongoConnection mongoConnection = SkyBlockC.getInstance().getMongoConnection();
        MongoClient mongoClient = mongoConnection.getMongoClient();

        MongoDatabase mongoDatabase = mongoClient.getDatabase("network");
        for (DataCollection dataCollection : this.collections) {
            if (dataCollection.getRegisteredName().equals(registeredName)) {
                return mongoDatabase.getCollection(registeredName);
            }
        }
        return null;
    }
}
