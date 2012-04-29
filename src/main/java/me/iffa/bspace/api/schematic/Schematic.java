// Package Declaration
package me.iffa.bspace.api.schematic;

// Java Imports
import java.util.List;

// JNBT Imports
import org.jnbt.Tag;

/**
 * Represents a schematic.
 * 
 * @author iffamies
 */
public class Schematic {
    // Variables
    private String name;
    private byte[] blocks;
    private byte[] data;
    private short width;
    private short height;
    private short length;
    private List<Tag> entities;
    private List<Tag> tileEntities;
    
    /**
     * Constructor of Schematic.
     * 
     * @param name Name of the schematic
     * @param blocks Blocks
     * @param data Block data
     * @param width Width (X)
     * @param height Height (Y)
     * @param length Length (Z)
     * @param entities Entities
     * @param tileEntities Tile entities
     */
    public Schematic(String name, 
            byte[] blocks, byte[] data, short width, short height, short length, 
            List<Tag> entities, List<Tag> tileEntities) {
        this.name = name;
        this.blocks = blocks;
        this.data = data;
        this.width = width;
        this.height = height;
        this.length = length;
        this.entities = entities;
        this.tileEntities = tileEntities;
    }
    
    /**
     * Gets the name of the schematic.
     * 
     * @return Name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Gets the blocks of the schematic.
     * 
     * @return Blocks
     */
    public byte[] getBlocks() {
        return blocks;
    }
    
    /**
     * Gets the block data of the schematic.
     * 
     * @return Block data
     */
    public byte[] getBlockData() {
        return data;
    }
    
    /**
     * Gets the width of the schematic.
     * 
     * @return Width (X)
     */
    public short getWidth() {
        return width;
    }
    
    /**
     * Gets the height of the schematic.
     * 
     * @return Height (Y)
     */
    public short getHeight() {
        return height;
    }
    
    /**
     * Gets the length of the schematic.
     * 
     * @return Length (Z)
     */
    public short getLength() {
        return length;
    }
    
    /**
     * Gets the entities of the schematic.
     * 
     * @return Entities
     */
    public List<Tag> getEntities() {
        return entities;
    }
    
    /**
     * Gets the tile entities of the schematic.
     * 
     * @return Tile entities
     */
    public List<Tag> getTileEntities() {
        return tileEntities;
    }
}
