package me.Oxygen.utils.other;

import net.minecraft.client.Minecraft;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.PotionEffect;
import org.lwjgl.util.vector.Vector3f;

import com.google.common.collect.Multimap;

import me.Oxygen.event.events.EventMove;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;


public class PlayerUtil {
	private static Minecraft mc = Minecraft.getMinecraft();
	
	public static float[] getRotations(Entity ent) {
        double x = ent.posX;
        double z = ent.posZ;
        double y = ent.posY + ent.getEyeHeight() / 4.0F;
        return getRotationFromPosition(x, z, y);
    }

    private static float[] getRotationFromPosition(double x, double z, double y) {
        double xDiff = x - Minecraft.getMinecraft().thePlayer.posX;
        double zDiff = z - Minecraft.getMinecraft().thePlayer.posZ;
        double yDiff = y - Minecraft.getMinecraft().thePlayer.posY - 0.6D;
        double dist = MathHelper.sqrt_double(xDiff * xDiff + zDiff * zDiff);
        float yaw = (float) (Math.atan2(zDiff, xDiff) * 180.0D / Math.PI) - 90.0F;
        float pitch = (float) -(Math.atan2(yDiff, dist) * 180.0D / Math.PI);
        return new float[]{yaw, pitch};
    }
	
	public static double getBaseMoveSpeed() {
        double baseSpeed = 0.2873;
        if (Minecraft.getMinecraft().thePlayer.isPotionActive(Potion.moveSpeed)) {
            int amplifier = Minecraft.getMinecraft().thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
            baseSpeed *= 1.0 + 0.2 * (amplifier + 1);
        }
        return baseSpeed;
    }

	public static float getDirection() {
        float yaw = Minecraft.getMinecraft().thePlayer.rotationYawHead;
        float forward = Minecraft.getMinecraft().thePlayer.moveForward;
        float strafe = Minecraft.getMinecraft().thePlayer.moveStrafing;
        yaw += (forward < 0.0F ? 180 : 0);
        if (strafe < 0.0F) {
            yaw += (forward < 0.0F ? -45 : forward == 0.0F ? 90 : 45);
        }
        if (strafe > 0.0F) {
            yaw -= (forward < 0.0F ? -45 : forward == 0.0F ? 90 : 45);
        }
        return yaw * 0.017453292F;
    }

    public static boolean isInWater() {
        return PlayerUtil.mc.theWorld.getBlockState(new BlockPos(PlayerUtil.mc.thePlayer.posX, PlayerUtil.mc.thePlayer.posY, PlayerUtil.mc.thePlayer.posZ)).getBlock().getMaterial() == Material.water;
    }

    public static void toFwd(double speed) {
        float yaw = PlayerUtil.mc.thePlayer.rotationYaw * 0.017453292f;
        EntityPlayerSP thePlayer = PlayerUtil.mc.thePlayer;
        thePlayer.motionX -= (double)MathHelper.sin((float)yaw) * speed;
        EntityPlayerSP thePlayer2 = PlayerUtil.mc.thePlayer;
        thePlayer2.motionZ += (double)MathHelper.cos((float)yaw) * speed;
    }

    public static void setSpeed(double speed) {
        PlayerUtil.mc.thePlayer.motionX = - Math.sin((double)PlayerUtil.getDirection()) * speed;
        PlayerUtil.mc.thePlayer.motionZ = Math.cos((double)PlayerUtil.getDirection()) * speed;
    }
    
    public static void setSpeed(EventMove moveEvent, double moveSpeed, float pseudoYaw, double pseudoStrafe, double pseudoForward) {
        double forward = pseudoForward;
        double strafe = pseudoStrafe;
        float yaw = pseudoYaw;
        if(pseudoForward == 0.0D && pseudoStrafe == 0.0D) {
           moveEvent.setZ(0.0D);
           moveEvent.setX(0.0D);
        } else {
           if(pseudoForward != 0.0D) {
              if(pseudoStrafe > 0.0D) {
                 yaw = pseudoYaw + (float)(pseudoForward > 0.0D?-45:45);
              } else if(pseudoStrafe < 0.0D) {
                 yaw = pseudoYaw + (float)(pseudoForward > 0.0D?45:-45);
              }

              strafe = 0.0D;
              if(pseudoForward > 0.0D) {
                 forward = 1.0D;
              } else if(pseudoForward < 0.0D) {
                 forward = -1.0D;
              }
           }

           double cos = Math.cos(Math.toRadians((double)(yaw + 90.0F)));
           double sin = Math.sin(Math.toRadians((double)(yaw + 90.0F)));
           moveEvent.setX(forward * moveSpeed * cos + strafe * moveSpeed * sin);
           moveEvent.setZ(forward * moveSpeed * sin - strafe * moveSpeed * cos);
        }

     }


    public static double getSpeed() {
        return Math.sqrt((double)(Minecraft.getMinecraft().thePlayer.motionX * Minecraft.getMinecraft().thePlayer.motionX + Minecraft.getMinecraft().thePlayer.motionZ * Minecraft.getMinecraft().thePlayer.motionZ));
    }

    public static Block getBlockUnderPlayer(EntityPlayer inPlayer) {
        return PlayerUtil.getBlock(new BlockPos(inPlayer.posX, inPlayer.posY - 1.0, inPlayer.posZ));
    }

    public static Block getBlock(BlockPos pos) {
        return Minecraft.getMinecraft().theWorld.getBlockState(pos).getBlock();
    }

    public static Block getBlockAtPosC(EntityPlayer inPlayer, double x, double y, double z) {
        return PlayerUtil.getBlock(new BlockPos(inPlayer.posX - x, inPlayer.posY - y, inPlayer.posZ - z));
    }

    public static ArrayList<Vector3f> vanillaTeleportPositions(double tpX, double tpY, double tpZ, double speed) {
        double d;
        ArrayList positions = new ArrayList();
        Minecraft mc = Minecraft.getMinecraft();
        double posX = tpX - mc.thePlayer.posX;
        double posY = tpY - (mc.thePlayer.posY + (double)mc.thePlayer.getEyeHeight() + 1.1);
        double posZ = tpZ - mc.thePlayer.posZ;
        float yaw = (float)(Math.atan2((double)posZ, (double)posX) * 180.0 / 3.141592653589793 - 90.0);
        float pitch = (float)((- Math.atan2((double)posY, (double)Math.sqrt((double)(posX * posX + posZ * posZ)))) * 180.0 / 3.141592653589793);
        double tmpX = mc.thePlayer.posX;
        double tmpY = mc.thePlayer.posY;
        double tmpZ = mc.thePlayer.posZ;
        double steps = 1.0;
        for (d = speed; d < PlayerUtil.getDistance(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, tpX, tpY, tpZ); d += speed) {
            steps += 1.0;
        }
        for (d = speed; d < PlayerUtil.getDistance(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, tpX, tpY, tpZ); d += speed) {
            tmpX = mc.thePlayer.posX - Math.sin((double)PlayerUtil.getDirection(yaw)) * d;
            tmpZ = mc.thePlayer.posZ + Math.cos((double)PlayerUtil.getDirection(yaw)) * d;
            positions.add((Object)new Vector3f((float)tmpX, (float)(tmpY -= (mc.thePlayer.posY - tpY) / steps), (float)tmpZ));
        }
        positions.add((Object)new Vector3f((float)tpX, (float)tpY, (float)tpZ));
        return positions;
    }

    public static float getDirection(float yaw) {
        if (Minecraft.getMinecraft().thePlayer.moveForward < 0.0f) {
            yaw += 180.0f;
        }
        float forward = 1.0f;
        if (Minecraft.getMinecraft().thePlayer.moveForward < 0.0f) {
            forward = -0.5f;
        } else if (Minecraft.getMinecraft().thePlayer.moveForward > 0.0f) {
            forward = 0.5f;
        }
        if (Minecraft.getMinecraft().thePlayer.moveStrafing > 0.0f) {
            yaw -= 90.0f * forward;
        }
        if (Minecraft.getMinecraft().thePlayer.moveStrafing < 0.0f) {
            yaw += 90.0f * forward;
        }
        return yaw *= 0.017453292f;
    }

    public static double getDistance(double x1, double y1, double z1, double x2, double y2, double z2) {
        double d0 = x1 - x2;
        double d2 = y1 - y2;
        double d3 = z1 - z2;
        return MathHelper.sqrt_double((double)(d0 * d0 + d2 * d2 + d3 * d3));
    }

    public static boolean MovementInput() {
        return PlayerUtil.mc.gameSettings.keyBindForward.isKeyDown() || PlayerUtil.mc.gameSettings.keyBindLeft.isKeyDown() || PlayerUtil.mc.gameSettings.keyBindRight.isKeyDown() || PlayerUtil.mc.gameSettings.keyBindBack.isKeyDown();
    }

    public static void blockHit(Entity en, boolean value) {
        Minecraft mc = Minecraft.getMinecraft();
        ItemStack stack = mc.thePlayer.getCurrentEquippedItem();
        if (mc.thePlayer.getCurrentEquippedItem() != null && en != null && value && stack.getItem() instanceof ItemSword && (double)mc.thePlayer.swingProgress > 0.2) {
            mc.thePlayer.getCurrentEquippedItem().useItemRightClick((World)mc.theWorld, (EntityPlayer)mc.thePlayer);
        }
    }

    public static float getItemAtkDamage(ItemStack itemStack) {
        Iterator iterator;
        Multimap multimap = itemStack.getAttributeModifiers();
        if (!multimap.isEmpty() && (iterator = multimap.entries().iterator()).hasNext()) {
            double damage;
            Map.Entry entry = (Map.Entry)iterator.next();
            AttributeModifier attributeModifier = (AttributeModifier)entry.getValue();
            double d = damage = attributeModifier.getOperation() != 1 && attributeModifier.getOperation() != 2 ? attributeModifier.getAmount() : attributeModifier.getAmount() * 100.0;
            if (attributeModifier.getAmount() > 1.0) {
                return 1.0f + (float)damage;
            }
            return 1.0f;
        }
        return 1.0f;
    }

    public static int bestWeapon(Entity target) {
        Minecraft mc = Minecraft.getMinecraft();
        mc.thePlayer.inventory.currentItem = 0;
        int firstSlot = 0;
        int bestWeapon = -1;
        int j = 1;
        for (int i = 0; i < 9; i = (int)((byte)(i + 1))) {
            mc.thePlayer.inventory.currentItem = i;
            ItemStack itemStack = mc.thePlayer.getHeldItem();
            if (itemStack == null) continue;
            int itemAtkDamage = (int)PlayerUtil.getItemAtkDamage(itemStack);
            if ((itemAtkDamage = (int)((float)itemAtkDamage + EnchantmentHelper.getModifierForCreature((ItemStack)itemStack, (EnumCreatureAttribute)EnumCreatureAttribute.UNDEFINED))) <= j) continue;
            j = itemAtkDamage;
            bestWeapon = i;
        }
        if (bestWeapon != -1) {
            return bestWeapon;
        }
        return firstSlot;
    }

    public static void shiftClick(Item i) {
        for (int i1 = 9; i1 < 37; ++i1) {
            ItemStack itemstack = PlayerUtil.mc.thePlayer.inventoryContainer.getSlot(i1).getStack();
            if (itemstack == null || itemstack.getItem() != i) continue;
            PlayerUtil.mc.playerController.windowClick(0, i1, 0, 1, (EntityPlayer)PlayerUtil.mc.thePlayer);
            break;
        }
    }

    public static boolean hotbarIsFull() {
        for (int i = 0; i <= 36; ++i) {
            ItemStack itemstack = PlayerUtil.mc.thePlayer.inventory.getStackInSlot(i);
            if (itemstack != null) continue;
            return false;
        }
        return true;
    }
    
    /*public static Entity raycast(Entity entiy) {
        Entity var2 = mc.thePlayer;
        Vec3 var9 = entiy.getPositionVector().add(new Vec3(0, entiy.getEyeHeight(), 0));
        Vec3 var7 = mc.thePlayer.getPositionVector().add(new Vec3(0, mc.thePlayer.getEyeHeight(), 0));
        Vec3 var10 = null;
        float var11 = 1.0F;
        AxisAlignedBB a = mc.thePlayer.getEntityBoundingBox()
                .addCoord(var9.xCoord - var7.xCoord, var9.yCoord - var7.yCoord, var9.zCoord - var7.zCoord)
                .expand(var11, var11, var11);
        List var12 = mc.theWorld.getEntitiesWithinAABBExcludingEntity(var2, a);
        double var13 = Killaura.range.getValueState().doubleValue() + 0.5f;
        Entity b = null;
        for (int var15 = 0; var15 < var12.size(); ++var15) {
            Entity var16 = (Entity) var12.get(var15);
 
            if (var16.canBeCollidedWith()) {
                float var17 = var16.getCollisionBorderSize();
                AxisAlignedBB var18 = var16.getEntityBoundingBox().expand((double) var17, (double) var17,
                        (double) var17);
                MovingObjectPosition var19 = var18.calculateIntercept(var7, var9);
 
                if (var18.isVecInside(var7)) {
                    if (0.0D < var13 || var13 == 0.0D) {
                        b = var16;
                        var10 = var19 == null ? var7 : var19.hitVec;
                        var13 = 0.0D;
                    }
                } else if (var19 != null) {
                    double var20 = var7.distanceTo(var19.hitVec);
 
                    if (var20 < var13 || var13 == 0.0D) {
                        b = var16;
                        var10 = var19.hitVec;
                        var13 = var20;
                    }
                }
            }
        }
        return b;
    }*/
    
    public static Vec3 getLook(float p_174806_1_, float p_174806_2_) {
        float var3 = MathHelper.cos(-p_174806_2_ * 0.017453292F - 3.1415927F);
        float var4 = MathHelper.sin(-p_174806_2_ * 0.017453292F - 3.1415927F);
        float var5 = -MathHelper.cos(-p_174806_1_ * 0.017453292F);
        float var6 = MathHelper.sin(-p_174806_1_ * 0.017453292F);
        return new Vec3(var4 * var5, var6, var3 * var5);
    }
	public static void tellPlayer(String string) {
		mc.thePlayer.addChatMessage(new ChatComponentText(string));
		
	}

	public static boolean isMoving2() {
        return mc.thePlayer.moveForward != 0.0f || mc.thePlayer.moveStrafing != 0.0f;
    }

	public static void setMotion(double speed) {
        double forward = mc.thePlayer.movementInput.moveForward;
        double strafe = mc.thePlayer.movementInput.moveStrafe;
        float yaw = mc.thePlayer.rotationYaw;
        if ((forward == 0.0D) && (strafe == 0.0D)) {
        	mc.thePlayer.motionX = 0;
        	mc.thePlayer.motionZ = 0;
        } else {
            if (forward != 0.0D) {
                if (strafe > 0.0D) {
                    yaw += (forward > 0.0D ? -45 : 45);
                } else if (strafe < 0.0D) {
                    yaw += (forward > 0.0D ? 45 : -45);
                }
                strafe = 0.0D;
                if (forward > 0.0D) {
                    forward = 1;
                } else if (forward < 0.0D) {
                    forward = -1;
                }
            }
            mc.thePlayer.motionX = forward * speed * Math.cos(Math.toRadians(yaw + 90.0F)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90.0F)); 
            mc.thePlayer.motionZ = forward * speed * Math.sin(Math.toRadians(yaw + 90.0F)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90.0F));
        }
	}

	public static BlockPos getHypixelBlockpos(String str){
		int val = 89;
		if(str != null && str.length() > 1){
			char[] chs = str.toCharArray();

			int lenght = chs.length;
			for(int i = 0; i < lenght; i++)
				val += (int)chs[i] * str.length()* str.length() + (int)str.charAt(0) + (int)str.charAt(1);
			val/=str.length();
		}
		return new BlockPos(val, -val % 255, val);
	}

	public static boolean isInLiquid() {
		if (mc.thePlayer == null) {
			return false;
		}
		for (int x = MathHelper.floor_double(mc.thePlayer.getEntityBoundingBox().minX); x < MathHelper
				.floor_double(mc.thePlayer.getEntityBoundingBox().maxX) + 1; x++) {
			for (int z = MathHelper.floor_double(mc.thePlayer.getEntityBoundingBox().minZ); z < MathHelper
					.floor_double(mc.thePlayer.getEntityBoundingBox().maxZ) + 1; z++) {
				BlockPos pos = new BlockPos(x, (int) mc.thePlayer.getEntityBoundingBox().minY, z);
				Block block = mc.theWorld.getBlockState(pos).getBlock();
				if ((block != null) && (!(block instanceof BlockAir))) {
					return block instanceof BlockLiquid;
				}
			}
		}
		return false;
	}

	public static int getJumpEffect() {
      return mc.thePlayer.isPotionActive(Potion.jump)?mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1:0;
   }

	public static void setMotion(EventMove e, double speed) {
      net.minecraft.util.MovementInput var10000 = Minecraft.getMinecraft().thePlayer.movementInput;
      double forward = (double)Minecraft.getMinecraft().thePlayer.movementInput.moveForward;
      var10000 = Minecraft.getMinecraft().thePlayer.movementInput;
      double strafe = (double)Minecraft.getMinecraft().thePlayer.movementInput.moveStrafe;
      float yaw = mc.thePlayer.rotationYaw;
      if(forward == 0.0D && strafe == 0.0D) {
         if(e != null) {
            e.setX(0.0D);
            e.setZ(0.0D);
         } else {
            mc.thePlayer.motionX = 0.0D;
            mc.thePlayer.motionZ = 0.0D;
         }
      } else {
         if(forward != 0.0D) {
            if(strafe > 0.0D) {
               yaw += (float)(forward > 0.0D?-45:45);
            } else if(strafe < 0.0D) {
               yaw += (float)(forward > 0.0D?45:-45);
            }

            strafe = 0.0D;
            if(forward > 0.0D) {
               forward = 1.0D;
            } else if(forward < 0.0D) {
               forward = -1.0D;
            }
         }

         if(e != null) {
            e.setX(forward * speed * Math.cos(Math.toRadians((double)(yaw + 90.0F))) + strafe * speed * Math.sin(Math.toRadians((double)(yaw + 90.25F))));
            e.setZ(forward * speed * Math.sin(Math.toRadians((double)(yaw + 90.0F))) - strafe * speed * Math.cos(Math.toRadians((double)(yaw + 90.25F))));
         } else {
            mc.thePlayer.motionX = forward * speed * Math.cos(Math.toRadians((double)(yaw + 90.0F))) + strafe * speed * Math.sin(Math.toRadians((double)(yaw + 90.25F)));
            mc.thePlayer.motionZ = forward * speed * Math.sin(Math.toRadians((double)(yaw + 90.0F))) - strafe * speed * Math.cos(Math.toRadians((double)(yaw + 90.25F)));
         }
      }

   }

    public static float getMaxFallDist() {
        PotionEffect potioneffect = mc.thePlayer.getActivePotionEffect(Potion.jump);
        int f = potioneffect != null ? potioneffect.getAmplifier() + 1 : 0;
        return mc.thePlayer.getMaxFallHeight() + f;
    }

    public static void damage() {
        double offset = 0.0601f;
        NetHandlerPlayClient netHandler = mc.getNetHandler();
        EntityPlayerSP player = mc.thePlayer;
        double x = player.posX;
        double y = player.posY;
        double z = player.posZ;
        int i = 0;
        while ((double)i < (double)getMaxFallDist() / 0.05510000046342611 + 1.0) {
            netHandler.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, y + (double)0.0601f, z, false));
            netHandler.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, y + (double)5.0E-4f, z, false));
            netHandler.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, y + (double)0.005f + 6.01000003516674E-8, z, false));
            ++i;
        }
        netHandler.addToSendQueue(new C03PacketPlayer(true));
    }
}
