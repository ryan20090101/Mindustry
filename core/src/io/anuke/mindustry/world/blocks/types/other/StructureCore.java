package io.anuke.mindustry.world.blocks.types.other;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import io.anuke.mindustry.world.Block;
import io.anuke.mindustry.world.Tile;
import com.badlogic.gdx.files.FileHandle;
import java.io.File;
import io.anuke.mindustry.world.Structure;
public class StructureCore extends Block{

	public StructureCore(String name) {
		super(name);
		solid = true;
		destructible = true;
        health = 250;
	}
	@Override
	public void tapped(Tile tile){
        /**File folder = new File("structures/");
        File[] listOfFiles = folder.listFiles();
        int listElement = 0;
        for(FileHandle file : listOfFiles){
            Structure structure = new Structure();
            structure.pixmap = new Pixmap(file.sibling(listOfFiles[listElement] + ".png"));
            listElement = listElement + 1;
            for(int y : structure.getHeight()){
                for(int x : structure.getWidth()){
                    if(!(y==0&&x==0)){
                        
                        
                        
                    
                    
                    
                    
                    }
            
                }
            }   
                
        }**/
	}
}
