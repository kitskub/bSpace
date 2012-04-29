// Package Declaration
package me.iffa.bspace.api.schematic;

// Java Imports
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.zip.GZIPInputStream;

// bSpace Imports
import me.iffa.bspace.handlers.MessageHandler;

// Bukkit Imports
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.material.MaterialData;
import org.bukkit.util.BlockVector;

// JNBT Imports
import org.jnbt.ByteArrayTag;
import org.jnbt.CompoundTag;
import org.jnbt.IntTag;
import org.jnbt.ListTag;
import org.jnbt.NBTConstants;
import org.jnbt.NBTInputStream;
import org.jnbt.ShortTag;
import org.jnbt.StringTag;
import org.jnbt.Tag;

/**
 * Schematic-file loading class. To be used for populators
 * 
 * @author iffamies
 * @author kitskub
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
        return Collections.unmodifiableList(schematics);
    }

    /**
     * Places a schematic to a location.
     * 
     * @param schematic Schematic
     * @param origin Location the schematic should be placed to
     */
    public static void placeSchematic(Schematic schematic, Location origin) {
        Map<Location, Map<Material, MaterialData>> blocksMap = getBlocksMap(schematic, origin);
        Map<BlockVector, Map<String, Tag>> tileEntitiesMap = getTileEntitiesMap(schematic.getTileEntities());

        // Builds the schematic.
        MessageHandler.debugPrint(Level.INFO, "Now going inside buildSchematic.");
        buildSchematic(origin, blocksMap, tileEntitiesMap);
    }

    /**
     * Internal buildSchematic that places the schematic (hopefully succesfully!).
     * 
     * @param origin Origin location
     * @param blocksMap Blocks Map
     * @param tileEntitiesMap Tile Entities Map
     */
    private static void buildSchematic(Location origin, 
            Map<Location, Map<Material, MaterialData>> blocksMap, 
            Map<BlockVector, Map<String, Tag>> tileEntitiesMap) {
        
        // Variables
        MessageHandler.debugPrint(Level.INFO, "Inside buildSchematic with origin location: '" + origin.toString() + "'.");
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
    
    private static Schematic loadSchematic(String name, NBTInputStream nbtStream) throws IOException, Exception{
            // Schematic tag
            CompoundTag schematicTag = (CompoundTag) nbtStream.readTag();
            if (!schematicTag.getName().equals("Schematic")) {
                throw new Exception("Tag \"Schematic\" does not exist or is not first");
            }

            // Check
            Map<String, Tag> tagCollection = schematicTag.getValue();
            if (!tagCollection.containsKey("Blocks")) {
                throw new Exception("Schematic file is missing a \"Blocks\" tag");
            }

            // Get information
            short width = getChildTag(tagCollection, "Width", ShortTag.class).getValue();
            short length = getChildTag(tagCollection, "Length", ShortTag.class).getValue();
            short height = getChildTag(tagCollection, "Height", ShortTag.class).getValue();

            // Get blocks
            byte[] blocks = getChildTag(tagCollection, "Blocks", ByteArrayTag.class).getValue();
            byte[] data = getChildTag(tagCollection, "Data", ByteArrayTag.class).getValue();
            
            List<Tag> entities = getChildTag(tagCollection, "Entities", ListTag.class).getValue();

            // Check type of Schematic
            String materials = getChildTag(tagCollection, "Materials", StringTag.class).getValue();
            if (!materials.equals("Alpha")) {
                throw new Exception("Schematic file is not an Alpha schematic");
            }

            // Need to pull out tile entities
            List<Tag> tileEntities = getChildTag(tagCollection, "TileEntities", ListTag.class).getValue();
            Schematic schem = new Schematic(name, blocks, data, width, height, length, entities, tileEntities);
            return schem;
    }

    /**
     * Loads a schematic file and adds it to the schematics list for later usage.
     * 
     * @param file Schematic file
     */
    @SuppressWarnings("unchecked")
    public static void loadSchematic(File file) {
        try{
            FileInputStream fis;
            NBTInputStream nbt;
            if(isGZIP(file)){
                fis = new FileInputStream(file);
                nbt = new NBTInputStream(new GZIPInputStream(fis));
            }
            else{
                fis = new FileInputStream(file);
                nbt = new NBTInputStream(fis);    
            }
            
            Schematic schematic = loadSchematic(file.getName().replaceAll(".schematic", ""), nbt);
            if (nbt != null) {
                nbt.close();
            }
            if (fis != null) {
                fis.close();
            }
            schematics.add(schematic);
            MessageHandler.debugPrint(Level.INFO, "Added Schematic '" + file.getName() + "' to schematics list:\n" + schematic.getWidth() + "\n" + schematic.getHeight() + "\n" + schematic.getLength() + "\n" + schematic.getBlocks() + "\n" + schematic.getBlockData());
        } catch (Exception e) {
            MessageHandler.print(Level.WARNING, "There was a problem while loading schematic file '" + file.getName() + "'! Are you sure it's a schematic: " + e.getMessage());
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
    private static <T extends Tag>  T getChildTag(Map<String, Tag> items, String key, Class<T> expected) throws Exception {
        if (!items.containsKey(key)) {
            throw new Exception("Schematic file is missing a \"" + key + "\" tag");
        }
        Tag tag = items.get(key);
        if (!expected.isInstance(tag)) {
            throw new Exception(
                    key + " tag is not of tag type " + expected.getName());
        }
        return expected.cast(tag);
    }

    public static boolean isGZIP(File file) {
        DataInputStream str = null;
        try {
            str = new DataInputStream(new GZIPInputStream(new FileInputStream(file)));
            if ((str.readByte() & 0xFF) != NBTConstants.TYPE_COMPOUND) {
                return false;
            }
            byte[] nameBytes = new byte[str.readShort() & 0xFFFF];
            str.readFully(nameBytes);
            String name = new String(nameBytes, NBTConstants.CHARSET);
            return name.equals("Schematic");
        } catch (IOException e) {
            return false;
        } finally {
            if (str != null) {
                try {
                    str.close();
                } catch (IOException ignore) {
                    // blargh
                }
            }
        }
    }

    private static Map<BlockVector, Map<String, Tag>> getTileEntitiesMap(List<Tag> tileEntities){
        Map<BlockVector, Map<String, Tag>> tileEntitiesMap = new HashMap<BlockVector, Map<String, Tag>>();

        for (Tag tag : tileEntities) {
            if (!(tag instanceof CompoundTag)) continue;
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
        return tileEntitiesMap;
    }
    
    private static Map<Location, Map<Material, MaterialData>> getBlocksMap(Schematic schematic, Location origin){
        Map<Location, Map<Material, MaterialData>> toRet = new HashMap<Location, Map<Material, MaterialData>>();
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
                    toRet.put(new Location(origin.getWorld(), x, y, z), tempMap);
                }
            }
        }
        return toRet;
    }
    
    /**
     * Constructor of SpaceSchematicHandler.
     */
    private SpaceSchematicHandler() {
    }
}
