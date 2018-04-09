package io.anuke.mindustry.world;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntIntMap;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.IntMap.Entry;
import com.badlogic.gdx.utils.ObjectIntMap;

import io.anuke.mindustry.world.blocks.Blocks;
import io.anuke.mindustry.world.blocks.SpecialBlocks;
import io.anuke.mindustry.world.blocks.DefenseBlocks;
import io.anuke.mindustry.world.blocks.ProductionBlocks;
import io.anuke.mindustry.world.blocks.WeaponBlocks;
import io.anuke.mindustry.world.blocks.DistributionBlocks;
public class ColorMapper{
	/**maps color IDs to their actual RGBA8888 colors*/
	private static int[] colorIDS;
	/**Maps RGBA8888 colors to pair IDs.*/
	private static IntIntMap reverseIDs = new IntIntMap();

	private static ObjectIntMap<Block> reverseColors = new ObjectIntMap<>();
	private static Array<BlockPair> pairs = new Array<>();
	private static IntMap<BlockPair> colors = map(
		"ff0000", pair(Blocks.dirt, SpecialBlocks.enemySpawn),
		"00ff00", pair(Blocks.stone, SpecialBlocks.playerSpawn),
		"323232", pair(Blocks.stone),
        /**"323233", pair(Blocks.stone, ProductionBlocks.omnidrill),**/
		"646464", pair(Blocks.stone, Blocks.stoneblock),
		"50965a", pair(Blocks.grass),
		"5ab464", pair(Blocks.grass, Blocks.grassblock),
		"506eb4", pair(Blocks.water),
        /**"34a2d1", pair(Blocks.water, ProductionBlocks.pump),
        "319fce", pair(Blocks.water, ProductionBlocks.fluxpump),**/
		"465a96", pair(Blocks.deepwater),
        /**"435896", pair(Blocks.deepwater, ProductionBlocks.pump),
        "405696", pair(Blocks.deepwater, ProductionBlocks.fluxpump),**/
		"252525", pair(Blocks.blackstone),
		"575757", pair(Blocks.blackstone, Blocks.blackstoneblock),
		"988a67", pair(Blocks.sand),
		"e5d8bb", pair(Blocks.sand, Blocks.sandblock),
		"c2d1d2", pair(Blocks.snow),
		"c4e3e7", pair(Blocks.ice),
		"f7feff", pair(Blocks.snow, Blocks.snowblock),
		"6e501e", pair(Blocks.dirt),
		"ed5334", pair(Blocks.lava),
        /**"cc7626", pair(Blocks.lava, ProductionBlocks.pump),
        "c16d1f", pair(Blocks.lava, ProductionBlocks.fluxpump),**/
		"292929", pair(Blocks.oil),
        /**"230c02", pair(Blocks.oil, ProductionBlocks.pump),
        "210a01", pair(Blocks.oil, ProductionBlocks.fluxpump),**/
		"c3a490", pair(Blocks.iron),
        /**"bc9c87", pair(Blocks.iron, ProductionBlocks.omnidrill),*/
		"161616", pair(Blocks.coal),
        /**"1c1b1b", pair(Blocks.coal, ProductionBlocks.omnidrill),*/
		"6277bc", pair(Blocks.titanium),
        /**"5b71b7", pair(Blocks.titanium, ProductionBlocks.omnidrill),*/
		"83bc58", pair(Blocks.uranium),
        "edeff2", pair(Blocks.moon),
        "dbdde0", pair(Blocks.moon, Blocks.moonblock),
        /**"7bb252", pair(Blocks.uranium, ProductionBlocks.omnidrill),*/
        /*DefenseBlocks*/
        "3d3d3d", pair(Blocks.blackstone, DefenseBlocks.stonewall),
        "545454", pair(Blocks.blackstone, DefenseBlocks.ironwall),
        "828282", pair(Blocks.blackstone, DefenseBlocks.steelwall),
        "74818c", pair(Blocks.blackstone, DefenseBlocks.titaniumwall),

        "9bd190", pair(Blocks.blackstone, DefenseBlocks.diriumwall),
        "6d756b", pair(Blocks.blackstone, DefenseBlocks.steelwalllarge),
        "505b77", pair(Blocks.blackstone, DefenseBlocks.titaniumwalllarge),
        "507751", pair(Blocks.blackstone, DefenseBlocks.diriumwalllarge),
     
        "ffeeb7", pair(Blocks.blackstone, DefenseBlocks.door),
        "ffe593", pair(Blocks.blackstone, DefenseBlocks.largedoor),
        "385eaa", pair(Blocks.blackstone, DefenseBlocks.titaniumshieldwall),
        /* "09d89d", pair(Blocks.blackstone, ),*/
        
        /* DistributionBlocks */
        "3f4444", pair(Blocks.blackstone, DistributionBlocks.conveyor),
        "a9aaaa", pair(Blocks.blackstone, DistributionBlocks.steelconveyor),
        "43db88", pair(Blocks.blackstone, DistributionBlocks.pulseconveyor),
        "3c4741", pair(Blocks.blackstone, DistributionBlocks.router),
        
        "607067", pair(Blocks.blackstone, DistributionBlocks.junction),
        "76847c", pair(Blocks.blackstone, DistributionBlocks.tunnel),
        "c0d6d1", pair(Blocks.blackstone, DistributionBlocks.conduit),
        "c0c0d6", pair(Blocks.blackstone, DistributionBlocks.pulseconduit),
        
        "b8d6d1", pair(Blocks.blackstone, DistributionBlocks.liquidrouter),
        "a2b5b2", pair(Blocks.blackstone, DistributionBlocks.liquidjunction),
        "526662", pair(Blocks.blackstone, DistributionBlocks.sorter),
        "3ab286", pair(Blocks.blackstone, DistributionBlocks.teleporter),

        /* WeaponBlocks */
        "949998", pair(Blocks.blackstone, WeaponBlocks.turret),
        "6a6d6c", pair(Blocks.blackstone, WeaponBlocks.doubleturret),
        "cacecd", pair(Blocks.blackstone, WeaponBlocks.machineturret),
        "e0e5e4", pair(Blocks.blackstone, WeaponBlocks.shotgunturret),
        
        "e8edec", pair(Blocks.blackstone, WeaponBlocks.flameturret),
        "eaf2f0", pair(Blocks.blackstone, WeaponBlocks.sniperturret),
        "f1e1f2", pair(Blocks.blackstone, WeaponBlocks.laserturret),
        "d6c0d4", pair(Blocks.blackstone, WeaponBlocks.mortarturret),
        
        "ebef6b", pair(Blocks.blackstone, WeaponBlocks.teslaturret),
        "f2f756", pair(Blocks.blackstone, WeaponBlocks.plasmaturret),
        "d8a600", pair(Blocks.blackstone, WeaponBlocks.chainturret),
        "edb600", pair(Blocks.blackstone, WeaponBlocks.titanturret),

        /* ProductionBlocks */
        "ed6200", pair(Blocks.blackstone, ProductionBlocks.smelter),
        "007eed", pair(Blocks.blackstone, ProductionBlocks.crucible),
        "000d19", pair(Blocks.blackstone, ProductionBlocks.coalpurifier),
        "001a33", pair(Blocks.blackstone, ProductionBlocks.titaniumpurifier),
        
        "01080f", pair(Blocks.blackstone, ProductionBlocks.oilrefinery),
        "313233", pair(Blocks.blackstone, ProductionBlocks.stoneformer),
        "eda261", pair(Blocks.blackstone, ProductionBlocks.lavasmelter),
        "ff30ba", pair(Blocks.blackstone, ProductionBlocks.weaponFactory),
        
        /** "ebef6b", pair(Blocks.blackstone, ),
        "f2f756", pair(Blocks.blackstone, ),
        "d8a600", pair(Blocks.blackstone, ),
        "edb600", pair(Blocks.blackstone, ), **/

        /* Gatherers */
        "665b62", pair(Blocks.stone, ProductionBlocks.grounddrill),
        "a38d9a", pair(Blocks.iron, ProductionBlocks.irondrill),
        "260016", pair(Blocks.coal, ProductionBlocks.coaldrill),
        "e0e5e4", pair(Blocks.titanium, ProductionBlocks.titaniumdrill),
        
        "e8edec", pair(Blocks.uranium, ProductionBlocks.uraniumdrill),


        
        /**"ebef6b", pair(Blocks.blackstone, ),
        "f2f756", pair(Blocks.blackstone, ),
        "d8a600", pair(Blocks.blackstone, ),
        "edb600", pair(Blocks.blackstone, ), **/

        /* PowerBlocks */
        "2b1f00", pair(Blocks.blackstone, ProductionBlocks.coalgenerator),
        "c68f00", pair(Blocks.blackstone, ProductionBlocks.thermalgenerator),
        "0c0a04", pair(Blocks.blackstone, ProductionBlocks.combustiongenerator),
        "91aa00", pair(Blocks.blackstone, ProductionBlocks.rtgenerator),
        
        "c3e20d", pair(Blocks.blackstone, ProductionBlocks.nuclearReactor),
        "e5c440", pair(Blocks.blackstone, DistributionBlocks.powerbooster),
        "e2b70b", pair(Blocks.blackstone, DistributionBlocks.powerlaser),
        "dbb10a", pair(Blocks.blackstone, DistributionBlocks.powerlasercorner),
        
        "d8af0a", pair(Blocks.blackstone, DistributionBlocks.powerlaserrouter),
        "09d89d", pair(Blocks.blackstone, DefenseBlocks.shieldgenerator),
        "26c129", pair(Blocks.blackstone, DefenseBlocks.repairturret),
        "1bb71e", pair(Blocks.blackstone, DefenseBlocks.megarepairturret),

        /** Rocks **/
        "38b22c", pair(Blocks.grass, Blocks.shrub),
        "2cb294", pair(Blocks.snow, Blocks.icerock),
        "141212", pair(Blocks.blackstone, Blocks.blackrock),
        "424141", pair(Blocks.blackstone, Blocks.rock),


        
        /**"", pair(Blocks.floor, Blocks.block),
        "", pair(Blocks.floor, Blocks.block),
        "", pair(Blocks.floor, Blocks.block),
        "", pair(Blocks.floor, Blocks.block),**/

        /**Ship**/
        "FEFFFF", pair(Blocks.sand, Blocks.ship1floor1)
        

    );
	
	public static BlockPair get(int color){
		return colors.get(color);
	}

	public static int getColorByID(byte id){
		return colorIDS[id];
	}

	public static byte getColorID(int color){
		return (byte)reverseIDs.get(color, -1);
	}
	
	public static IntMap<BlockPair> getColors(){
		return colors;
	}
	
	public static Array<BlockPair> getPairs(){
		return pairs;
	}
	
	public static int getColor(Block block){
		return reverseColors.get(block, 0);
	}
	
	private static BlockPair pair(Block floor, Block wall){
		return new BlockPair(floor, wall);
	}
	
	private static BlockPair pair(Block floor){
		return new BlockPair(floor, Blocks.air);
	}
	
	private static IntMap<BlockPair> map(Object...objects){
		colorIDS = new int[objects.length/2];
		IntMap<BlockPair> colors = new IntMap<>();
		for(int i = 0; i < objects.length/2; i ++){
			int color = Color.rgba8888(Color.valueOf((String)objects[i*2]));
			colors.put(color, (BlockPair)objects[i*2+1]);
			pairs.add((BlockPair)objects[i*2+1]);
			colorIDS[i] = color;
			reverseIDs.put(color, i);
		}
		for(Entry<BlockPair> e : colors.entries()){
			reverseColors.put(e.value.wall == Blocks.air ? e.value.floor : e.value.wall, e.key);
		}
		return colors;
	}
	
	public static class BlockPair{
		public final Block floor, wall;
		
		public Block dominant(){
			return wall == Blocks.air ? floor : wall;
		}
		
		private BlockPair(Block floor, Block wall){
			this.floor = floor;
			this.wall = wall;
		}
	}
}
