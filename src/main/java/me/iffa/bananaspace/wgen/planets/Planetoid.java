// Package Declaration
package me.iffa.bananaspace.wgen.planets;

// Java Imports
import java.io.Serializable;

// Bukkit Imports
import org.bukkit.Material;

/**
 * Holder class for an individual planetoid.
 * 
 * @author Canis85
 * @author iffa
 */
public class Planetoid implements Serializable {
    // Variables
    private static final long serialVersionUID = 1L;
    public Material coreBlk;
    public Material shellBlk;
    public int shellThickness;
    public int radius;
    public int xPos;
    public int yPos;
    public int zPos;

    /**
     * Constructor for Planetoid.
     */
    public Planetoid() {
    }

    /**
     * Constructor for Planetoid.
     * 
     * @param coreID Core material
     * @param shellID Shell material
     * @param shellThick Shell thickness
     * @param radius Radius
     * @param x X-coord
     * @param y Y-coord
     * @param z Z-coord
     */
    public Planetoid(Material coreID, Material shellID, int shellThick, int radius, int x, int y, int z) {
        this.coreBlk = coreID;
        this.shellBlk = shellID;
        this.shellThickness = shellThick;
        this.radius = radius;

        this.xPos = x;
        this.yPos = y;
        this.zPos = z;
    }
}
