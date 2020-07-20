package me.Oxygen.utils.other;

import java.util.List;

import com.google.common.base.Predicates;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

public final class RaycastUtils {

	public static Entity raycastEntity(final double range, final float yaw, final float pitch, Entity ent) {
    	Minecraft mc = Minecraft.getMinecraft();
        final Entity renderViewEntity = mc.getRenderViewEntity();

        if(renderViewEntity != null && mc.theWorld != null) {
            double blockReachDistance = range;
            final Vec3 eyePosition = renderViewEntity.getPositionEyes(1F);

            final float yawCos = MathHelper.cos(-yaw * 0.017453292F - (float) Math.PI);
            final float yawSin = MathHelper.sin(-yaw * 0.017453292F - (float) Math.PI);
            final float pitchCos = -MathHelper.cos(-pitch * 0.017453292F);
            final float pitchSin = MathHelper.sin(-pitch * 0.017453292F);

            final Vec3 entityLook = new Vec3(yawSin * pitchCos, pitchSin, yawCos * pitchCos);
            final Vec3 vector = eyePosition.addVector(entityLook.xCoord * blockReachDistance, entityLook.yCoord * blockReachDistance, entityLook.zCoord * blockReachDistance);
            final List<Entity> entityList = mc.theWorld.getEntitiesInAABBexcluding(renderViewEntity, renderViewEntity.getEntityBoundingBox().addCoord(entityLook.xCoord * blockReachDistance, entityLook.yCoord * blockReachDistance, entityLook.zCoord * blockReachDistance).expand(1D, 1D, 1D), Predicates.and(EntitySelectors.NOT_SPECTATING, Entity :: canBeCollidedWith));

            Entity pointedEntity = null;

            for (final Entity entity : entityList) {
            	if (entity == ent) {
            		final float collisionBorderSize = entity.getCollisionBorderSize();
                    final AxisAlignedBB axisAlignedBB = entity.getEntityBoundingBox().expand(collisionBorderSize, collisionBorderSize, collisionBorderSize);
                    final MovingObjectPosition movingObjectPosition = axisAlignedBB.calculateIntercept(eyePosition, vector);

                    if(axisAlignedBB.isVecInside(eyePosition)) {
                        if(blockReachDistance >= 0.0D) {
                            pointedEntity = entity;
                            blockReachDistance = 0.0D;
                        }
                    }else if(movingObjectPosition != null) {
                        final double eyeDistance = eyePosition.distanceTo(movingObjectPosition.hitVec);

                        if(eyeDistance < blockReachDistance || blockReachDistance == 0.0D) {
                            if(entity == renderViewEntity.ridingEntity && !renderViewEntity.canRiderInteract()) {
                                if(blockReachDistance == 0.0D) {
                                	pointedEntity = entity;
                                }                               
                            }else{
                                pointedEntity = entity;
                                blockReachDistance = eyeDistance;
                            }
                        }
                    }
            	}
            }

            return pointedEntity;
        }
        
        return null;
    }
}