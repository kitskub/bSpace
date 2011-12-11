// Package Declaration
package me.iffa.bspace.api.schematic;

// Java Imports
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

// bSpace Imports
import me.iffa.bspace.api.SpaceMessageHandler;

// Bukkit Imports
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.material.MaterialData;
import org.bukkit.util.BlockVector;
import org.bukkit.util.Vector;

// JNBT Imports
import org.jnbt.ByteArrayTag;
import org.jnbt.CompoundTag;
import org.jnbt.IntTag;
import org.jnbt.ListTag;
import org.jnbt.NBTInputStream;
import org.jnbt.ShortTag;
import org.jnbt.Tag;

/**
 * Schematic-file loading class. To be used for coming populators.
 * 
 * @author iffamies
 * @author DrAgonmoray (original NBT loading code)
 * @author sk89q and other people who accidently a WE commit (schematic handling)
 */
public class SpaceSchematicHandler {
    // Variables
    public static File schematicFolder = new File("plugins" + File.separator + "bSpace" + File.separator + "schematics");
    private static List<Schematic> schematics = new ArrayList<Schematic>();

    /**
     * Gets the list of schematics loaded.
     * 
     * @return List of schematics loaded
     */
    public static List<Schematic> getSchematics() {
        return schematics;
    }

    /**
     * Places a schematic to a location.
     * 
     * @param schematic Schematic
     * @param origin Location the schematic should be placed to
     */
    public static void placeSchematic(Schematic schematic, Location origin) {
        //TODO Schematic placing code (WILL GET MESSY D:)
        Map<BlockVector, Map<String, Tag>> tileEntitiesMap = new HashMap<BlockVector, Map<String, Tag>>();
        Map<Location, Map<Material, MaterialData>> blocksMap = new HashMap<Location, Map<Material, MaterialData>>();
        for (Tag tag : schematic.getTileEntities()) {
            if (!(tag instanceof CompoundTag)) {
                continue;
            }
            CompoundTag t = (CompoundTag) tag;

            int x = 0;
            int y = 0;
            int z = 0;

            Map<String, Tag> values = new HashMap<String, Tag>();

            for (Map.Entry<String, Tag> entry : t.getValue().entrySet()) {
                if (entry.getKey().equals("x")) {
                    if (entry.getValue() instanceof IntTag) {
                        x = ((IntTag) entry.getValue()).getValue();
                    }
                } else if (entry.getKey().equals("y")) {
                    if (entry.getValue() instanceof IntTag) {
                        y = ((IntTag) entry.getValue()).getValue();
                    }
                } else if (entry.getKey().equals("z")) {
                    if (entry.getValue() instanceof IntTag) {
                        z = ((IntTag) entry.getValue()).getValue();
                    }
                }

                values.put(entry.getKey(), entry.getValue());
            }

            BlockVector vec = new BlockVector(x, y, z);
            tileEntitiesMap.put(vec, values);
        }
        Vector size = new Vector(schematic.getWidth(), schematic.getHeight(), schematic.getLength());
        for (int x = 0; x < schematic.getWidth(); ++x) {
            for (int y = 0; y < schematic.getHeight(); ++y) {
                for (int z = 0; z < schematic.getLength(); ++z) {
                    int index = y * schematic.getWidth() * schematic.getLength() + z * schematic.getWidth() + x;
                    Material block = Material.getMaterial(schematic.getBlocks()[index]);
                    MaterialData blockData = null;
                    try {
                        blockData = new MaterialData(block, schematic.getBlockData()[index]);
                    } catch (Exception ex) {
                    }
                    Map<Material, MaterialData> tempMap = new EnumMap<Material, MaterialData>(Material.class);
                    tempMap.put(block, blockData);
                    blocksMap.put(new Location(origin.getWorld(), x, y, z), tempMap);
                }
            }
        }
        // Builds the schematic.
        SpaceMessageHandler.debugPrint(Level.INFO, "Now going inside buildSchematic.");
        buildSchematic(origin, blocksMap, tileEntitiesMap);
    }

    /**
     * Internal buildSchematic that places the schematic (hopefully succesfully!).
     * 
     * @param origin Origin location
     * @param blocksMap Blocks Map
     * @param tileEntitiesMap Tile Entities Map
     */
    private static void buildSchematic(Location origin, Map<Location, Map<Material, MaterialData>> blocksMap, Map<BlockVector, Map<String, Tag>> tileEntitiesMap) {
        // Variables
        SpaceMessageHandler.debugPrint(Level.INFO, "Inside buildSchematic with origin location: '" + origin.toString() + "'.");
        World world = origin.getWorld();

        // Setting blocks
        for (Location location : blocksMap.keySet()) {
            int originX = origin.getBlockX() + location.getBlockX();
            int originY = origin.getBlockY() + location.getBlockY();
            int originZ = origin.getBlockZ() + location.getBlockZ();

            for (Material material : blocksMap.get(location).keySet()) {
                world.getBlockAt(originX, originY, originZ).setType(material);
                world.getBlockAt(originX, originY, originZ).setData(blocksMap.get(location).get(material).getData());
            }
        }
    }
    
    /**
     * Loads all schematics from plugins/bSpace/schematics.
     */
    public static void loadSchematics() {
        List<File> files = Arrays.asList(new File("plugins" + File.separator + "bSpace" + File.separator + "schematics").listFiles());
        if (files.isEmpty()) {
            return;
        }
        for (File file : files) {
            if (file.isFile() && file.getName().toLowerCase().endsWith(".schematic")) {
                // It is for sure a .schematic-file, but now let's see if it's valid.
                loadSchematic(file);
            }
        }
    }
    

    /**
     * Loads a schematic file and adds it to the schematics list for later usage.
     * 
     * @param file Schematic file
     */
    @SuppressWarnings("unchecked")
    public static void loadSchematic(File file) {
        try {
            FileInputStream fis = new FileInputStream(file);
            NBTInputStream nbt = new NBTInputStream(fis);
            CompoundTag backuptag = (CompoundTag) nbt.readTag();
            Map<String, Tag> tagCollection = backuptag.getValue();
            short width = (Short) getChildTag(tagCollection, "Width", ShortTag.class).getValue();
            short height = (Short) getChildTag(tagCollection, "Height", ShortTag.class).getValue();
            short length = (Short) getChildTag(tagCollection, "Length", ShortTag.class).getValue();
            byte[] blocks = (byte[]) getChildTag(tagCollection, "Blocks", ByteArrayTag.class).getValue();
            byte[] data = (byte[]) getChildTag(tagCollection, "Data", ByteArrayTag.class).getValue();
            List<Object> entities = (List) getChildTag(tagCollection, "Entities", ListTag.class).getValue();
            List<Tag> tileentities = (List) getChildTag(tagCollection, "TileEntities", ListTag.class).getValue();
            if (nbt != null) {
                nbt.close();
            }
            if (fis != null) {
                fis.close();
            }
            Schematic schematic = new Schematic(file.getName().replace(".schematic", ""), blocks, data, width, height, length, entities, tileentities);
            schematics.add(schematic);
            SpaceMessageHandler.debugPrint(Level.INFO, "Added Schematic '" + file.getName() + "' to schematics list:\n" + width + "\n" + height + "\n" + length + "\n" + blocks + "\n" + data);
        } catch (Exception e) {
            SpaceMessageHandler.print(Level.WARNING, "There was a problem while loading schematic file '" + file.getName() + "'! Are you sure it's a schematic: " + e.getMessage());
        }
    }

    /**
     * Gets a child tag.
     * 
     * @param items Tag collection
     * @param key Key
     * @param expected Expected
     * 
     * @return Child tag
     */
    private static Tag getChildTag(Map<String, Tag> items, String key, Class<? extends Tag> expected) {
        return items.get(key);
    }

    /**
     * Constructor of SpaceSchematicHandler.
     */
    private SpaceSchematicHandler() {
    }
}
