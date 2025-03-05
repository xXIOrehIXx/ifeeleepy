package net.oreh.buoibeomod.item.custom;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class DowsingRodItem extends Item {

	public DowsingRodItem(Properties pProperties) {
		super(pProperties);
	}

	@Override
	public InteractionResult useOn(UseOnContext pContext) {
		if (pContext.getLevel().isClientSide()) {
			BlockPos positionClicked = pContext.getClickedPos();
			Player player = pContext.getPlayer();
			boolean foundblock = false;
			int size = 3;
			StringBuilder coords = new StringBuilder();

			for (int i = 0; i <= positionClicked.getY() + 64; i++) {
				Map<BlockPos, Block> foundBlocks = new HashMap<>();
				foundBlocks = findDiamondOreInArea(pContext, positionClicked.below(i), size);

				for (Map.Entry<BlockPos, Block> entry : foundBlocks.entrySet()) {
					if (isDiamondOre(entry.getValue())) {
						coords.append(outputValueCoordiantes(entry.getKey(), player));
						foundblock = true;
					}
				}

				if (foundblock) {
					StringBuilder message = new StringBuilder("Found diamond at: ");
					message.append(coords.toString());
					player.sendMessage(new TextComponent(message.toString()), player.getUUID());
					break;
				}
			}

			if (!foundblock) {
				player.sendMessage(new TranslatableComponent("item.buoibeomod.dowsing_rod.no_diamond"),
						player.getUUID());
			}
		}

		pContext.getItemInHand().hurtAndBreak(1, pContext.getPlayer(),
				(player) -> player.broadcastBreakEvent(player.getUsedItemHand()));

		return super.useOn(pContext);
	}

	private Map<BlockPos, Block> findDiamondOreInArea(UseOnContext pContext, BlockPos pos, int size) {
		int radius = (int) size / 2;

		Map<BlockPos, Block> blocksInArea = new HashMap<BlockPos, Block>();
		for (int dx = -radius; dx <= radius; dx++) {
			for (int dz = -radius; dz <= radius; dz++) {
				blocksInArea.put(pos.offset(dx, 0, dz),
						pContext.getLevel().getBlockState(pos.offset(dx, 0, dz)).getBlock());
			}
		}

		return blocksInArea;
	}

	private String outputValueCoordiantes(BlockPos blockpos, Player player) {
		return "(" + blockpos.getX() + ", " + blockpos.getY() + ", " + blockpos.getZ() + ") ";
	}

	private boolean isDiamondOre(Block block) {
		return block == Blocks.DIAMOND_ORE || block == Blocks.DEEPSLATE_DIAMOND_ORE;
	}
}
