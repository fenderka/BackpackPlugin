package yourpackage;

import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.entity.Player;

public class BackpackListener implements Listener {

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        InventoryView view = event.getView();
        if (view.getTitle().equals("§6Рюкзак")) {
            Player player = (Player) event.getPlayer();
            player.sendMessage("Вы открыли рюкзак!");
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        InventoryView view = event.getView();
        if (view.getTitle().equals("§6Рюкзак")) {
            // event.setCancelled(true); // Убери эту строку, чтобы разрешить любые действия с рюкзаком

            // Можно оставить уведомление при клике, если хочешь
            Player player = (Player) event.getWhoClicked();
            player.sendMessage("Вы взаимодействуете с рюкзаком!");
        }
    }
}
