package cn.rainbow.oxygen.event.events;

import cn.rainbow.oxygen.event.Event;

public class MotionEvent extends Event {
	
	private final MotionType type;
    private float yaw;
    private float pitch;
    private boolean onGround;
    private double x, y, z;
    private boolean modified;

    public MotionEvent(MotionType eventType, float yaw, float pitch, boolean onGround, double x, double y, double z) {
    	this.type = eventType;
        this.yaw = yaw;
        this.pitch = pitch;
        this.onGround = onGround;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float getYaw() {
        return this.yaw;
    }

    public void setYaw(float yaw) {
        this.modified = true;
        this.yaw = yaw;
    }

    public float getPitch() {
        return this.pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public boolean isGround() {
        return this.onGround;
    }

    public void setGround(boolean ground) {
        this.onGround= ground;
    }

    public double getX() {
        return this.x;
    }
    
    public double getY() {
        return this.y;
    }
    
    public double getZ() {
        return this.z;
    }

    public void setX(double x) {
        this.x = x;
    }
    
    public void setY(double y) {
        this.y = y;
    }
    
    public void setZ(double z) {
        this.z = z;
    }
    
    public MotionType getMotionType() {
		return type;
	}

    public boolean isModified() {
        return modified;
    }

    public enum MotionType {
        PRE, 
        POST;
	}
    
}
