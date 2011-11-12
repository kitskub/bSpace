// Package Declaration
package me.iffa.bananaspace.api.schematic;

// Java Imports
import java.util.List;

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
    private List<Object> entities;
    private List<Object> tileEntities;
    
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
    public Schematic(String name, byte[] blocks, byte[] data, short width, short height, short length, List<Object> entities, List<Object> tileEntities) {
        this.name = name;
        this.blocks = blocks;
        this.data = data;
        this.width = width;
        this.height = height;
        this.length = length;
        this.entities = entities;
        this.tileEntities = tileEntities;
    }
}
