package id.my.khuirulhuda.moccivotes;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Listener;
import org.bukkit.Bukkit;
import id.my.khuirulhuda.moccivotes.events.Vote;
import id.my.khuirulhuda.moccivotes.commands.Reload;
import org.bukkit.ChatColor;
import java.io.File;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.IOException;
import org.bukkit.configuration.InvalidConfigurationException;
import id.my.khuirulhuda.moccivotes.bstats.Metrics;

public class Main extends JavaPlugin implements Listener {
  
  private File customConfigFile;
  private FileConfiguration customConfig;
  private static Main INSTANCE;
  
    @Override
    public void onEnable() {
        INSTANCE = this;
        saveDefaultConfig();
        this.getCommand("mvote").setExecutor(new Reload());
        Bukkit.getPluginManager().registerEvents(new Vote(), this);
        this.getLogger().info(ChatColor.GREEN+"MocciVotes Successfully Enabled");
        int pluginId = 12094; //DON'T TOUCH!
        Metrics metrics = new Metrics(this, pluginId);
    }
    
    @Override
    public void onDisable() {
        this.getLogger().info("MocciVotes Successfully Disabled");
    }
    
    public static Main getInstance() {
      return INSTANCE;
    }
    
    public FileConfiguration getCustomConfig() {
        return this.customConfig;
    }

    private void createCustomConfig() {
        customConfigFile = new File(getDataFolder(), "config.yml");
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
            saveResource("config.yml", false);
         }
        
        customConfig= new YamlConfiguration();
        try {
            customConfig.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
}