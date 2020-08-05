package icycream.common.registry;

import net.minecraft.server.MinecraftServer;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;

/**
 * @author lyt
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ServerHandler {
    private static MinecraftServer minecraftServer;

    public static MinecraftServer getServerInstance() {
        return minecraftServer;
    }

    @SubscribeEvent
    public static void onServerStarted(FMLServerStartedEvent event) {
        minecraftServer = event.getServer();
    }
}
