package yourpackage;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BackpackManager {
    private final BackpackPlugin plugin;
    private final Map<UUID, Inventory> backpacks = new HashMap<>();

    public BackpackManager(BackpackPlugin plugin) {
        this.plugin = plugin;

        // Создаем папку плагина, если не существует
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }
    }

    public void openBackpack(Player player) {
        Inventory inv = backpacks.computeIfAbsent(player.getUniqueId(), id -> loadBackpack(player));
        player.openInventory(inv);
    }

    private Inventory loadBackpack(Player player) {
        File file = new File(plugin.getDataFolder(), player.getUniqueId() + ".yml");
        Inventory inv = Bukkit.createInventory(null, 27, "§6Рюкзак");

        if (file.exists()) {
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
            for (int i = 0; i < inv.getSize(); i++) {
                ItemStack item = config.getItemStack("slot" + i);
                if (item != null) {
                    inv.setItem(i, item);
                }
            }
        }

        return inv;
    }

    public void saveBackpack(Player player) {
        UUID id = player.getUniqueId();
        Inventory inv = backpacks.get(id);
        if (inv == null) return;

        File folder = plugin.getDataFolder();
        if (!folder.exists()) folder.mkdirs();

        File file = new File(folder, id + ".yml");
        YamlConfiguration config = new YamlConfiguration();

        for (int i = 0; i < inv.getSize(); i++) {
            config.set("slot" + i, inv.getItem(i));
        }

        try {
            config.save(file);
        } catch (IOException e) {
            plugin.getLogger().warning("Не удалось сохранить рюкзак для " + player.getName());
            e.printStackTrace();
        }
    }

    public void saveAll() {
        for (UUID id : backpacks.keySet()) {
            Player player = Bukkit.getPlayer(id);
            if (player != null) {
                saveBackpack(player);
            }
        }
    }

    // Опционально — удалить рюкзак из памяти
    public void removeBackpack(UUID playerId) {
        backpacks.remove(playerId);
    }
}
