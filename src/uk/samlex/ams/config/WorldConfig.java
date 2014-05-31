package uk.samlex.ams.config;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import uk.samlex.ams.util.EnumSort;

@SuppressWarnings("deprecation")
public class WorldConfig {

    private String worldName;

    private static final EntityType excludedEntityType = EntityType.PLAYER;
    private static final SpawnReason excludedSpawnReason = SpawnReason.BED;

    private boolean allBlocks = false;
    private boolean allSpawnReasons = false;
    private boolean allCreatures = false;
    private HashMap<Material, Boolean> blockMap;
    private HashMap<EntityType, Boolean> entityMap;
    private HashMap<SpawnReason, Boolean> spawnReasonMap;
    private HeightLimitMode heightLimitMode = HeightLimitMode.BELOW;
    private int heightLimit = 0;
    private int safeTimeStart = 0;
    private int safeTimeEnd = 24000;
    private WorldMode worldMode = WorldMode.NONE;

    public WorldConfig(String worldName) {
        this.worldName = worldName;
        this.blockMap = new HashMap<Material, Boolean>();
        this.entityMap = new HashMap<EntityType, Boolean>();
        this.spawnReasonMap = new HashMap<SpawnReason, Boolean>();

        ConfigStore config = ConfigStore.instance();

        worldMode = (WorldMode) config.getConfigEnum(worldName, "world mode", worldMode);
        safeTimeStart = config.getConfigInt(worldName, "safe time start", safeTimeStart);
        safeTimeEnd = config.getConfigInt(worldName, "safe time end", safeTimeEnd);

        heightLimitMode = (HeightLimitMode) config.getConfigEnum(worldName, "global.height limit mode", heightLimitMode);
        heightLimit = config.getConfigInt(worldName, "global.height limit", heightLimit);

        allBlocks = config.getConfigBoolean(worldName, "all block types", allBlocks);
        setupBlockMap(config);

        allSpawnReasons = config.getConfigBoolean(worldName, "all spawn reasons", allSpawnReasons);
        setupSpawnReasonMap(config);

        allCreatures = config.getConfigBoolean(worldName, "all creatures", allCreatures);
        setupEntityMap(config);
    }

    public HashMap<Material, Boolean> getBlockMap() {
        return blockMap;
    }

    public HashMap<EntityType, Boolean> getEntityMap() {
        return entityMap;
    }

    public int getHeightLimit() {
        return heightLimit;
    }

    public HeightLimitMode getHeightLimitMode() {
        return heightLimitMode;
    }

    public int getSafeTimeEnd() {
        return safeTimeEnd;
    }

    public int getSafeTimeStart() {
        return safeTimeStart;
    }

    public HashMap<SpawnReason, Boolean> getSpawnReasonMap() {
        return spawnReasonMap;
    }

    public WorldMode getWorldMode() {
        return worldMode;
    }

    public String getWorldName() {
        return worldName;
    }

    public boolean isAllBlocks() {
        return allBlocks;
    }

    public boolean isAllCreatures() {
        return allCreatures;
    }

    public boolean isAllSpawnReasons() {
        return allSpawnReasons;
    }

    private void setupBlockMap(ConfigStore config) {
        for (Material mat : EnumSort.sortEnumArrayAlphabetically(Material.values(), Material.class)) {
            if (mat.isBlock()) {
                blockMap.put(mat, config.getConfigBoolean(worldName, "block types" + "." + mat.toString().toLowerCase(), false));
            }
        }
    }

    private void setupEntityMap(ConfigStore config) {
        for (EntityType type : EnumSort.sortEnumArrayAlphabetically(EntityType.values(), EntityType.class)) {
            if (type.isAlive() && type != excludedEntityType) {
                entityMap.put(type, config.getConfigBoolean(worldName, "creature" + "." + type.toString().toLowerCase(), false));
            }
        }
    }

    private void setupSpawnReasonMap(ConfigStore config) {
        for (SpawnReason reason : EnumSort.sortEnumArrayAlphabetically(SpawnReason.values(), SpawnReason.class)) {
            if (reason == excludedSpawnReason) {
                continue;
            }
            spawnReasonMap.put(reason, config.getConfigBoolean(worldName, "spawn reason" + "." + reason.toString().toLowerCase(), false));
        }
    }
}
