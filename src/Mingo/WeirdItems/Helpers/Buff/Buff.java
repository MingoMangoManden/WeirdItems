package Mingo.WeirdItems.Helpers.Buff;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;

public class Buff {

    BuffType type;
    double strength;

    HashMap<BuffType, Double> effects = new HashMap<>();

    public Buff(BuffType type, double strength) {
        this.type = type;
        this.strength = strength;
    }

    public Buff(BuffType type, StrengthType strength) {
        this.type = type;
        this.strength = strength.getStrength();
    }

    public Buff(HashMap<BuffType, Double> effects) {
        this.effects = effects;
    }

    // Update the item's ItemMeta - find a better name for this
    public ItemMeta getBuff(ItemMeta meta) {
        switch (this.type) {
            // Add the rest of the attributes
            case ATTACK_DAMAGE:
                meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier("attackDamageModifier", this.strength, AttributeModifier.Operation.ADD_NUMBER));
            case ATTACK_SPEED:
                meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier("attackSpeedModifier", this.strength, AttributeModifier.Operation.ADD_NUMBER));
                break;
            case MOVEMENT_SPEED:
                meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, new AttributeModifier("movementSpeedModifier", this.strength, AttributeModifier.Operation.ADD_NUMBER));
                break;
            default:
                throw new IllegalStateException("sus value detected: " + this.type);
        }
        // Hide attributes - redundant for now, since items do this by themselves.
        //meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);

        return meta;
    }

    public BuffType getType() { return this.type; }
}

/*
Item hammer = new Item("Hammer", Material.NETHERITE_AXE, RarityType.EPIC, lore, enchants, new Category[]{Category.WEAPON, Category.MAGICAL});
ItemMeta meta = hammer.getItem().getItemMeta();
meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier("attackDamageModifier", 17.0D, AttributeModifier.Operation.ADD_NUMBER));
meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier("attackSpeedModifier", -3.6D, AttributeModifier.Operation.ADD_NUMBER));
meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
hammer.getItem().setItemMeta(meta);
 */