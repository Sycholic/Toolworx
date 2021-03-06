package me.lyneira.fabricator;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.lyneira.machinacore.BlockLocation;
import me.lyneira.machinacore.block.BlockRotation;
import me.lyneira.machinacore.block.BlockVector;
import me.lyneira.machinacore.machina.Machina;
import me.lyneira.machinacore.machina.MachinaBlueprint;
import me.lyneira.machinafactory.ComponentActivateException;
import me.lyneira.machinafactory.ComponentBlueprint;
import me.lyneira.machinafactory.ComponentDetectException;
import me.lyneira.machinafactory.MachinaFactory;

import net.boutopia.toolworx.mecharchitech.BlueprintBlock;

/**
 * Blueprint for the {@link Fabricator}.
 *
 * @author Lyneira
 */
public class Blueprint implements MachinaBlueprint {
    private final Material anchorMaterial;
    final MachinaFactory plugin;
    final BlueprintBlock chest;
    final BlueprintBlock sender;
    final ComponentBlueprint blueprint;

    /**
     * The blueprints for the base, inactive and active states are specified
     * here.
     */
    public Blueprint(MachinaFactory plugin) {
        this.plugin = plugin;
        anchorMaterial = ComponentBlueprint.coreMaterial();
        BlueprintBlock[] blueprintBase = {new BlueprintBlock(new BlockVector(0, 0, 0), anchorMaterial, true), //
                new BlueprintBlock(new BlockVector(1, 0, 0), Material.WORKBENCH, true), //
                chest = new BlueprintBlock(new BlockVector(0, 1, 0), Material.CHEST, false), //
        };
        BlueprintBlock[] blueprintInactive = {new BlueprintBlock(new BlockVector(2, 1, 0), Material.IRON_FENCE, false), //
                new BlueprintBlock(new BlockVector(2, 0, 0), ComponentBlueprint.pipelineMaterial(), false), //
        };

        BlueprintBlock[] blueprintActive = {new BlueprintBlock(new BlockVector(2, 0, 0), Material.IRON_FENCE, false), //
                sender = new BlueprintBlock(new BlockVector(3, 0, 0), ComponentBlueprint.pipelineMaterial(), false), //
        };
        blueprint = new ComponentBlueprint(blueprintBase, blueprintInactive, blueprintActive);
    }

    @Override
    public Machina detect(Player player, BlockLocation anchor, BlockFace leverFace, ItemStack itemInHand) {
        BlockRotation yaw = null;

        if (!anchor.checkType(anchorMaterial))
            return null;

        for (BlockRotation i : BlockRotation.values()) {
            if (anchor.getRelative(i.getYawFace()).checkType(Material.WORKBENCH)) {
                yaw = i;
                break;
            }
        }

        if (yaw == null)
            return null;

        if (!player.hasPermission("machinafactory.fabricator")) {
            player.sendMessage("You do not have permission to activate a fabricator.");
            return null;
        }

        try {
            return new Fabricator(this, anchor, yaw, player);
        } catch (ComponentDetectException e) {
        } catch (ComponentActivateException e) {
        }
        return null;
    }
}
