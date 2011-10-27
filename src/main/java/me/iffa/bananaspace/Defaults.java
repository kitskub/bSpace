// Package Declaration
package me.iffa.bananaspace;

/**
 * Default config values.
 * 
 * @author Jack
 * @author iffa
 */
public enum Defaults {
    //Global
    REQUIRE_HELMET(true),
    DEBUGGING(false),
    REQUIRE_SUIT(false),
    ARMOR_TYPE("iron"),
    USE_SPOUT(true),  
    HELMET_GIVEN(false),
    SUIT_GIVEN(false),
    ECONOMY_ENABLED(false),
    ENTER_COST(20),
    EXIT_COST(20),
    ENTER_COMMAND_COST(20),
    EXIT_COMMAND_COST(20),
    //ID
    HOSTILE_MOBS_ALLOWED(false),
    NEUTRAL_MOBS_ALLOWED(true),
    FORCE_NIGHT(true),
    HELMET_BLOCK(86),
    ROOM_HEIGHT(5),
    ALLOW_WEATHER(false),
    GLOWSTONE_CHANCE(1),
    STONE_CHANCE(3),
    ASTEROIDS_ENABLED(true),
    SATELLITES_ENABLED(true),
    SATELLITE_CHANCE(1),
    GENERATE_PLANETS(true),
    //Planet
    DENSITY(15000),
    MIN_SIZE(4),
    MAX_SIZE(20),
    MIN_DISTANCE(10),
    FLOOR_HEIGHT(0),
    MAX_SHELL_SIZE(5),
    MIN_SHELL_SIZE(3),
    FLOOR_BLOCK("STATIONARY_WATER");

    // Variables
    private Object value;
    
    /**
     * Constructor of Defaults.
     * 
     * @param def Boolean
     */
    Defaults(boolean def){
        this.value = def;
    }
    
    /**
     * Constructor of Defaults.
     * 
     * @param def Integer
     */
    Defaults(int def){
        this.value = def;
    }
    
    /**
     * Constructor of Defaults.
     * 
     * @param def String
     */
    Defaults(String def){
        this.value = def;
    }
    
    /**
     * Gets the default value.
     * 
     * @return Default value
     */
    public Object getDefault(){
        return value;
    }
}
