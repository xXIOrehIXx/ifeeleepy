package net.oreh.buoibeomod.block;

import java.util.function.Supplier;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.oreh.buoibeomod.BuoiBeo;
import net.oreh.buoibeomod.item.ModCreativeModTab;
import net.oreh.buoibeomod.item.ModItems;

public class ModBlocks {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,
			BuoiBeo.MOD_ID);

	public static final RegistryObject<Block> BUOI_BLOCK = registerBlock("buoi_block",
			() -> new Block(BlockBehaviour.Properties.of(Material.METAL).strength(9f).requiresCorrectToolForDrops()),
			ModCreativeModTab.BUOIBEO_TAB);

	private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block,
			CreativeModeTab tab) {
		RegistryObject<T> toReturn = BLOCKS.register(name, block);
		registerBlockItem(name, toReturn, tab);
		return toReturn;
	}

	private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block,
			CreativeModeTab tab) {
		return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
	}

	public static void register(IEventBus eventBus) {
		BLOCKS.register(eventBus);
	}
}
