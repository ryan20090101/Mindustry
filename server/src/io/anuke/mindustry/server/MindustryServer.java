package io.anuke.mindustry.server;

import io.anuke.mindustry.command.CommandSystem;
import io.anuke.mindustry.core.*;
import io.anuke.mindustry.io.BlockLoader;
import io.anuke.mindustry.io.BundleLoader;
import io.anuke.ucore.modules.ModuleCore;

import static io.anuke.mindustry.Vars.*;

public class MindustryServer extends ModuleCore {
    private String[] args;

    public MindustryServer(String[] args){
        this.args = args;
    }

    @Override
    public void init(){
        headless = true;

        BundleLoader.load();
        BlockLoader.load();

        module(logic = new Logic());
        module(global = new Global());
        world = new World[dimensionIds];
        for (int i=0;i<dimensionIds;i++){
            module(world[i] = new World(i));
        }
        module(netServer = new NetServer());
        module(netCommon = new NetCommon());
<<<<<<< HEAD
        module(commandSystem = new CommandSystem());
        module(new ServerControl());
=======
        module(new ServerControl(args));
>>>>>>> upstream/master
    }
}
