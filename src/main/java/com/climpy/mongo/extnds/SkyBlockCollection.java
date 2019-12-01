package com.climpy.mongo.extnds;

import com.climpy.SkyBlockC;
import com.climpy.mongo.DataCollection;

public class SkyBlockCollection extends DataCollection {

    public SkyBlockCollection() {
        super(SkyBlockC.getInstance(), "skyblock");
    }

}
