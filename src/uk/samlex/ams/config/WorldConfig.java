package uk.samlex.ams.config;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import uk.samlex.ams.util.EnumSort;
import static org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

public class WorldConfig {

    private String worldName;

    @SuppressWarnings("deprecation")
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
        ConfigStore config = ConfigStore.instance();

        this.worldName = worldName;

        worldMode = (WorldMode) config.getConfigEnum(worldName, "world mode", worldMode);
        safeTimeStart = config.getConfigInt(worldName, "safe time start", safeTimeStart);
        safeTimeEnd = config.getConfigInt(worldName, "safe time end", safeTimeEnd);
        allBlocks = config.getConfigBoolean(worldName, "all block types", allBlocks);
        allSpawnReasons = config.getConfigBoolean(worldName, "all spawn reasons", allSpawnReasons);
        allCreatures = config.getConfigBoolean(worldName, "all creatures", allCreatures);
        heightLimitMode = (HeightLimitMode) config.getConfigEnum(worldName, "global.height limit mode", heightLimitMode);
        heightLimit = config.getConfigInt(worldName, "global.height limit", heightLimit);

        blockMap = new HashMap<Material, Boolean>();
        entityMap = new HashMap<EntityType, Boolean>();
        spawnReasonMap = new HashMap<SpawnReason, Boolean>();

        setupBlockMap(config);
        setupSpawnReasonMap(config);
        setupEntityMap(config);
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
            if (type.isAlive()) {
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

    public HashMap<Material, Boolean> getBlockMap() {
        return blockMap;
    }

    public HashMap<EntityType, Boolean> getEntityMap() {
        return entityMap;
    }

    public HashMap<SpawnReason, Boolean> getSpawnReasonMap() {
        return spawnReasonMap;
    }

    public HeightLimitMode getHeightLimitMode() {
        return heightLimitMode;
    }

    public int getHeightLimit() {
        return heightLimit;
    }

    public int getSafeTimeStart() {
        return safeTimeStart;
    }

    public int getSafeTimeEnd() {
        return safeTimeEnd;
    }

    public WorldMode getWorldMode() {
        return worldMode;
    }

    public boolean isAllBlocks() {
        return allBlocks;
    }

    public String getWorldName() {
        return worldName;
    }

    public boolean isAllSpawnReasons() {
        return allSpawnReasons;
    }

    public boolean isAllCreatures() {
        return allCreatures;
    }
}
