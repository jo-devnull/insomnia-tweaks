package github.jodevnull.insomnia;

import github.jodevnull.insomnia.cmds.MiscCommands;
import github.jodevnull.insomnia.event.PlayerEvents;
import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import org.slf4j.Logger;

@Mod(Insomnia.MODID)
public class Insomnia
{
    public static final String MODID = "insomnia";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final TagKey<EntityType<?>> IS_PROTECTED = TagKey.create(
        Registries.ENTITY_TYPE,
        ResourceLocation.fromNamespaceAndPath("friendlyfire", "player_protection")
    );

    public Insomnia(IEventBus modEventBus, ModContainer modContainer)
    {
        PlayerEvents.register();
        NeoForge.EVENT_BUS.addListener(this::registerCommand);
    }

    private void registerCommand(RegisterCommandsEvent event) {
        MiscCommands.createCommand(event.getDispatcher());
    }
}
