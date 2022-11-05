package de.nopantscarl.purpleitems.listener;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import de.nopantscarl.purpleitems.items.SpecialItems;
import de.nopantscarl.purpleitems.main.PurpleItems;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerArmorChange implements Listener {

    private PurpleItems purpleItems;

    public PlayerArmorChange(PurpleItems purpleItems) {
        this.purpleItems = purpleItems;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onArmorChange(PlayerArmorChangeEvent e) {
        Player player = e.getPlayer();

        ItemStack oldItem = e.getOldItem();
        ItemStack newItem = e.getNewItem();

        if (SpecialItems.checkSpecialItemPredicate(oldItem, SpecialItems.Item::isTurtleSet)) {
            player.removePotionEffect(PotionEffectType.WATER_BREATHING);
            player.removePotionEffect(PotionEffectType.NIGHT_VISION);
        }

        if (SpecialItems.isWearingFullTurtleSet(player)) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, Integer.MAX_VALUE, 0, false, false));
            player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0, false, false));
        }

    }
}
