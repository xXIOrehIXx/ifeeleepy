package net.oreh.buoibeomod.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class DownsingRodItem extends Item {

	public DownsingRodItem(Properties pProperties) {
		super(pProperties);
	}

	@Override
	public InteractionResult useOn(UseOnContext pContext) {
		if (pContext.getLevel().isClientSide()) {
			BlockPos positionClicked = pContext.getClickedPos();
			Player player = pContext.getPlayer();
			boolean foundblock = false;

			for (int i = 0; i <= positionClicked.getY() + 64; i++) {
				Block blockBelow = pContext.getLevel().getBlockState(positionClicked.below(i)).getBlock();

				if (isValuableBlock(blockBelow)) {
					outputValueCoordiantes(positionClicked.below(i), player, blockBelow);
					foundblock = true;
					break;
				}
			}

			if (!foundblock) {
				player.sendMessage(new TranslatableComponent("item.buoibeomod.downsing_rod.no_diamonod"),
						player.getUUID());
			}
		}
		
		pContext.getItemInHand().hurtAndBreak(1, pContext.getPlayer(), (player) -> player.broadcastBreakEvent(player.getUsedItemHand()));
		
		return super.useOn(pContext);
	}

	private void outputValueCoordiantes(BlockPos blockpos, Player player, Block blockBelow) {
		player.sendMessage(new TextComponent("Found: " + blockBelow.asItem().getRegistryName().toString() + "("
				+ blockpos.getX() + ", " + blockpos.getY() + ", " + blockpos.getZ() + ")"), player.getUUID());
	}

	private boolean isValuableBlock(Block block) {
		return block == Blocks.DIAMOND_ORE;
	}
}
