package me.squeeglii.mod.mekatweaks.mixin;

import me.squeeglii.mod.mekatweaks.IJetpackUser;
import me.squeeglii.mod.mekatweaks.MekanismTweaks;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeConfigSpec;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class PlayerSpeedModifierMixin implements IJetpackUser {

    @Inject(method = "aiStep()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;travel(Lnet/minecraft/world/phys/Vec3;)V", shift = At.Shift.AFTER))
    private void applyJetpackSpeed(CallbackInfo ci) {
        LivingEntity thiss = (LivingEntity) (Object) this;

        if((!thiss.isEffectiveAi()) && (!thiss.isControlledByLocalInstance())) return;

        if(thiss.isFallFlying()) return;
        if(thiss.isAffectedByFluids() && (thiss.isInLava() || thiss.isInWater())) return; // this has high potential to break.



        if(!this.mekanismTweaks$checkJetpackUseOnLogicalSide(thiss))
            return;

        ForgeConfigSpec.DoubleValue forwardMultProp = MekanismTweaks.CONFIG.getValues().get("jetpack.forward_speed_boost");
        ForgeConfigSpec.DoubleValue strafeMultProp = MekanismTweaks.CONFIG.getValues().get("jetpack.strafe_speed_boost");
        double forwardMult = forwardMultProp.get() / 20;
        double strafeMult = strafeMultProp.get() / 20;

        double rotation = Math.toRadians(thiss.getYRot());

        double x = thiss.xxa * strafeMult;
        double z = thiss.zza * forwardMult;

        double newX = (x * Math.cos(rotation)) - (-z * Math.sin(rotation));
        double newZ = (x * Math.sin(rotation)) + (z * Math.cos(rotation));

        if(thiss instanceof Player)
            MekanismTweaks.LOGGER.info("x: %s, z: %s".formatted(newX, newZ));

        Vec3 oldMove = thiss.getDeltaMovement();
        thiss.setDeltaMovement(oldMove.add(newX, 0, newZ));
    }
}
