package io.anuke.mindustry.server;

import io.anuke.mindustry.core.Logic;
import io.anuke.mindustry.core.NetCommon;
import io.anuke.mindustry.core.NetServer;
import io.anuke.mindustry.core.World;
import io.anuke.mindustry.io.BlockLoader;
import io.anuke.mindustry.io.BundleLoader;
import io.anuke.ucore.modules.ModuleCore;

import static io.anuke.mindustry.Vars.*;

public class MindustryServer extends ModuleCore {
    World world1;
    @Override
    public void init(){
        headless = true;

        BundleLoader.load();
        BlockLoader.load();

        module(logic = new Logic());
        module(world = new World());
        module(world1 = new World());
        module(netServer = new NetServer());
        module(netCommon = new NetCommon());
        module(new ServerControl());
    }
}
