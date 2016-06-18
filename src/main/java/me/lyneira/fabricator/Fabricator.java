package me.lyneira.fabricator;

import java.util.Iterator;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.Recipe;

import me.lyneira.inventoryprocessor.InventoryProcessor;
import me.lyneira.inventoryprocessor.ProcessInventoryException;
import me.lyneira.machinacore.BlockLocation;
import me.lyneira.machinacore.HeartBeatEvent;
import me.lyneira.machinacore.block.BlockRotation;
import me.lyneira.machinafactory.ComponentActivateException;
import me.lyneira.machinafactory.ComponentDetectException;
import me.lyneira.machinafactory.PacketTypeUnsupportedException;
import me.lyneira.machinafactory.Pipeline;
import me.lyneira.machinafactory.PipelineException;
import me.lyneira.util.InventoryManager;
import me.lyneira.util.InventoryTransaction;

public class Fabricator extends InventoryProcessor {

    private static final int delay = 20;
    private static final int maxAge = 11;

    private final Blueprint blueprint;
    private final Pipeline pipeline;
    private final Transaction recipeTransaction;
    private int age = 0;

    Fabricator(Blueprint blueprint, BlockLocation anchor, BlockRotation yaw, Player player) throws ComponentActivateException, ComponentDetectException {
        super(blueprint.blueprint, anchor, yaw);
        this.blueprint = blueprint;
        recipeTransaction = determineRecipe();
        if (recipeTransaction == null) {
            onDeActivate(anchor);
            throw new ComponentActivateException();
        }
        pipeline = new Pipeline(anchor, player, sender());
    }

    private Transaction determineRecipe() {
        Inventory chestInventory = InventoryManager.getSafeInventory(chest().getBlock());
        RecipeVerifier verifier;
        try {
            verifier = new RecipeVerifier(chestInventory);
        } catch (ComponentActivateException e) {
            return null;
        }
        Iterator<Recipe> it = blueprint.plugin.getServer().recipeIterator();
        return verifier.find(it);
    }

    @Override
    protected boolean process(Inventory inventory) throws ProcessInventoryException {
        InventoryTransaction transaction = new InventoryTransaction(inventory);
        transaction.remove(recipeTransaction.ingredients);

        if (!transaction.verify()) {
            // Transaction can't be completed, so the sending inventory has not
            // enough items.
            return false;
        }

        boolean sendResult = false;
        try {
            sendResult = pipeline.sendPacket(recipeTransaction.result.clone());
        } catch (PacketTypeUnsupportedException e) {
            // Other end can't handle items
            throw new ProcessInventoryException();
        } catch (PipelineException e) {
            // Pipeline is broken
            throw new ProcessInventoryException();
        }

        age = 0;
        if (sendResult) {
            // Make a new transaction here in case the fabricator fed the result
            // back to the source inventory.
            transaction = new InventoryTransaction(inventory);
            transaction.remove(recipeTransaction.ingredients);
            transaction.execute();
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected HeartBeatEvent postTransactions() {
        if (age++ >= maxAge)
            return null;
        return new HeartBeatEvent(delay);
    }

    private final BlockLocation chest() {
        return anchor.getRelative(blueprint.chest.vector(yaw));
    }

    private final BlockLocation sender() {
        return anchor.getRelative(blueprint.sender.vector(yaw));
    }
}
