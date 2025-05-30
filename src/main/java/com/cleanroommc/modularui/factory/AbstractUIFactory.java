package com.cleanroommc.modularui.factory;

import com.cleanroommc.modularui.api.IGuiHolder;
import com.cleanroommc.modularui.api.UIFactory;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.screen.ModularScreen;
import com.cleanroommc.modularui.screen.UISettings;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public abstract class AbstractUIFactory<T extends GuiData> implements UIFactory<T> {

    protected static EntityPlayerMP verifyServerSide(EntityPlayer player) {
        if (player == null) throw new NullPointerException("Can't open UI for null player!");
        if (player instanceof EntityPlayerMP entityPlayerMP) return entityPlayerMP;
        throw new IllegalStateException("Expected server player to open UI on server!");
    }

    protected static EntityPlayerSP verifyClientSide(EntityPlayer player) {
        if (player == null) throw new NullPointerException("Can't open UI for null player!");
        if (player instanceof EntityPlayerSP entityPlayerMP) return entityPlayerMP;
        throw new IllegalStateException("Expected client player to open UI on client side!");
    }

    private final String name;

    protected AbstractUIFactory(String name) {
        this.name = Objects.requireNonNull(name);
    }

    @Override
    public final @NotNull String getFactoryName() {
        return this.name;
    }

    @NotNull
    public abstract IGuiHolder<T> getGuiHolder(T data);

    @Override
    public ModularPanel createPanel(T guiData, PanelSyncManager syncManager, UISettings settings) {
        IGuiHolder<T> guiHolder = Objects.requireNonNull(getGuiHolder(guiData), "Gui holder must not be null!");
        return guiHolder.buildUI(guiData, syncManager, settings);
    }

    @Override
    public ModularScreen createScreen(T guiData, ModularPanel mainPanel) {
        IGuiHolder<T> guiHolder = Objects.requireNonNull(getGuiHolder(guiData), "Gui holder must not be null!");
        return guiHolder.createScreen(guiData, mainPanel);
    }

    @SuppressWarnings("unchecked")
    protected IGuiHolder<T> castGuiHolder(Object o) {
        if (!(o instanceof IGuiHolder)) return null;
        try {
            return (IGuiHolder<T>) o;
        } catch (ClassCastException e) {
            return null;
        }
    }
}
