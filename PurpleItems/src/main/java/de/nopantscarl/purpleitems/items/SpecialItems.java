package de.nopantscarl.purpleitems.items;

import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

public class SpecialItems {

    public static final String NBT_VACCINE_TARGET_TYPE = "vacc-target-type";
    public static final String NBT_ITEM_TAG = "vacc-target-type";

    private SpecialItems() {
    }

    public static ItemStack createVaccineItem(Material targetType, Material displayType, String displayName, List<String> lore) {
        ItemStack result = new ItemStack(displayType);
        ItemMeta resultMeta = result.getItemMeta();
        resultMeta.setDisplayName(displayName);
        resultMeta.setLore(lore);
        result.setItemMeta(resultMeta);

        NBTItem nbtItem = new NBTItem(result);
        nbtItem.setString(NBT_VACCINE_TARGET_TYPE, targetType.name());
        nbtItem.applyNBT(result);

        return result;
    }

    public static Optional<Material> getVaccineTargetType(ItemStack itemStack) {
        if (itemStack == null || itemStack.getType().isAir()) {
            return Optional.empty();
        }

        NBTItem nbtItem = new NBTItem(itemStack);

        if (!nbtItem.hasKey(NBT_VACCINE_TARGET_TYPE)) {
            return Optional.empty();
        }

        String targetTypeString = nbtItem.getString(NBT_VACCINE_TARGET_TYPE);

        try {
            Material targetType = Material.valueOf(targetTypeString);
            return Optional.of(targetType);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public static ItemStack createSpecialItem(Item item, String displayName, List<String> lore) {
        ItemStack result = new ItemStack(item.bukkitMaterial);
        ItemMeta resultMeta = result.getItemMeta();
        resultMeta.setDisplayName(displayName);
        resultMeta.setLore(lore);

        switch (item) {
            case TURTLE_HELMET: {
                resultMeta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(
                        UUID.randomUUID(),
                        "generic.armor",
                        3,
                        AttributeModifier.Operation.ADD_NUMBER,
                        EquipmentSlot.HEAD
                ));
                resultMeta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(
                        UUID.randomUUID(),
                        "generic.armor_toughness",
                        3,
                        AttributeModifier.Operation.ADD_NUMBER,
                        EquipmentSlot.HEAD
                ));
                resultMeta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, new AttributeModifier(
                        UUID.randomUUID(),
                        "generic.knockback_resistance",
                        0.1,
                        AttributeModifier.Operation.ADD_NUMBER,
                        EquipmentSlot.HEAD
                ));
            }
            break;
        }

        result.setItemMeta(resultMeta);

        NBTItem nbtItem = new NBTItem(result);
        nbtItem.setString(NBT_ITEM_TAG, item.name());
        nbtItem.applyNBT(result);

        return result;
    }

    public static Optional<Item> getSpecialItemType(ItemStack itemStack) {
        if (itemStack == null || itemStack.getType().isAir()) {
            return Optional.empty();
        }

        NBTItem nbtItem = new NBTItem(itemStack);

        if (!nbtItem.hasKey(NBT_ITEM_TAG)) {
            return Optional.empty();
        }

        String speecialItemTypeString = nbtItem.getString(NBT_ITEM_TAG);

        try {
            Item specialItemType = Item.valueOf(speecialItemTypeString);
            return Optional.of(specialItemType);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public static boolean isOfSpecialItemType(ItemStack itemStack, Item item) {
        return getSpecialItemType(itemStack).filter(item::equals).isPresent();
    }

    public static boolean checkSpecialItemPredicate(ItemStack itemStack, Predicate<Item> itemPredicate) {
        return getSpecialItemType(itemStack).filter(itemPredicate).isPresent();
    }

    public static boolean isWearingFullTurtleSet(Player player) {
        PlayerInventory inventory = player.getInventory();
        return isOfSpecialItemType(inventory.getHelmet(), Item.TURTLE_HELMET)
                && isOfSpecialItemType(inventory.getChestplate(), Item.TURTLE_CHESTPLATE)
                && isOfSpecialItemType(inventory.getLeggings(), Item.TURTLE_LEGGINGS)
                && isOfSpecialItemType(inventory.getBoots(), Item.TURTLE_BOOTS);
    }

    public enum Item {

        TURTLE_HELMET(Material.TURTLE_HELMET),
        TURTLE_CHESTPLATE(Material.NETHERITE_CHESTPLATE),
        TURTLE_LEGGINGS(Material.NETHERITE_LEGGINGS),
        TURTLE_BOOTS(Material.NETHERITE_BOOTS);

        private Material bukkitMaterial;

        Item(Material bukkitMaterial) {
            this.bukkitMaterial = bukkitMaterial;
        }

        public boolean isTurtleSet() {
            return this == TURTLE_HELMET || this == TURTLE_CHESTPLATE || this == TURTLE_LEGGINGS || this == TURTLE_BOOTS;
        }

    }

}
