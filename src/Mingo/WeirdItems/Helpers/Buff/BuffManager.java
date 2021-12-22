package Mingo.WeirdItems.Helpers.Buff;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;

public class BuffManager {

    // Update the item's ItemMeta - find a better name for this
    public static ItemMeta getBuff(ItemMeta meta, HashMap<BuffType, Double> effects) {

        // loop through all the buffs for the item
        for(Map.Entry<BuffType, Double> entry : effects.entrySet()) {
            switch (entry.getKey()) {
                // Add the correct attributes
                case ATTACK_DAMAGE:
                    meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier("attackDamageModifier", entry.getValue(), AttributeModifier.Operation.ADD_NUMBER));
                case ATTACK_SPEED:
                    meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier("attackSpeedModifier", entry.getValue(), AttributeModifier.Operation.ADD_NUMBER));
                    break;
                case MOVEMENT_SPEED:
                    meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, new AttributeModifier("movementSpeedModifier", entry.getValue(), AttributeModifier.Operation.ADD_NUMBER));
                    break;
            }
        }

        return meta;
    }
}
