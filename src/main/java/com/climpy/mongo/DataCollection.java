package com.climpy.mongo;

import com.climpy.SkyBlockC;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class DataCollection {
    private final SkyBlockC bedrockPlugin;
    private String registeredName;

    public DataCollection(SkyBlockC bedrockPlugin, String registeredName) {
        this.bedrockPlugin = bedrockPlugin;
        this.registeredName = registeredName;
    }

}
