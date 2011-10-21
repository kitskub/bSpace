/*
 */
package me.iffa.bananaspace.wgen.populators;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import me.iffa.bananaspace.api.SpaceMessageHandler;
import me.iffa.bananaspace.config.SpacePlanetConfig;
import me.iffa.bananaspace.wgen.planets.Planetoid;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

/**
 *
 * @author Jack
 */
public class PlanetPopulator extends BlockPopulator {
        // Variables
    private Map<Material, Float> allowedShells;
    private Map<Material, Float> allowedCores;
    private long seed = (long) SpacePlanetConfig.getConfig().getDouble("seed", 0.0);; // Seed for generating planetoids
    private int density = SpacePlanetConfig.getConfig().getInt("density", 15000); // Number of planetoids it will try to create per
    private int minSize = SpacePlanetConfig.getConfig().getInt("minSize", 4); // Minimum radius
    private int maxSize = SpacePlanetConfig.getConfig().getInt("maxSize", 20); // Maximum radius
    private int minDistance = SpacePlanetConfig.getConfig().getInt("minDistance", 10); // Minimum distance between planets, in blocks
    private int floorHeight = SpacePlanetConfig.getConfig().getInt("floorHeight", 0); // Floor height
    private int maxShellSize = SpacePlanetConfig.getConfig().getInt("maxShellSize", 5); // Maximum shell thickness
    private int minShellSize = SpacePlanetConfig.getConfig().getInt("minShellSize", 3); // Minimum shell thickness, should be at least 3
    
    @Override
    public void populate(World world, Random random, Chunk source) {
        loadAllowedBlocks();
        generatePlanets(world,random,source);
    }
    
    private void generatePlanets(World world, Random random, Chunk source) {
        List<Planetoid> planetoids = new ArrayList<Planetoid>();
        // If x and Z are zero, generate a log/leaf planet close to 0,0
        if (source.getX() == 0 && source.getZ() == 0) {
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

        for (int i = 0; i < density; i++) {
            if(source.getX()%4==0&&source.getZ()%4==0){
            // Try to make a planet
            Planetoid curPl = new Planetoid();
            curPl.shellBlk = getBlockType(random, false, true);
            switch (curPl.shellBlk) {
                case LEAVES:
                    curPl.coreBlk = Material.LOG;
                    break;
                case ICE:
                case WOOL:
                    curPl.coreBlk = getBlockType(random, true, true);
                default:
                    curPl.coreBlk = getBlockType(random, true, false);
                    break;
            }

            curPl.shellThickness = random.nextInt(maxShellSize - minShellSize)
                    + minShellSize;
            curPl.radius = random.nextInt(maxSize - minSize) + minSize;

            // Set position
            curPl.xPos = (source.getX() << 4) + random.nextInt(16);
            curPl.yPos = random.nextInt(128 - curPl.radius * 2 - floorHeight)
                    + curPl.radius;
            curPl.zPos = (source.getX() << 4) + random.nextInt(16);

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
        }
        SpaceMessageHandler.debugPrint(Level.INFO, "Made " + planetoids.size() + " planets in chunk ("+source.getX()+","+source.getZ()+")");
        // Go through the current system's planetoids and fill in this chunk as
        // needed.
        for (Planetoid curPl : planetoids) {
            
            for (int curX = -curPl.radius; curX <= curPl.radius; curX++) {//Iterate across every x block
                boolean xShell = false;//Is part of the x shell
                int worldX = curX + curPl.xPos;//get the x block number in the world
                // Figure out radius of this circle
                int distFromCenter = Math.abs(curX);//Distance from center in the x 
                if (curPl.radius - distFromCenter < curPl.shellThickness) {//Check if part of xShell
                    xShell = true;
                }
                int zHalfLength = (int) Math.ceil(Math.sqrt((curPl.radius * curPl.radius)
                        - (distFromCenter * distFromCenter)));//Half the amount of blocks in the z direction
                for (int curZ = -zHalfLength; curZ <= zHalfLength; curZ++) {//Iterate over all z blocks 
                    int worldZ = curZ + curPl.zPos;//get the z block number in the world
                    boolean zShell = false;//Is part of z shell
                    int zDistFromCenter = Math.abs(curZ);//Distance from center in the z
                    if (zHalfLength - zDistFromCenter < curPl.shellThickness) {//Check if part of zShell
                        zShell = true;
                    }
                    int yHalfLength = (int) Math.ceil(Math.sqrt((zHalfLength * zHalfLength)
                            - (zDistFromCenter * zDistFromCenter)));
                    for (int curY = -yHalfLength; curY <= yHalfLength; curY++) {
                        int worldY = curY + curPl.yPos;
                        boolean yShell = false;
                        if (yHalfLength - Math.abs(curY) < curPl.shellThickness) {
                            yShell = true;
                        }
                        if (xShell || zShell || yShell) {
                            world.getBlockAt(worldX, worldY, worldZ).setType(curPl.shellBlk);
                            //retVal[(worldX * 16 + worldZ) * 128 + worldY] = (byte) curPl.shellBlk.getId();
                        } else {
                            world.getBlockAt(worldX, worldY, worldZ).setType(curPl.coreBlk);
                            //retVal[(worldX * 16 + worldZ) * 128 + worldY] = (byte) curPl.coreBlk.getId();
                        }
                    }   
                }
            }
        }
    }
    
    /**
     * Loads allowed blocks
     */
    private void loadAllowedBlocks() {
        allowedCores = new EnumMap<Material, Float>(Material.class);
        allowedShells = new EnumMap<Material, Float>(Material.class);
        for (String s : (List<String>)SpacePlanetConfig.getConfig().getList(
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
        for (String s : (List<String>)SpacePlanetConfig.getConfig().getList(
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
