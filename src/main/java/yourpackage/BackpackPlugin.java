package yourpackage;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class BackpackPlugin extends JavaPlugin {
    private static BackpackPlugin instance;
    private BackpackManager backpackManager;

    @Override
    public void onEnable() {
        instance = this;
        backpackManager = new BackpackManager(this);

        getCommand("backpack").setExecutor((sender, command, label, args) -> {
            if (sender instanceof org.bukkit.entity.Player player) {
                backpackManager.openBackpack(player);
                return true;
            }
            sender.sendMessage("Только игрок может использовать эту команду.");
            return false;
        });

        Bukkit.getPluginManager().registerEvents(new BackpackListener(), this);

        getLogger().info("BackpackPlugin включен.");
    }

    @Override
    public void onDisable() {
        backpackManager.saveAll();
    }

    public static BackpackPlugin getInstance() {
        return instance;
    }

    public BackpackManager getBackpackManager() {
        return backpackManager;
    }
}
