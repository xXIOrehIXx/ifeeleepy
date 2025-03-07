package net.oreh.buoibeomod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.material.Fluid;

public class ConveyorBelt extends SlabBlock {

	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

	public ConveyorBelt(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH)); // Default facing
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite())
				.setValue(WATERLOGGED, false).setValue(TYPE, SlabType.BOTTOM);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(FACING); // Add the FACING property
	}

	@Override
	public void stepOn(Level pLevel, BlockPos pPos, BlockState pState, Entity pEntity) {
		if (!pLevel.isClientSide()) {
			Direction facing = pState.getValue(FACING);
			double speed = 0.1; // Adjust speed for smoother movement

			// Move both LivingEntity and ItemEntity the same way
			moveEntity(facing, pEntity, speed);
		}
		super.stepOn(pLevel, pPos, pState, pEntity);
	}
	
	@Override
	public boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
	    // Prevent placement on top if already a conveyor belt
	    if (context.getClickedFace() == Direction.UP && state.getBlock() instanceof ConveyorBelt) {
	        return false; // Deny stacking
	    }
	    return super.canBeReplaced(state, context);
	}
	
	@Override
	public boolean canPlaceLiquid(BlockGetter pLevel, BlockPos pPos, BlockState pState, Fluid pFluid) {
		return false;
	}

	private void moveEntity(Direction direction, Entity entity, double speed) {
		switch (direction) {
		case NORTH -> entity.push(0, 0, speed);
		case SOUTH -> entity.push(0, 0, -speed);
		case WEST -> entity.push(speed, 0, 0);
		case EAST -> entity.push(-speed, 0, 0);
		default -> throw new IllegalArgumentException("Unexpected value: " + direction);
		}

		if (entity instanceof Player player) {
			player.hurtMarked = true; // Ensures client updates motion correctly
		}
	}
}
