package de.nopantscarl.purpleitems.listener;

import de.nopantscarl.purpleitems.main.PurpleItems;
import de.nopantscarl.purpleitems.items.SpecialItems;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class PlayerPickupItems implements Listener {

    private PurpleItems purpleItems;

    public PlayerPickupItems(PurpleItems purpleItems) {
        this.purpleItems = purpleItems;
    }

    @EventHandler
    public void onPickup(PlayerPickupItemEvent e) {
        Material pickedUpType = e.getItem().getItemStack().getType();

        // Don't use for each here to optimize for cpu-cache
        ItemStack[] invContents = e.getPlayer().getInventory().getContents();
        for (int i = 0; i < invContents.length; i++) {
            ItemStack item = invContents[i];
            Optional<Material> vaccineTargetTypeOptional = SpecialItems.getVaccineTargetType(item);

            if (!vaccineTargetTypeOptional.isPresent()) {
                continue;
            }

            Material targetType = vaccineTargetTypeOptional.get();
            if (targetType == pickedUpType) {
                e.setCancelled(true);
                break;
            }
        }
    }
}
