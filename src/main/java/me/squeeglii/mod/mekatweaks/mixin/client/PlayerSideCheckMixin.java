package me.squeeglii.mod.mekatweaks.mixin.client;

import me.squeeglii.mod.mekatweaks.IJetpackUser;
import mekanism.client.ClientTickHandler;
import mekanism.common.item.interfaces.IJetpackItem;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
@Mixin(LocalPlayer.class)
public class PlayerSideCheckMixin implements IJetpackUser {

    @Override
    public boolean mekanismTweaks$checkJetpackUseOnLogicalSide(LivingEntity entity) {
        if(!(entity instanceof Player player)) return false; // adjust this if support is added for other entities.

        ItemStack jetpack = IJetpackItem.getActiveJetpack(player);
        return ClientTickHandler.isJetpackInUse(player, jetpack);
    }
}
