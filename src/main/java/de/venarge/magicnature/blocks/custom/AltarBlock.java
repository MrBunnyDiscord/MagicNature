package de.venarge.magicnature.blocks.custom;

import com.google.common.collect.ImmutableMap;
import com.mojang.realmsclient.dto.WorldDownload;
import de.venarge.magicnature.MagicNature;
import de.venarge.magicnature.blocks.entities.ModBlockEntitys;
import de.venarge.magicnature.blocks.entities.custom.altar.AltarBlockEntity;
import de.venarge.magicnature.screen.AltarBlockMenu;
import de.venarge.magicnature.screen.AltarBlockScreen;
import de.venarge.magicnature.util.coord.Coord;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.commands.arguments.coordinates.WorldCoordinate;
import net.minecraft.commands.arguments.coordinates.WorldCoordinates;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.storage.WorldData;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class AltarBlock extends BaseEntityBlock {
    private static final Component CONTAINER_TITLE = new TranslatableComponent ("container.altar");

    private static final int ELEMENTS_IN_COMPLETE_ALTAR = 6;

    public AltarBlock(Properties p_49224_) {
        super (p_49224_);
    }

    // BLOCK ENTITY
    @Override
    public RenderShape getRenderShape(BlockState p_49232_) {
        return RenderShape.MODEL;
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock ( ) == pNewState.getBlock ( )) {
            BlockEntity blockEntity = pLevel.getBlockEntity (pPos);

            if (blockEntity instanceof AltarBlockEntity) {
                ((AltarBlockEntity) blockEntity).drops ( );
            }
        }

        super.onRemove (pState, pLevel, pPos, pNewState, pIsMoving);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pResult) {
        if(pLevel.isClientSide){
            return InteractionResult.SUCCESS;
        } else {
            pPlayer.openMenu (pState.getMenuProvider (pLevel,pPos));

            return InteractionResult.CONSUME;
        }
    }

    public MenuProvider getMenuProvider(BlockState state,Level level, BlockPos pos){
        return new SimpleMenuProvider ((p_52229_, p_52230_, p_52231_) -> {
            return new AltarBlockMenu (p_52229_,p_52230_,new AltarBlockEntity (pos, state));
        }, CONTAINER_TITLE);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new AltarBlockEntity (pPos,pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntity) {
        return createTickerHelper (pBlockEntity, ModBlockEntitys.ALTAR_BLOCK_ENTITY.get (),
                AltarBlockEntity::tick);
    }

    private void updateMultiBlock(Level level, int x, int y, int z, Coord exclude){
        if(!level.isClientSide){
            ArrayList<Coord> visited = new ArrayList<> (  );
            ArrayList<Coord> toVisit = new ArrayList<> (  );
            toVisit.add (new Coord (x,y,z));
            boolean valid = true;

            while(true){
                Coord coord;
                int neighbours;
                do{
                    if(toVisit.size ()<=0){
                        coord = valid && visited.size () == 6 ? (Coord) visited.get (0) : null;
                        Iterator i$ = visited.iterator ();

                        while (i$.hasNext ()){
                            Coord coord1 = (Coord)i$.next ();
                            BlockEntity be = coord1.getBlockEntity (level);
                            if(be != null && be instanceof AltarBlockEntity){
                                AltarBlockEntity abe = (AltarBlockEntity) be;
                                abe.setCore (coord1);
                            }
                        }
                        
                        if(exclude != null){
                            BlockEntity be= exclude.getBlockEntity (level);
                            if(be != null && be instanceof AltarBlockEntity){
                                AltarBlockEntity abe = (AltarBlockEntity) be;
                                abe.setCore ((Coord) null);
                                return;
                            }
                        }
                        return;
                    }
                    coord = (Coord) toVisit.get (0);
                    toVisit.remove (0);
                    neighbours = 0;
                    Coord[] arr$ = new Coord[]{coord.north (), coord.south (), coord.east (), coord.west ()};
                    int len$ = arr$.length;
                    
                    for(int i = 0;i < len$; i++){
                        Coord newCoord = arr$[i];
                        if(!visited.contains (newCoord) && !toVisit.contains (newCoord)){
                            toVisit.add (newCoord);
                        }
                        ++neighbours;
                    }
                } while (coord.equals (exclude));
                if (neighbours < 2 || neighbours > 3) {
                    valid = false;
                }

                visited.add(coord);
            }
        }
    }
}
