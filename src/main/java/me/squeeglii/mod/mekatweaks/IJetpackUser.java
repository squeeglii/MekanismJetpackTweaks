package me.squeeglii.mod.mekatweaks;

import mekanism.common.Mekanism;
import mekanism.common.base.KeySync;
import mekanism.common.item.interfaces.IJetpackItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface IJetpackUser {

    // Yes, this is hacky.
    // No, I can't think of an easier way to fix this without an over-engineered system.

    // just pass the same player it's being called on.

    default boolean mekanismTweaks$checkJetpackUseOnLogicalSide(LivingEntity entity) {
        if(!(entity instanceof Player player)) return false; // adjust this if support is added for other entities.

        if(IJetpackItem.getActiveJetpack(player).isEmpty())
            return false;

        ItemStack primaryJetpack = IJetpackItem.getPrimaryJetpack(player);

        if(primaryJetpack.isEmpty())
            return false;

        IJetpackItem.JetpackMode primaryMode = ((IJetpackItem) primaryJetpack.getItem()).getJetpackMode(primaryJetpack);
        IJetpackItem.JetpackMode mode = IJetpackItem.getPlayerJetpackMode(player, primaryMode, () -> Mekanism.keyMap.has(player.getUUID(), KeySync.ASCEND));

        return mode != IJetpackItem.JetpackMode.DISABLED;
    }

}
