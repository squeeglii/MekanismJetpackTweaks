package me.squeeglii.mod.mekatweaks.mixin;

import me.squeeglii.mod.mekatweaks.MekanismTweaks;
import mekanism.common.CommonPlayerTickHandler;
import mekanism.common.item.interfaces.IJetpackItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeConfigSpec;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.function.BooleanSupplier;

@Mixin(IJetpackItem.class)
public interface JetpackVerticalityModifierMixin {

    /**
     * @author Squeeglii
     * @reason Mixin doesn't like injecting into static interface methods, plus an injection would have been very messy for this.
     */
    @Overwrite(remap = false)
    static boolean handleJetpackMotion(Player player, IJetpackItem.JetpackMode mode, BooleanSupplier ascendingSupplier) {
        Vec3 motion = player.getDeltaMovement();

        // Overwrite is here:
        // - any reference to "speed" was previously "0.15D"
        // - any reference to "maxNormal" was "0.5D"
        // - any reference to "maxHover" was "0.2D"
        ForgeConfigSpec.DoubleValue verticalSpeed = MekanismTweaks.CONFIG.getValues().get("jetpack.vertical_speed");
        ForgeConfigSpec.DoubleValue verticalSpeedNormalMax = MekanismTweaks.CONFIG.getValues().get("jetpack.max_vertical_speed_normal");
        ForgeConfigSpec.DoubleValue verticalSpeedHoverMax = MekanismTweaks.CONFIG.getValues().get("jetpack.max_vertical_speed_normal");
        double speed = verticalSpeed.get();
        double maxNormal = verticalSpeedNormalMax.get();
        double maxHover = verticalSpeedHoverMax.get();

        if (mode == IJetpackItem.JetpackMode.NORMAL) {

            if (player.isFallFlying()) {
                Vec3 forward = player.getLookAngle();
                Vec3 delta = forward.multiply(forward.scale(0.15))
                                    .add(
                                            forward.scale(1.5)
                                                   .subtract(motion)
                                                   .scale(0.5)
                                    );
                player.setDeltaMovement(motion.add(delta));
                return false;

            } else {
                player.setDeltaMovement(motion.x(), Math.min(motion.y() + speed, maxNormal), motion.z());
            }

        } else if (mode == IJetpackItem.JetpackMode.HOVER) {
            boolean ascending = ascendingSupplier.getAsBoolean();
            boolean descending = player.isDescending();
            if (ascending == descending) {
                if (motion.y() > 0) {
                    player.setDeltaMovement(motion.x(), Math.max(motion.y() - speed, 0), motion.z());
                } else if (motion.y() < 0) {
                    if (!CommonPlayerTickHandler.isOnGroundOrSleeping(player)) {
                        player.setDeltaMovement(motion.x(), Math.min(motion.y() + speed, 0), motion.z());
                    }
                }
            } else if (ascending) {
                player.setDeltaMovement(motion.x(), Math.min(motion.y() + speed, maxHover), motion.z());
            } else if (!CommonPlayerTickHandler.isOnGroundOrSleeping(player)) {
                player.setDeltaMovement(motion.x(), Math.max(motion.y() - speed, -maxHover), motion.z());
            }
        }
        return true;
    }

}
