package icycream;

import icycream.common.registry.BlockRegistryHandler;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file

/**
 * @author Takakura-Anri
 */
@Mod(IcyCream.MODID)
public class IcyCream
{
    public static final String MODID = "icycream";
    // Directly reference a log4j logger.
    public static final Logger log = LogManager.getLogger(MODID);

    public IcyCream() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        new BlockRegistryHandler(modEventBus);
    }
}
