package mrburgerUS.surface_rule_fix;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SurfaceRuleFixMod implements ModInitializer
{
	public static final String MOD_NAME = "Omnidirectional Steep Surface Rule";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

	@Override
	public void onInitialize()
	{
		LOGGER.info("ALL DIRECTIONS ARE... MINE!");
	}
}
