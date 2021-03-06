package us.talabrek.ultimateskyblock;

import org.bukkit.inventory.*;
import org.bukkit.enchantments.*;

import java.util.*;
import java.util.logging.Level;

public class ItemParser {
    public static String parseItemStackToString(final ItemStack item) {
        if (item == null) {
            return "";
        }
        String s = "";
        s = s + "id:" + item.getTypeId() + ";";
        s = s + "amount:" + item.getAmount() + ";";
        s = s + "durab:" + item.getDurability() + ";";
        s = s + "data:" + item.getData().getData() + ";";
        if (item.getEnchantments().size() > 0) {
            s = s + "ench:";
            for (final Enchantment e : item.getEnchantments().keySet()) {
                s = s + "eid#" + e.getId() + " ";
                s = s + "elevel#" + item.getEnchantments().get(e) + " ";
            }
        }
        return s.trim();
    }

    public static ItemStack getItemStackfromString(final String s) {
        if (s.equalsIgnoreCase("")) {
            return null;
        }
        final ItemStack x = new ItemStack(1);
        String[] split;
        for (int length = (split = s.split(";")).length, i = 0; i < length; ++i) {
            final String thing = split[i];
            final String[] sp = thing.split(":");
            if (sp.length != 2) {
                uSkyBlock.log(Level.WARNING,  "error, wrong type size '" + thing + "'");
            }
            final String name = sp[0];
            if (name.equals("id")) {
                x.setTypeId(Integer.parseInt(sp[1]));
            } else if (name.equals("amount")) {
                x.setAmount(Integer.parseInt(sp[1]));
            } else if (name.equals("durab")) {
                x.setDurability((short) Integer.parseInt(sp[1]));
            } else if (name.equals("data")) {
                x.getData().setData((byte) Integer.parseInt(sp[1]));
            } else if (name.equals("ench")) {
                int enchId = 0;
                int level = 0;
                String[] split2;
                for (int length2 = (split2 = sp[1].split(" ")).length, j = 0; j < length2; ++j) {
                    final String enchantment = split2[j];
                    final String[] prop = enchantment.split("#");
                    if (prop.length != 2) {
                        uSkyBlock.log(Level.SEVERE, "error, wrong enchantmenttype length, '" + enchantment + "'");
                    }
                    if (prop[0].equals("eid")) {
                        enchId = Integer.parseInt(prop[1]);
                    } else if (prop[0].equals("elevel")) {
                        level = Integer.parseInt(prop[1]);
                        x.addUnsafeEnchantment(Enchantment.getById(enchId), level);
                    }
                }
            } else {
                uSkyBlock.log(Level.SEVERE, "error, unknown itemvalue, '" + name + "'");
            }
        }
        return x;
    }
}
