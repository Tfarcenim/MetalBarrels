package tfar.metalbarrels.item;

import net.minecraft.world.level.block.BarrelBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.InteractionResult;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.Level;
import tfar.metalbarrels.util.UpgradeInfo;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public abstract class BarrelUpgradeItem extends Item {

    protected final UpgradeInfo upgradeInfo;

    public BarrelUpgradeItem(Properties properties, UpgradeInfo info) {
        super(properties);
        this.upgradeInfo = info;
    }

    @Nonnull
    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        BlockPos pos = context.getClickedPos();
        Level world = context.getLevel();
        ItemStack heldStack = context.getItemInHand();
        BlockState state = world.getBlockState(pos);

        if (player == null || !upgradeInfo.canUpgrade(world.getBlockState(pos))) {
            return InteractionResult.FAIL;
        }
        if (world.isClientSide || player.getPose() != Pose.CROUCHING)
            return InteractionResult.PASS;

        if (state.getBlock() instanceof BarrelBlock)
            if (state.getValue(BlockStateProperties.OPEN)) {
                player.displayClientMessage(Component.translatable("metalbarrels.in_use")
                        .withStyle(Style.EMPTY.applyFormat(ChatFormatting.RED)), true);
                return InteractionResult.PASS;
            }

        BlockEntity oldBarrel = world.getBlockEntity(pos);
        final List<ItemStack> oldBarrelContents = new ArrayList<>();

        Direction facing = Direction.NORTH;
        if (state.hasProperty(BlockStateProperties.HORIZONTAL_FACING)) {
            facing = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
        } else if (state.hasProperty(BlockStateProperties.FACING)) {
            facing = state.getValue(BlockStateProperties.FACING);
        }

        if (oldBarrel != null) {
            copyOldItems(world,pos,state, oldBarrel, oldBarrelContents);
            oldBarrel.setRemoved();
        }

        Block newBlock = upgradeInfo.end_block();
        BlockState newState = newBlock.defaultBlockState();

        if (newState.hasProperty(BlockStateProperties.HORIZONTAL_FACING)) {
            newState = newState.setValue(BlockStateProperties.HORIZONTAL_FACING, facing);
        } else if (newState.hasProperty(BlockStateProperties.FACING)) {
            newState = newState.setValue(BlockStateProperties.FACING, facing);
        }

        world.setBlock(pos, newState, 3);
        BlockEntity newBarrel = world.getBlockEntity(pos);
        if (newBarrel != null) {
            setNewItems(world, pos, newState, newBarrel, oldBarrelContents);
        }

        if (!player.getAbilities().instabuild)
            heldStack.shrink(1);

        player.displayClientMessage(Component.translatable("metalbarrels.upgrade_successful")
                .withStyle(Style.EMPTY.applyFormat(ChatFormatting.GREEN)), true);
        return InteractionResult.SUCCESS;
    }

    protected abstract void copyOldItems(Level level,BlockPos pos,BlockState state,BlockEntity blockEntity,List<ItemStack> list);
    protected abstract void setNewItems(Level level,BlockPos pos,BlockState state,BlockEntity blockEntity,List<ItemStack> list);

    public UpgradeInfo getUpgradeInfo() {
        return upgradeInfo;
    }
}