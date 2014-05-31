package uk.samlex.ams.config;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.bukkit.util.Vector;

import uk.samlex.ams.util.BoundingBox;

import com.avaje.ebean.validation.NotNull;

@Entity()
@Table(name = "antimobspawn_world_zone")
public class WorldZone {

    public static BoundingBox getBoundingBox(WorldZone worldZone) {
        return new BoundingBox(getPointOneVector(worldZone), getPointTwoVector(worldZone));
    }

    public static Vector getPointOneVector(WorldZone worldZone) {
        return new Vector(worldZone.getPointOneVectorX(), worldZone.getPointOneVectorY(), worldZone.getPointOneVectorZ());
    }

    public static Vector getPointTwoVector(WorldZone worldZone) {
        return new Vector(worldZone.getPointTwoVectorX(), worldZone.getPointTwoVectorY(), worldZone.getPointTwoVectorZ());
    }

    public static void setPointOneVector(WorldZone worldZone, Vector pointOneVector) {
        worldZone.setPointOneVectorX(pointOneVector.getBlockX());
        worldZone.setPointOneVectorY(pointOneVector.getBlockY());
        worldZone.setPointOneVectorZ(pointOneVector.getBlockZ());
    }

    public static void setPointTwoVector(WorldZone worldZone, Vector pointTwoVector) {
        worldZone.setPointTwoVectorX(pointTwoVector.getBlockX());
        worldZone.setPointTwoVectorY(pointTwoVector.getBlockY());
        worldZone.setPointTwoVectorZ(pointTwoVector.getBlockZ());
    }

    @Id
    private int id;

    @NotNull
    private String worldName;

    @NotNull
    private String zoneName;

    @NotNull
    private int pointOneVectorX;

    @NotNull
    private int pointOneVectorY;

    @NotNull
    private int pointOneVectorZ;

    @NotNull
    private int pointTwoVectorX;

    @NotNull
    private int pointTwoVectorY;

    @NotNull
    private int pointTwoVectorZ;

    public int getId() {
        return id;
    }

    public int getPointOneVectorX() {
        return pointOneVectorX;
    }

    public int getPointOneVectorY() {
        return pointOneVectorY;
    }

    public int getPointOneVectorZ() {
        return pointOneVectorZ;
    }

    public int getPointTwoVectorX() {
        return pointTwoVectorX;
    }

    public int getPointTwoVectorY() {
        return pointTwoVectorY;
    }

    public int getPointTwoVectorZ() {
        return pointTwoVectorZ;
    }

    public String getWorldName() {
        return worldName;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPointOneVectorX(int pointOneVectorX) {
        this.pointOneVectorX = pointOneVectorX;
    }

    public void setPointOneVectorY(int pointOneVectorY) {
        this.pointOneVectorY = pointOneVectorY;
    }

    public void setPointOneVectorZ(int pointOneVectorZ) {
        this.pointOneVectorZ = pointOneVectorZ;
    }

    public void setPointTwoVectorX(int pointTwoVectorX) {
        this.pointTwoVectorX = pointTwoVectorX;
    }

    public void setPointTwoVectorY(int pointTwoVectorY) {
        this.pointTwoVectorY = pointTwoVectorY;
    }

    public void setPointTwoVectorZ(int pointTwoVectorZ) {
        this.pointTwoVectorZ = pointTwoVectorZ;
    }

    public void setWorldName(String worldName) {
        this.worldName = worldName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }
}
