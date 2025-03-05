package net.oreh.buoibeomod.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.oreh.buoibeomod.BuoiBeo;
import net.oreh.buoibeomod.item.custom.DownsingRodItem;

public class ModItems {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BuoiBeo.MOD_ID);

	public static final RegistryObject<Item> BUOIBEO = ITEMS.register("buoibeo",
			() -> new Item(new Item.Properties().tab(ModCreativeModTab.BUOIBEO_TAB)));

	public static final RegistryObject<Item> BUOIBE = ITEMS.register("buoibe",
			() -> new Item(new Item.Properties().tab(ModCreativeModTab.BUOIBEO_TAB)));
	
	public static final RegistryObject<Item> DOWNSINGROD = ITEMS.register("downsing_rod",
			() -> new DownsingRodItem(new Item.Properties().tab(ModCreativeModTab.BUOIBEO_TAB).durability(100)));
	
	public static void register(IEventBus evenbus) {
		ITEMS.register(evenbus);
	}
}
