package uk.samlex.ams.util;

import org.bukkit.util.Vector;

public class BoundingBox {

    private Vector minimum, maximum;

    public BoundingBox(Vector pointOne, Vector pointTwo) {
        this.minimum = Vector.getMinimum(pointOne, pointTwo);
        this.maximum = Vector.getMaximum(pointOne, pointOne);
    }

    public boolean withinBoundingBox(Vector point) {
        if(point.getBlockX() >= minimum.getBlockX() && point.getBlockX() <=maximum.getBlockX()) {
            if(point.getBlockY() >= minimum.getBlockY() && point.getBlockY() <=maximum.getBlockY()) {
                if(point.getBlockZ() >= minimum.getBlockZ() && point.getBlockZ() <=maximum.getBlockZ()) {
                    return true;
                }
            }
        }
        return false;
    }
}
