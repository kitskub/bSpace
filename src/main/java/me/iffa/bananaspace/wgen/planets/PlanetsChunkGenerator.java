// Package Declaration
package me.iffa.bananaspace.wgen.planets;

// Java Imports
import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

// BananaSpace Imports
import me.iffa.bananaspace.BananaSpace;
import me.iffa.bananaspace.wgen.populators.SpaceStonePopulator;
import me.iffa.bananaspace.wgen.populators.SpaceGlowstonePopulator;

// Bukkit Imports
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.config.Configuration;

/**
 * Generates a Planetoids world.
 * 
 * Planetoids are generated in "systems" that are (by default) 100x100 chunks
 * (1600x1600 blocks) in size.
 * 
 * @author Canis85
 * @author iffa
 */
public class PlanetsChunkGenerator extends ChunkGenerator {
    // Variables
    private Map<Material, Float> allowedShells;
    private Map<Material, Float> allowedCores;
    private Map<Point, List<Planetoid>> cache;
    private Configuration planetConfig;
    private static final int SYSTEM_SIZE = 100;
    private long seed; // Seed for generating planetoids
    private int density; // Number of planetoids it will try to create per
    private int minSize; // Minimum radius
    private int maxSize; // Maximum radius
    private int minDistance; // Minimum distance between planets, in blocks
    private int floorHeight; // Floor height
    private Material floorBlock; // BlockID for the floor
    private int maxShellSize; // Maximum shell thickness
    private int minShellSize; // Minimum shell thickness, should be at least 3
    private Plugin plugin; // ref to plugin

    /**
     * Loads allowed blocks
     */
    private void loadAllowedBlocks() {
        allowedCores = new EnumMap<Material, Float>(Material.class);
        allowedShells = new EnumMap<Material, Float>(Material.class);
        for (String s : planetConfig.getStringList(
                "blocks.cores", null)) {
            String[] sSplit = s.split("-");
            Material newMat = Material.matchMaterial(sSplit[0]);
            if (newMat.isBlock()) {
                if (sSplit.length == 2) {
                    allowedCores.put(newMat, Float.valueOf(sSplit[1]));
                } else {
                    allowedCores.put(newMat, 1.0f);
                }
            }
        }
        for (String s : planetConfig.getStringList(
                "blocks.shells", null)) {
            String[] sSplit = s.split("-");
            Material newMat = Material.matchMaterial(sSplit[0]);
            if (newMat.isBlock()) {
                if (sSplit.length == 2) {
                    allowedShells.put(newMat, Float.valueOf(sSplit[1]));
                } else {
                    allowedShells.put(newMat, 1.0f);
                }
            }
        }
    }

    /**
     * Constructor for PlanetsChunkGenerator.
     * 
     * @param planetConfig Configuration
     * @param plugin Plugin
     */
    public PlanetsChunkGenerator(Configuration planetConfig, Plugin plugin) {
        this.plugin = plugin;
        this.planetConfig = planetConfig;
        this.seed = (long) planetConfig.getDouble("seed", 0.0);
        this.density = planetConfig.getInt("density", 15000);
        minSize = planetConfig.getInt("minSize", 4);
        maxSize = planetConfig.getInt("maxSize", 20);
        minDistance = planetConfig.getInt("minDistance", 10);
        floorBlock = Material.matchMaterial(planetConfig.getString(
                "floorBlock", "STATIONARY_WATER"));
        this.floorHeight = planetConfig.getInt("floorHeight",
                0);
        minShellSize = planetConfig.getInt("minShellSize", 3);
        maxShellSize = planetConfig.getInt("maxShellSize", 5);

        loadAllowedBlocks();

        cache = new HashMap<Point, List<Planetoid>>();
    }

    /**
     * Generates planets.
     * 
     * @param world World
     * @param random Random
     * @param x X-coord
     * @param z Z-coord
     * 
     * @return byte[] Byte
     */
    @SuppressWarnings("unchecked")
    @Override
    public byte[] generate(World world, Random random, int x, int z) {
        byte[] retVal = new byte[32768];

        Arrays.fill(retVal, (byte) 0);

        int sysX;
        if (x >= 0) {
            sysX = x / SYSTEM_SIZE;
        } else {
            sysX = (int) Math.floor((x / (float) SYSTEM_SIZE));
        }

        int sysZ;
        if (z >= 0) {
            sysZ = z / SYSTEM_SIZE;
        } else {
            sysZ = (int) Math.floor((z / (float) SYSTEM_SIZE));
        }

        // check if the "system" this chunk is in is cached
        List<Planetoid> curSystem = cache.get(new Point(sysX, sysZ));

        if (curSystem == null) {
            // if not, does it exist on disk?
            File systemFolder = new File(plugin.getDataFolder(), "systems");
            if (!systemFolder.exists()) {
                systemFolder.mkdir();
            }
            File systemFile = new File(systemFolder, "system_" + sysX + "."
                    + sysZ + ".dat");
            if (systemFile.exists()) {
                try {
                    // load and cache
                    FileInputStream fis = new FileInputStream(systemFile);
                    ObjectInputStream ois = new ObjectInputStream(fis);
                    curSystem = (List<Planetoid>) ois.readObject();
                    cache.put(new Point(sysX, sysZ), curSystem);
                    ois.close();
                    fis.close();
                } catch (Exception ex) {
                }
            } else {
                // generate, save, and cache
                curSystem = generatePlanets(sysX, sysZ);
                try {
                    systemFile.createNewFile();
                    FileOutputStream fos = new FileOutputStream(systemFile);
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    oos.writeObject(curSystem);
                    oos.flush();
                    oos.close();
                    fos.flush();
                    fos.close();
                } catch (Exception ex) {
                }
                cache.put(new Point(sysX, sysZ), curSystem);
            }
        }

        // figure out the chunk's position in the "system"
        int chunkXPos;
        if (x >= 0) {
            chunkXPos = (x % SYSTEM_SIZE) * 16;
        } else {
            chunkXPos = SYSTEM_SIZE * 16 + ((x % SYSTEM_SIZE) * 16);
        }   
        int chunkZPos;
        if (z >= 0) {
            chunkZPos = (z % SYSTEM_SIZE) * 16;
        } else {
            chunkZPos = SYSTEM_SIZE * 16 + ((z % SYSTEM_SIZE) * 16);
        }

        // Go through the current system's planetoids and fill in this chunk as
        // needed.
        for (Planetoid curPl : curSystem) {
            // Find planet's center point relative to this chunk.
            int relCenterX = curPl.xPos - chunkXPos;
            int relCenterZ = curPl.zPos - chunkZPos;

            for (int curX = -curPl.radius; curX <= curPl.radius; curX++) {
                boolean xShell = false;
                int blkX = curX + relCenterX;
                if (blkX >= 0 && blkX < 16) {
                    // Figure out radius of this circle
                    int distFromCenter = Math.abs(curX);
                    if (curPl.radius - distFromCenter < curPl.shellThickness) {
                        xShell = true;
                    }
                    int radius = (int) Math.ceil(Math.sqrt((curPl.radius * curPl.radius)
                            - (distFromCenter * distFromCenter)));
                    for (int curZ = -radius; curZ <= radius; curZ++) {
                        int blkZ = curZ + relCenterZ;
                        if (blkZ >= 0 && blkZ < 16) {
                            boolean zShell = false;
                            int zDistFromCenter = Math.abs(curZ);
                            if (radius - zDistFromCenter < curPl.shellThickness) {
                                zShell = true;
                            }
                            int zRadius = (int) Math.ceil(Math.sqrt((radius * radius)
                                    - (zDistFromCenter * zDistFromCenter)));
                            for (int curY = -zRadius; curY <= zRadius; curY++) {
                                int blkY = curPl.yPos + curY;
                                boolean yShell = false;
                                if (zRadius - Math.abs(curY) < curPl.shellThickness) {
                                    yShell = true;
                                }
                                if (xShell || zShell || yShell) {
                                    retVal[(blkX * 16 + blkZ) * 128 + blkY] = (byte) curPl.shellBlk.getId();
                                } else {
                                    retVal[(blkX * 16 + blkZ) * 128 + blkY] = (byte) curPl.coreBlk.getId();
                                }
                            }
                        }
                    }
                }
            }
        }
        // Fill in the floor
        for (int i = 0; i < floorHeight; i++) {
            for (int j = 0; j < 16; j++) {
                for (int k = 0; k < 16; k++) {
                    if (i == 0) {
                        retVal[j * 2048 + k * 128 + i] = (byte) Material.BEDROCK.getId();
                    } else {
                        retVal[j * 2048 + k * 128 + i] = (byte) floorBlock.getId();
                    }
                }
            }
        }
        return retVal;
    }

    /**
     * Gets the world's populators.
     * 
     * @param world World
     * 
     * @return Populators
     */
    @Override
    public List<BlockPopulator> getDefaultPopulators(World world) {
        return Arrays.asList((BlockPopulator) new SpaceGlowstonePopulator(), new SpaceStonePopulator());
    }

    /**
     * Gets the can spawn-state.
     * 
     * @param world World
     * @param x X-location
     * @param z Z-location
     * 
     * @return True if can spawn
     */
    @Override
    public boolean canSpawn(World world, int x, int z) {
        return true;
    }

    /**
     * Gets the fixed spawn location.
     * 
     * @param world World
     * @param random Random
     * 
     * @return Spawn location
     */
    @Override
    public Location getFixedSpawnLocation(World world, Random random) {
        return new Location(world, 7, 77, 7);
    }

    /**
     * Generates planets.
     * 
     * @param x X-coord
     * @param z Z-coord
     * 
     * @return List of Planetoids
     */
    @SuppressWarnings("fallthrough")
    private List<Planetoid> generatePlanets(int x, int z) {
        List<Planetoid> planetoids = new ArrayList<Planetoid>();

        // If x and Z are zero, generate a log/leaf planet close to 0,0
        if (x == 0 && z == 0) {
            Planetoid spawnPl = new Planetoid();
            spawnPl.xPos = 7;
            spawnPl.yPos = 70;
            spawnPl.zPos = 7;
            spawnPl.coreBlk = Material.LOG;
            spawnPl.shellBlk = Material.LEAVES;
            spawnPl.shellThickness = 3;
            spawnPl.radius = 6;
            planetoids.add(spawnPl);
        }

        // if X is negative, left shift seed by one
        if (x < 0) {
            seed <<= 1;
        } // if Z is negative, change sign on seed.
        if (z < 0) {
            seed = -seed;
        }

        Random rand = new Random(seed);
        for (int i = 0; i < Math.abs(x) + Math.abs(z); i++) {
            // cycle generator
            rand.nextDouble();
        }

        for (int i = 0; i < density; i++) {
            // Try to make a planet
            Planetoid curPl = new Planetoid();
            curPl.shellBlk = getBlockType(rand, false, true);
            switch (curPl.shellBlk) {
                case LEAVES:
                    curPl.coreBlk = Material.LOG;
                    break;
                case ICE:
                case WOOL:
                    curPl.coreBlk = getBlockType(rand, true, true);
                default:
                    curPl.coreBlk = getBlockType(rand, true, false);
                    break;
            }

            curPl.shellThickness = rand.nextInt(maxShellSize - minShellSize)
                    + minShellSize;
            curPl.radius = rand.nextInt(maxSize - minSize) + minSize;

            // Set position, check bounds with system edges
            curPl.xPos = -1;
            while (curPl.xPos == -1) {
                int curTry = rand.nextInt(SYSTEM_SIZE * 16);
                if (curTry + curPl.radius < SYSTEM_SIZE * 16
                        && curTry - curPl.radius >= 0) {
                    curPl.xPos = curTry;
                }
            }
            curPl.yPos = rand.nextInt(128 - curPl.radius * 2 - floorHeight)
                    + curPl.radius;
            curPl.zPos = -1;
            while (curPl.zPos == -1) {
                int curTry = rand.nextInt(SYSTEM_SIZE * 16);
                if (curTry + curPl.radius < SYSTEM_SIZE * 16
                        && curTry - curPl.radius >= 0) {
                    curPl.zPos = curTry;
                }
            }

            // Created a planet, check for collisions with existing planets
            // If any collision, discard planet
            boolean discard = false;
            for (Planetoid pl : planetoids) {
                // each planetoid has to be at least pl1.radius + pl2.radius +
                // min distance apart
                int distMin = pl.radius + curPl.radius + minDistance;
                if (distanceSquared(pl, curPl) < distMin * distMin) {
                    discard = true;
                    break;
                }
            }
            if (!discard) {
                planetoids.add(curPl);
            }
        }
        BananaSpace.log.info(BananaSpace.prefix + " Made " + planetoids.size() + " planets in this region.");
        return planetoids;
    }

    /**
     * Gets the squared distance.
     * @param pl1 Planetoid 1
     * @param pl2 Planetoid 2
     * 
     * @return Distance
     */
    private int distanceSquared(Planetoid pl1, Planetoid pl2) {
        int xDist = pl2.xPos - pl1.xPos;
        int yDist = pl2.yPos - pl1.yPos;
        int zDist = pl2.zPos - pl1.zPos;

        return xDist * xDist + yDist * yDist + zDist * zDist;
    }

    /**
     * Returns a valid block type.
     * 
     * @param randrandom generator to use
     * @param core if true, searching through allowed cores, otherwise allowed shells
     * @param heated if true, will not return a block that gives off heat
     * 
     * @return Material
     */
    private Material getBlockType(Random rand, boolean core, boolean noHeat) {
        Material retVal = null;
        Map<Material, Float> refMap;
        if (core) {
            refMap = allowedCores;
        } else {
            refMap = allowedShells;
        }
        while (retVal == null) {
            int arrayPos = rand.nextInt(refMap.size());
            Material blkID = (Material) refMap.keySet().toArray()[arrayPos];
            float testVal = rand.nextFloat();
            if (refMap.get(blkID) >= testVal) {
                if (noHeat) {
                    switch (blkID) {
                        case BURNING_FURNACE:
                        case FIRE:
                        case GLOWSTONE:
                        case JACK_O_LANTERN:
                        case STATIONARY_LAVA:
                            break;
                        default:
                            retVal = blkID;
                            break;
                    }
                } else {
                    retVal = blkID;
                }
            }
        }
        return retVal;
    }
}
