package io.anuke.mindustry.ui.dialogs;

import io.anuke.mindustry.Vars;
import io.anuke.mindustry.io.Platform;
import io.anuke.ucore.core.Core;
import io.anuke.ucore.core.Settings;
import io.anuke.ucore.scene.ui.ButtonGroup;
import io.anuke.ucore.scene.ui.ScrollPane;
import io.anuke.ucore.scene.ui.TextButton;
import io.anuke.ucore.scene.ui.layout.Table;
import io.anuke.ucore.util.Atlas;
import io.anuke.ucore.util.Log;
import static io.anuke.mindustry.Vars.ui;

public class TextureDialog extends FloatingDialog{

    public TextureDialog(){
        super("$text.settings.texture");
        addCloseButton();
        setup();
    }

    private void setup(){
        Table textures = new Table();
        textures.marginRight(24f).marginLeft(24f);
        ScrollPane pane = new ScrollPane(textures, "clear");
        pane.setFadeScrollBars(false);

        ButtonGroup<TextButton> group = new ButtonGroup<>();

        for(String texture : Vars.texturetypes){
            TextButton button = new TextButton(texture, "toggle");
            try{
                button.setChecked(Settings.getString("texture") == texture);
            }catch(Exception e){
                Settings.defaults("texture", "default");
            }
            button.clicked(() -> {
                if(Settings.getString("texture") == texture) return;
                Settings.putString("texture", texture);
                Settings.save();
                if(texture == "default") {
                    Core.atlas = new Atlas("sprites.atlas");
                }else{
                    Core.atlas = new Atlas(texture+".atlas");
                }
            });
            textures.add(button).group(group).update(t -> {
                t.setChecked(Settings.getString("texture") == texture);
            }).size(400f, 60f).row();
        }

        content().add(pane);
    }
}
