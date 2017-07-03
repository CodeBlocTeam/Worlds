package io.github.codeblocteam.worlds.events;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.entity.living.humanoid.player.TargetPlayerEvent;
import org.spongepowered.api.event.impl.AbstractEvent;
import org.spongepowered.api.event.world.TargetWorldEvent;
import org.spongepowered.api.world.World;

public class PlayerChangeWorldEvent
        extends AbstractEvent
        implements TargetPlayerEvent, TargetWorldEvent {

    private final Cause cause;
    private final Player traveler;
    private final World destination;

    public PlayerChangeWorldEvent(Cause cause, Player traveler, World destination) {
        this.cause = cause;
        this.traveler = traveler;
        this.destination = destination;
    }

    @Override
    public Player getTargetEntity() {
        return traveler;
    }

    @Override
    public World getTargetWorld() {
        return destination;
    }

    @Override
    public Cause getCause() {
        return cause;
    }
}
