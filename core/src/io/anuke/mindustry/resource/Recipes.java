package io.anuke.mindustry.resource;

import com.badlogic.gdx.utils.Array;
import io.anuke.mindustry.Vars;
import io.anuke.mindustry.world.Block;
import io.anuke.mindustry.world.blocks.DefenseBlocks;
import io.anuke.mindustry.world.blocks.DistributionBlocks;
import io.anuke.mindustry.world.blocks.ProductionBlocks;
import io.anuke.mindustry.world.blocks.WeaponBlocks;
import io.anuke.mindustry.world.blocks.OtherBlocks;
import static io.anuke.mindustry.resource.Section.*;

public class Recipes {
	private static final Array<Recipe> list = Array.with(
			new Recipe(defense, null,DefenseBlocks.stonewall, stack(Item.stone, 12)),
			new Recipe(defense, null,DefenseBlocks.ironwall, stack(Item.iron, 12)),
			new Recipe(defense, null,DefenseBlocks.steelwall, stack(Item.steel, 12)),
			new Recipe(defense, null,DefenseBlocks.titaniumwall, stack(Item.titanium, 12)),
			new Recipe(defense, null,DefenseBlocks.diriumwall, stack(Item.dirium, 12)),
			new Recipe(defense, null,DefenseBlocks.steelwalllarge, stack(Item.steel, 12*4)),
			new Recipe(defense, null,DefenseBlocks.titaniumwalllarge, stack(Item.titanium, 12*4)),
			new Recipe(defense, null,DefenseBlocks.diriumwalllarge, stack(Item.dirium, 12*4)),
			new Recipe(defense, null,DefenseBlocks.door, stack(Item.steel, 3), stack(Item.iron, 3*4)).setDesktop(),
			new Recipe(defense, null,DefenseBlocks.largedoor, stack(Item.steel, 3*4), stack(Item.iron, 3*4*4)).setDesktop(),
			new Recipe(defense, null,DefenseBlocks.titaniumshieldwall, stack(Item.titanium, 16)),
			new Recipe(defense, null,DefenseBlocks.diriumshieldwall, stack(Item.titanium, 16), stack(Item.dirium, 12)),
			new Recipe(defense, null,DefenseBlocks.diriumalloywalllarge, stack(Item.titanium, 12*4), stack(Item.dirium, 12*4), stack(Item.steel,12*4)),
			new Recipe(defense, null,DefenseBlocks.diriumalloywall, stack(Item.titanium, 12), stack(Item.dirium, 12), stack(Item.steel,12)),

			new Recipe(distribution, null,DistributionBlocks.conveyor, stack(Item.stone, 1)),
			new Recipe(distribution, null,DistributionBlocks.steelconveyor, stack(Item.steel, 1)),
			new Recipe(distribution, null,DistributionBlocks.pulseconveyor, stack(Item.titanium, 1)),
			//new Recipe(distribution, null,DistributionBlocks.ultraconveyor, stack(Item.dirium, 1)),
			new Recipe(distribution, null,DistributionBlocks.combiner, stack(Item.stone, 1)),
			new Recipe(distribution, null,DistributionBlocks.router, stack(Item.stone, 2)),
			new Recipe(distribution, null,DistributionBlocks.buffer, stack(Item.steel, 2)),
			new Recipe(distribution, null,DistributionBlocks.junction, stack(Item.iron, 2)),
			new Recipe(distribution, null,DistributionBlocks.tunnel, stack(Item.iron, 2)),
			new Recipe(distribution, null,DistributionBlocks.conduit, stack(Item.steel, 1)),
			new Recipe(distribution, null,DistributionBlocks.pulseconduit, stack(Item.titanium, 1), stack(Item.steel, 1)),
			new Recipe(distribution, null,DistributionBlocks.liquidrouter, stack(Item.steel, 2)),
			new Recipe(distribution, null,DistributionBlocks.liquidjunction, stack(Item.steel, 2)),
			new Recipe(distribution, null,DistributionBlocks.sorter, stack(Item.steel, 2)),
			new Recipe(distribution, null,DistributionBlocks.coreOut, stack(Item.stone, 2)),
			new Recipe(distribution, null,DistributionBlocks.coreIn, stack(Item.stone, 2)),

			new Recipe(weapon, null,WeaponBlocks.turret, stack(Item.stone, 4)),
			new Recipe(weapon, null,WeaponBlocks.doubleturret, stack(Item.stone, 7)),
			new Recipe(weapon, null,WeaponBlocks.machineturret, stack(Item.iron, 8), stack(Item.stone, 10)),
			new Recipe(weapon, null,WeaponBlocks.shotgunturret, stack(Item.iron, 10), stack(Item.stone, 10)),
			new Recipe(weapon, null,WeaponBlocks.flameturret, stack(Item.iron, 12), stack(Item.steel, 9)),
			new Recipe(weapon, null,WeaponBlocks.sniperturret, stack(Item.iron, 15), stack(Item.steel, 10)),
			new Recipe(weapon, null,WeaponBlocks.laserturret, stack(Item.steel, 12), stack(Item.titanium, 12)),
			new Recipe(weapon, null,WeaponBlocks.mortarturret, stack(Item.steel, 25), stack(Item.titanium, 15)),
			new Recipe(weapon, null,WeaponBlocks.teslaturret, stack(Item.steel, 20), stack(Item.titanium, 25), stack(Item.dirium, 15)),
			new Recipe(weapon, null,WeaponBlocks.plasmaturret, stack(Item.steel, 10), stack(Item.titanium, 20), stack(Item.dirium, 15)),
			new Recipe(weapon, null,WeaponBlocks.chainturret, stack(Item.steel, 50), stack(Item.titanium, 25), stack(Item.dirium, 40)),
			new Recipe(weapon, null,WeaponBlocks.titanturret, stack(Item.steel, 70), stack(Item.titanium, 50), stack(Item.dirium, 55)),
			new Recipe(weapon, null,WeaponBlocks.artilleryturret, stack(Item.steel, 170), stack(Item.titanium, 150), stack(Item.dirium, 155)),

			new Recipe(crafting, null,ProductionBlocks.smelter, stack(Item.stone, 40), stack(Item.iron, 40)),
			new Recipe(crafting, null,ProductionBlocks.crucible, stack(Item.titanium, 50), stack(Item.steel, 50)),
			new Recipe(crafting, null,ProductionBlocks.coalpurifier, stack(Item.steel, 10), stack(Item.iron, 10)),
			new Recipe(crafting, null,ProductionBlocks.titaniumpurifier, stack(Item.steel, 30), stack(Item.iron, 30)),
			new Recipe(crafting, null,ProductionBlocks.oilrefinery, stack(Item.steel, 15), stack(Item.iron, 15)),
			new Recipe(crafting, null,ProductionBlocks.stoneformer, stack(Item.steel, 10), stack(Item.iron, 10)),
			new Recipe(crafting, null,ProductionBlocks.lavasmelter, stack(Item.steel, 30), stack(Item.titanium, 15)),
			new Recipe(crafting, null,ProductionBlocks.weaponFactory, stack(Item.steel, 60), stack(Item.iron, 60)).setDesktop(),
            new Recipe(crafting, null,ProductionBlocks.researchCenter, stack(Item.steel, 60), stack(Item.iron, 60)),

			new Recipe(production, null, ProductionBlocks.grounddrill, stack(Item.stone, 12)),
			new Recipe(production, null,ProductionBlocks.irondrill, stack(Item.stone, 25)),
			new Recipe(production, null,ProductionBlocks.coaldrill, stack(Item.stone, 25), stack(Item.iron, 40)),
			new Recipe(production, null,ProductionBlocks.titaniumdrill, stack(Item.iron, 50), stack(Item.steel, 50)),
			new Recipe(production, null,ProductionBlocks.uraniumdrill, stack(Item.iron, 40), stack(Item.steel, 40)),
			new Recipe(production, Research.unlockOmnidrill,ProductionBlocks.omnidrill, stack(Item.titanium, 40), stack(Item.dirium, 40)),
			new Recipe(production, null,ProductionBlocks.sieve, stack(Item.steel, 12)),

			new Recipe(power, null,ProductionBlocks.coalgenerator, stack(Item.iron, 30), stack(Item.stone, 20)),
			new Recipe(power, null,ProductionBlocks.thermalgenerator, stack(Item.steel, 30), stack(Item.iron, 30)),
			new Recipe(power, null,ProductionBlocks.combustiongenerator, stack(Item.iron, 30), stack(Item.stone, 20)),
			new Recipe(power, null,ProductionBlocks.rtgenerator, stack(Item.titanium, 20), stack(Item.steel, 20)),
			new Recipe(power, null,ProductionBlocks.nuclearReactor, stack(Item.titanium, 40), stack(Item.dirium, 40), stack(Item.steel, 50)),
			new Recipe(power, null,DistributionBlocks.powerbooster, stack(Item.steel, 8), stack(Item.iron, 8)),
			new Recipe(power, null,DistributionBlocks.powerlaser, stack(Item.steel, 3), stack(Item.iron, 3)),
			new Recipe(power, null,DistributionBlocks.powerlasercorner, stack(Item.steel, 4), stack(Item.iron, 4)),
			new Recipe(power, null,DistributionBlocks.powerlaserrouter, stack(Item.steel, 5), stack(Item.iron, 5)),

			new Recipe(power, null,DefenseBlocks.shieldgenerator, stack(Item.titanium, 30), stack(Item.dirium, 30)),
			new Recipe(power, null,DefenseBlocks.megaShield, stack(Item.titanium, 30*4), stack(Item.dirium, 30*4*2)),

			new Recipe(distribution, null,DistributionBlocks.teleporter, stack(Item.steel, 30), stack(Item.dirium, 40)),

			new Recipe(power, null,DefenseBlocks.repairturret, stack(Item.iron, 30)),
			new Recipe(power, null,DefenseBlocks.megarepairturret, stack(Item.iron, 20), stack(Item.steel, 30)),
			new Recipe(power, null,DefenseBlocks.ultrarepairturret, stack(Item.iron, 20), stack(Item.steel, 30), stack(Item.titanium, 40)),

			new Recipe(production, null,ProductionBlocks.pump, stack(Item.steel, 10)),
			new Recipe(production, Research.unlockFluxpump,ProductionBlocks.fluxpump, stack(Item.steel, 10), stack(Item.dirium, 5)),
            
            new Recipe(crafting, Research.unlockFluxpump,OtherBlocks.structurecore, stack(Item.steel, 10)),
            new Recipe(power, null,OtherBlocks.powervoid, stack(Item.stone, 1)),
			new Recipe(power, null,OtherBlocks.powersource, stack(Item.stone, 1)),
            new Recipe(distribution, null,OtherBlocks.itemspawner, stack(Item.stone, 1))
	);
	
	private static ItemStack stack(Item item, int amount){
		return new ItemStack(item, amount);
	}

	public static Array<Recipe> all(){
		return list;
	}

	public static Recipe getByResult(Block block){
		for(Recipe recipe : list){
			if(recipe.result == block){
				return recipe;
			}
		}
		return null;
	}
	public static Research getResearchByResult(Recipe targetRecipe){
		for(Recipe recipe : list){
			if(recipe == targetRecipe){
				return targetRecipe.research;
			}
		}
		return null;
	}	
	public static Recipe getByResearch(Research research){
		for(Recipe recipe : list){
			if(recipe.research == research){
				return recipe;
			}
		}
		return null;
	}
	public static Array<Recipe> getBy(Section section, Array<Recipe> r){
		for(Recipe recipe : list){
			if(recipe.section == section && !(Vars.android && recipe.desktopOnly))
				r.add(recipe);
		}
		
		return r;
	}
}
