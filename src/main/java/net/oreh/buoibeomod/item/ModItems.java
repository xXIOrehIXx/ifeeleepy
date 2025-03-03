package net.oreh.buoibeomod.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.oreh.buoibeomod.BuoiBeo;

public class ModItems {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BuoiBeo.MOD_ID);

	public static final RegistryObject<Item> BUOIBEO = ITEMS.register("buoibeo",
			() -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

	public static void register(IEventBus evenbus) {
		ITEMS.register(evenbus);
	}
}
