package net.oreh.buoibeomod.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModTab {
	public static final CreativeModeTab BUOIBEO_TAB = new CreativeModeTab("buoibeotab") {

		@Override
		public ItemStack makeIcon() {
			return new ItemStack(ModItems.BUOIBEO.get());
		}
	};
}
