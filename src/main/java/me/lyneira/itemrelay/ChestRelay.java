package me.lyneira.itemrelay;

import me.lyneira.machinacore.BlockLocation;
import me.lyneira.machinacore.block.BlockRotation;
import me.lyneira.machinafactory.ComponentActivateException;
import me.lyneira.machinafactory.ComponentDetectException;

import org.bukkit.entity.Player;

/**
 * Item Relay with a chest as container.
 * 
 * @author Lyneira
 */
public class ChestRelay extends ItemRelay {

    ChestRelay(Blueprint blueprint, BlockLocation anchor, BlockRotation yaw, Player player) throws ComponentActivateException, ComponentDetectException {
        super(blueprint, blueprint.blueprintChest, anchor, yaw, player);
    }

    @Override
    protected BlockLocation container() {
        return anchor.getRelative(blueprint.chest.vector(yaw));
    }

}
