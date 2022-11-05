package de.nopantscarl.purpleitems.listener;

import de.nopantscarl.purpleitems.items.SpecialItems;
import org.bukkit.entity.FishHook;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;

public class PlayerFishEventListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void handlePlayerFishEvent(PlayerFishEvent event) {
        FishHook fishHook = event.getHook();

        if (SpecialItems.isWearingFullTurtleSet(event.getPlayer())) {
            fishHook.setMinWaitTime(fishHook.getMinWaitTime() / 3);
            fishHook.setMaxWaitTime(fishHook.getMaxWaitTime() / 3);
        }
    }

}
