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
import tfar.metalbarrels.blockentity.MetalBarrelBlockEntity;
import tfar.metalbarrels.platform.Services;
import tfar.metalbarrels.util.UpgradeInfo;

import java.util.ArrayList;
import java.util.List;

public class BarrelUpgradeItem extends Item {

    protected final UpgradeInfo upgradeInfo;

    public BarrelUpgradeItem(Properties properties, UpgradeInfo info) {
        super(properties);
        this.upgradeInfo = info;
    }

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
        if (world.isClientSide() || player.getPose() != Pose.CROUCHING)
            return InteractionResult.PASS;

        if (state.getBlock() instanceof BarrelBlock)
            if (state.getValue(BlockStateProperties.OPEN)) {
                player.sendOverlayMessage(Component.translatable("metalbarrels.in_use")
                        .withStyle(Style.EMPTY.applyFormat(ChatFormatting.RED)));
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

        player.sendOverlayMessage(Component.translatable("metalbarrels.upgrade_successful")
                .withStyle(Style.EMPTY.applyFormat(ChatFormatting.GREEN)));
        return InteractionResult.SUCCESS;
    }



    protected void copyOldItems(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity, List<ItemStack> list) {
        Services.PLATFORM.copyOldItems(level,pos,state, blockEntity,list);
        /*Storage<ItemVariant> storageViews = ItemStorage.SIDED.find(level, pos, state, blockEntity, null);
        if (storageViews != null) {
            for (StorageView<ItemVariant> storageView : storageViews) {
                ItemVariant itemVariant = storageView.getResource();
                if (itemVariant.isBlank()) {
                    list.add(ItemStack.EMPTY);
                } else {
                    ItemStack stack = itemVariant.toStack();
                    stack.setCount((int)storageView.getAmount());
                    list.add(stack);
                }
            }
        }*/
    }

    protected void setNewItems(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity, List<ItemStack> list) {
        if (blockEntity instanceof MetalBarrelBlockEntity metalBarrelBlockEntity) {
            metalBarrelBlockEntity.resourceHandler.setItems(list);
        }
    }

    public UpgradeInfo getUpgradeInfo() {
        return upgradeInfo;
    }
}