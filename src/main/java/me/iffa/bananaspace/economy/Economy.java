package me.iffa.bananaspace.economy;

import com.nijikokun.register.payment.Method.MethodAccount;
import me.iffa.bananaspace.BananaSpace;
import me.iffa.bananaspace.config.SpaceConfig;
import org.bukkit.entity.Player;
import com.nijikokun.register.payment.*;

/**
 *
 * @author kitskub
 */
public class Economy {
    private static Method method;
    private static BananaSpace plugin;
    private boolean use;

    /**
     * currently only supports iconomy
     * @param plugin
     */
    public Economy(BananaSpace plugin) {
        this.plugin = plugin;
        use=true;
        getMethod();
        BananaSpace.debugLog("Hooked into "+method.getName());
    }
    /**
     * 
     */
    public Economy(){
        use=false;
    }
    public static boolean checkEconomy(BananaSpace plugin){
            if(SpaceConfig.getConfig().getBoolean("economy.enabled", true)){
                return (getMethod()!=null);
            }
        return false;
    }
    public boolean enter(Player player) {
        if (use==false) return true;
            if(method.hasAccount(player.getName())) {
                int amount =SpaceConfig.getConfig().getInt("economy.entercost",20);
                return subtract(player,amount);
            } else {
                return false;
            }   
            
            
         
    }
    public boolean exit(Player player){
        if (use==false) return true;
            if(method.hasAccount(player.getName())) {
                int amount =SpaceConfig.getConfig().getInt("economy.exitcost",20);
                return subtract(player,amount);
            } else {
                return false;
            }   
        
    }
    public boolean enterCommand(Player player){
        if (use==false) return true;
            if(method.hasAccount(player.getName())) {
                int amount =SpaceConfig.getConfig().getInt("economy.entercommandcost",20);
                return subtract(player,amount);
            } else {
                return false;
            }   
         
    }
    public boolean exitCommand(Player player){
        if (use==false) return true;
            if(method.hasAccount(player.getName())) {
                int amount =SpaceConfig.getConfig().getInt("economy.exitcommandcost",20);
                return subtract(player,amount);
            } else {
                return false;
            }   
    }

    private boolean subtract(Player player, int amount) {
        if(BananaSpace.getPlayerHandler().hasPermission("bananspace.economy.exempt",player)) return true;
        MethodAccount balance = method.getAccount(player.getName());
        if(!balance.hasEnough(amount)) return false;
        balance.subtract(amount);
        return true;
    }

    /**
     * @return method
     */
    public static Method getMethod() {
        if(method==null){
            Methods.setMethod(plugin.getServer().getPluginManager());
            method = Methods.getMethod();
        }
        return method;
    }
}
