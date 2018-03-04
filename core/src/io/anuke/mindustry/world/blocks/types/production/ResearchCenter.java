package io.anuke.mindustry.world.blocks.types.production;

import io.anuke.mindustry.Vars;
import io.anuke.mindustry.entities.TileEntity;
import io.anuke.mindustry.net.Net;
import io.anuke.mindustry.net.NetEvents;
import io.anuke.mindustry.resource.*;
import io.anuke.mindustry.world.Block;
import io.anuke.mindustry.world.Tile;
import io.anuke.ucore.core.Effects;
import io.anuke.ucore.core.Timers;
import io.anuke.ucore.function.Listenable;
import io.anuke.ucore.graphics.Draw;
import io.anuke.ucore.scene.style.TextureRegionDrawable;
import io.anuke.ucore.scene.ui.ImageButton;
import io.anuke.ucore.scene.ui.Tooltip;
import io.anuke.ucore.scene.ui.layout.Table;
import io.anuke.ucore.util.Timer;

import static io.anuke.mindustry.Vars.*;

public class ResearchCenter extends Block{

    protected final int timerProgress = timers++;

    /**Time it takes to progress++*/
    protected int researchTime;

    public ResearchCenter(String name){
        super(name);
        solid = true;
        destructible = true;
    }

    @Override
    public void update(Tile tile){
        ResearchCenterEntity ent = tile.entity();

        if(!ent.researching)
            return;

        boolean hasItems;
        Research res = world.getResearchById(ent.resID);

        for(ItemStack stack : res.requirements) {
            if (!(ent.items[stack.item.id] >= stack.amount))
                return;
        }
        
        if(ent.researching && ent.progress < 100 && Timers.get(timerProgress,researchTime)) {
            ent.progress++;
        }
        else if (ent.researching && ent.progress >= 100) {
            ent.researching = false;
            Research res = world.getResearchById(ent.resID);
            world.research(res);
            state.inventory.removeItems(res.requirements);
        }
    }

    @Override
    public boolean isConfigurable(Tile tile){
        return !Vars.android;
    }

    @Override
    public void buildTable(Tile tile, Table table) {
        ResearchCenterEntity ent = tile.entity();

        int i = 0;

        Table content = new Table();

        for(Research res : Research.researches){

            ItemStack[] requirements = res.requirements;

            Table tiptable = new Table();

            Listenable run = ()->{
                tiptable.clearChildren();

                String description = res.description;

                tiptable.background("pane");
                tiptable.add("[orange]" + res.localizedName(), 0.5f).left().padBottom(2f);

                Table reqtable = new Table();

                tiptable.row();
                tiptable.add(reqtable).left();

                if(!world.getResearchStatus(res)){
                    for(ItemStack s : requirements){

                        int amount = Math.min(state.inventory.getAmount(s.item), s.amount);
                        reqtable.addImage(s.item.region).padRight(3).size(8*2);
                        reqtable.add(
                                (amount >= s.amount ? "" : "[RED]")
                                        + amount + " / " +s.amount, 0.5f).left();
                        reqtable.row();
                    }
                }

                tiptable.row();
                tiptable.add().size(4);
                tiptable.row();
                tiptable.add("[gray]" + description).left();
                tiptable.row();
                if(world.getResearchStatus(res)){
                    tiptable.add("$text.researched").padTop(4).left();
                }
                tiptable.margin(8f);
            };

            run.listen();

            Tooltip<Table> tip = new Tooltip<>(tiptable, run);

            tip.setInstant(true);

            ImageButton button = content.addImageButton("white", 8*4, () -> {
                ent.resID = (byte) res.id;
                ent.researching = true;
                world.research(res);
                run.listen();
                Effects.sound("purchase");
            }).size(49f, 54f).padBottom(-5).get();

            button.setDisabled(() -> world.getResearchStatus(res) || !state.inventory.hasItems(requirements));
            //button.getStyle().imageUp = new TextureRegionDrawable(res.region);
            button.getStyle().imageUp = new TextureRegionDrawable(Draw.region(res.name));
            button.addListener(tip);

            if(++i % 3 == 0){
                content.row();
            }
        }

        table.add(content).padTop(140f);
    }


    @Override
    public TileEntity getEntity() {
        return new ResearchCenterEntity();
    }

    public class ResearchCenterEntity extends TileEntity{
        public byte resID;     //research id, byte until more required
        public byte progress; //this only needs to be from 0 to 100
        public boolean researching;
        //extra vars maybe?
    }
}
