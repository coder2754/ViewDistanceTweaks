package com.froobworld.viewdistancetweaks.hook.viewdistance;

import com.froobworld.viewdistancetweaks.limiter.ClientViewDistanceManager;
import com.froobworld.viewdistancetweaks.util.NmsUtils;
import com.froobworld.viewdistancetweaks.util.SpigotViewDistanceSyncer;
import com.froobworld.viewdistancetweaks.util.ViewDistanceUtils;
import org.bukkit.World;
import org.bukkit.entity.Player;

import static org.joor.Reflect.*;

public class SpigotViewDistanceHook implements ViewDistanceHook {
    private final ClientViewDistanceManager clientViewDistanceManager;

    public SpigotViewDistanceHook(ClientViewDistanceManager clientViewDistanceManager) {
        this.clientViewDistanceManager = clientViewDistanceManager;
    }

    @Override
    public int getDistance(World world) {
        return world.getViewDistance();
    }

    @Override
    public void setDistance(World world, int value) {
        value = ViewDistanceUtils.clampViewDistance(value);
        for (Player player : world.getPlayers()) {
            player.setViewDistance(value);
        }
    }

    private static void sendUpdatedSimulationDistance(World world, int distance) {
        for (Player player : world.getPlayers()) {
            player.setSimulationDistance(distance);
        }
    }

    public static boolean isCompatible() {
        return NmsUtils.getMajorVersion() == 1
                && NmsUtils.getMinorVersion() >= 18 // at least 1.18
                && NmsUtils.getMinorVersion() <= 20 // no more than 1.20
                && (NmsUtils.getRevisionNumber() < 20 || NmsUtils.getRevisionNumber() <= 1); // no more than 1.20 R1
    }

}
