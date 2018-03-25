package io.anuke.mindustry.world.blocks.types.production;

import com.badlogic.gdx.graphics.Color;
import io.anuke.mindustry.Vars;
import io.anuke.mindustry.entities.TileEntity;
import io.anuke.mindustry.net.Net;
import io.anuke.mindustry.net.NetEvents;
import io.anuke.mindustry.resource.*;
import io.anuke.mindustry.world.Block;
import io.anuke.mindustry.world.BlockBar;
import io.anuke.mindustry.world.Tile;
import io.anuke.ucore.core.Effects;
import io.anuke.ucore.core.Timers;
import io.anuke.ucore.function.Listenable;
import io.anuke.ucore.graphics.Draw;
import io.anuke.ucore.scene.style.TextureRegionDrawable;
import io.anuke.ucore.scene.ui.ButtonGroup;
import io.anuke.ucore.scene.ui.Image;
import io.anuke.ucore.scene.ui.ImageButton;
import io.anuke.ucore.scene.ui.Tooltip;
import io.anuke.ucore.scene.ui.layout.Table;
import io.anuke.ucore.util.Mathf;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;

import static io.anuke.mindustry.Vars.*;

public class ResearchCenter extends Block{
    protected final int timerProgress = timers++;
    /**Time it takes to progress++*/
    protected int researchTime = 5;
    public ResearchCenter(String name){
        super(name);
        solid = true;
        destructible = true;
    }

    @Override
    public void init() {
        bars.add(new BlockBar(Color.ORANGE, true, tile -> (float)tile.<ResearchCenterEntity>entity().progress/100));
    }

    @Override
    public void update(Tile tile){
        ResearchCenterEntity ent = tile.entity();

        if(!ent.researching)
            return;

        Research res = global.getResearchById(ent.resID);

        for(ItemStack stack : res.requirements) {
            if (!(ent.items[stack.item.id] >= stack.amount))
                return;
        }
        if(ent.researching && ent.progress < 100 && Timers.get(timerProgress,researchTime)) {
            ent.progress++;
            ItemStack itemS = res.requirements[Mathf.random(res.requirements.length-1)];
            ent.items[itemS.item.id] -= Mathf.random(itemS.amount);
        }
        else if (ent.researching && ent.progress >= 100) {
            ent.researching = false;
            ent.progress = 0;
            if(Net.client()){
                NetEvents.handleResearch(res);
            }
            global.research(res);
            setConfigure(tile,ent.resID,(byte) 0,ent.progress);
            ui.hudfrag.buildRecipe();
        }
    }

    @Override
    public boolean isConfigurable(Tile tile){
        return true;
    }

    public void configure(Tile tile, byte... data) {
        ResearchCenterEntity entity = tile.entity();
        if(entity != null){
            entity.resID = data[0];
            entity.researching = data[1] == 1;
            entity.progress = data[2];
        }
    }

    @Override
    public void buildTable(Tile tile, Table table) {
        ResearchCenterEntity ent = tile.entity();

        int i = 0;

        Table content = new Table();

        for(Research res : Research.researches){
            
            if(!global.getResearchStatus(res.unlock)){continue;}
            
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

                if(!global.getResearchStatus(res)){
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
                if(global.getResearchStatus(res)){
                    tiptable.add("$text.researched").padTop(4).left();
                }
                tiptable.margin(8f);
            };

            run.listen();

            Tooltip<Table> tip = new Tooltip<>(tiptable, run);

            tip.setInstant(true);

            ButtonGroup<ImageButton> group = new ButtonGroup<>();

            ImageButton button = content.addImageButton("white", "toggle", 8*4, () -> {
                ent.resID = (byte) res.id;
                ent.researching = true;
                setConfigure(tile,ent.resID,(byte) 1,ent.progress);
                run.listen();
                Effects.sound("purchase");
            }).size(49f, 54f).padBottom(-5).group(group).get();

            button.setChecked(ent.resID == res.id);

            button.setDisabled(() -> global.getResearchStatus(res));
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
    public boolean acceptItem(Item item, Tile tile, Tile source){
        ResearchCenterEntity ent = tile.entity();

        if(!ent.researching)
            return false;
        for(ItemStack stack : global.getResearchById(ent.resID).requirements) {
            if (stack.item.id == item.id)
                return true;
        }
        return false;
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

        @Override
        public void write(DataOutputStream stream) throws IOException {
            stream.writeByte(resID);
            stream.writeByte(progress);
            stream.writeByte((byte)(researching ? 1 : 0));
        }

        @Override
        public void read(DataInputStream stream) throws IOException{
            resID = stream.readByte();
            progress = stream.readByte();
            researching = stream.readByte() == 1;
        }
    }
}
