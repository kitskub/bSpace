/*
 */
package me.iffa.bspace.api;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author kitskub
 */
public class SpaceAddonHandler {
    
    public static Set<SpaceAddon> addons = new HashSet<SpaceAddon>();
    
    public static void registerAddon(SpaceAddon addon){
        addons.add(addon);
    }
    
    protected SpaceAddonHandler(){}
    
}
