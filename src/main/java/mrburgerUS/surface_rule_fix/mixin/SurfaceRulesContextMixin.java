package mrburgerUS.surface_rule_fix.mixin;

import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.SurfaceRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(SurfaceRules.Context.SteepMaterialCondition.class)
public class SurfaceRulesContextMixin
{
	static abstract class SteepMaterialCondition extends SurfaceRules.LazyXZCondition
	{
		protected SteepMaterialCondition(SurfaceRules.Context context)
		{
			super(context);
		}

		/**
		 * @author mrburgerUS
		 * @reason Alter MOJANG code
		 */
		@Overwrite
		protected boolean compute()
		{
			// Starting Point Lifted directly from the vanilla SurfaceRules file for 26.2
			int chunkBlockX = this.context.blockX & 15;
			int chunkBlockZ = this.context.blockZ & 15;
			int zNorth = Math.max(chunkBlockZ - 1, 0);
			int zSouth = Math.min(chunkBlockZ + 1, 15);
			// Moved outside if statement
			int xWest = Math.max(chunkBlockX - 1, 0);
			int xEast = Math.min(chunkBlockX + 1, 15);

			ChunkAccess chunk = this.context.chunk;
			int heightNorth = chunk.getHeight(Heightmap.Types.WORLD_SURFACE_WG, chunkBlockX, zNorth);
			int heightSouth = chunk.getHeight(Heightmap.Types.WORLD_SURFACE_WG, chunkBlockX, zSouth);
			int heightWest = chunk.getHeight(Heightmap.Types.WORLD_SURFACE_WG, xWest, chunkBlockZ);
			int heightEast = chunk.getHeight(Heightmap.Types.WORLD_SURFACE_WG, xEast, chunkBlockZ);

			int heightNorthSouthDelta = Math.absExact(heightNorth - heightSouth);
			int heightEastWestDelta = Math.absExact(heightEast - heightWest);
			int maxHeightDelta = Math.max(heightNorthSouthDelta,heightEastWestDelta);
			return (maxHeightDelta > 3); // must exceed 3 blocks slope
		}
	}
}
