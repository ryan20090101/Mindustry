package io.anuke.mindustry.resource;

import com.sun.istack.internal.Nullable;
import io.anuke.mindustry.world.Block;

public class Recipe {
    public Block result;
    public ItemStack[] requirements;
    public Research research;
    public Section section;
    public boolean desktopOnly = false;

    public Recipe(Section section, @Nullable Research research , Block result, ItemStack... requirements){
        this.result = result;
        this.research = research;
        this.requirements = requirements;
        this.section = section;
    }

    public Recipe setDesktop(){
        desktopOnly = true;
        return this;
    }
}
