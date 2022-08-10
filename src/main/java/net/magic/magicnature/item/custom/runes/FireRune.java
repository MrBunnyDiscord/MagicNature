package net.magic.magicnature.item.custom.runes;

import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.InstantenousMobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class FireRune extends Item {

    public FireRune(Properties p_41383_) {
        super (p_41383_);
    }

    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        if(!pLevel.isClientSide () && pHand == InteractionHand.MAIN_HAND){
            pPlayer.addEffect (new MobEffectInstance (MobEffects.FIRE_RESISTANCE, 5000));
            pPlayer.getCooldowns ().addCooldown (this,8);
        }
        if(!pLevel.isClientSide () && pPlayer.isShiftKeyDown () && pHand == InteractionHand.MAIN_HAND){
            pPlayer.removeEffect (MobEffects.FIRE_RESISTANCE);
            pPlayer.getCooldowns ().addCooldown (this,5);
        }

        return super.use (pLevel, pPlayer, pHand);
    }
}
