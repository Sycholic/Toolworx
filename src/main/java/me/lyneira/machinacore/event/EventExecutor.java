package me.lyneira.machinacore.event;

import org.bukkit.event.EventException;

import me.lyneira.machinacore.machina.MachinaController;

/**
 * Defines the class for event callbacks to MachinaControllers
 * 
 * @author Lyneira
 */
public interface EventExecutor {
    public void execute(MachinaController controller, Event event) throws EventException;
}
