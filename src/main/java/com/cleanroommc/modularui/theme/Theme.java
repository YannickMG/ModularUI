package com.cleanroommc.modularui.theme;

import com.cleanroommc.modularui.ModularUIConfig;
import com.cleanroommc.modularui.api.ITheme;
import com.cleanroommc.modularui.api.IThemeApi;
import com.cleanroommc.modularui.screen.RichTooltip;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import java.util.Map;

public class Theme implements ITheme {

    public static final String FALLBACK = IThemeApi.FALLBACK;
    public static final String PANEL = IThemeApi.PANEL;
    public static final String BUTTON = IThemeApi.BUTTON;
    public static final String ITEM_SLOT = IThemeApi.ITEM_SLOT;
    public static final String FLUID_SLOT = IThemeApi.FLUID_SLOT;
    public static final String TEXT_FIELD = IThemeApi.TEXT_FIELD;
    public static final String TOGGLE_BUTTON = IThemeApi.TOGGLE_BUTTON;

    private final Map<String, WidgetTheme> widgetThemes = new Object2ObjectOpenHashMap<>();

    private final String id;
    private final ITheme parentTheme;
    private final WidgetTheme fallback;
    private final WidgetTheme panelTheme;
    private final WidgetTheme buttonTheme;
    private final WidgetSlotTheme itemSlotTheme;
    private final WidgetSlotTheme fluidSlotTheme;
    private final WidgetTextFieldTheme textFieldTheme;
    private final WidgetThemeSelectable toggleButtonTheme;

    private int openCloseAnimationOverride = -1;
    private Boolean smoothProgressBarOverride = null;
    private RichTooltip.Pos tooltipPosOverride = null;

    Theme(String id, ITheme parent, Map<String, WidgetTheme> widgetThemes) {
        this.id = id;
        this.parentTheme = parent;
        this.widgetThemes.putAll(widgetThemes);
        if (parent instanceof Theme theme) {
            for (Map.Entry<String, WidgetTheme> entry : theme.widgetThemes.entrySet()) {
                if (!this.widgetThemes.containsKey(entry.getKey())) {
                    this.widgetThemes.put(entry.getKey(), entry.getValue());
                }
            }
        } else if (parent == IThemeApi.get().getDefaultTheme()) {
            if (!this.widgetThemes.containsKey(FALLBACK)) {
                this.widgetThemes.put(FALLBACK, ThemeManager.defaultdefaultWidgetTheme);
            }
            for (Map.Entry<String, WidgetTheme> entry : ThemeAPI.INSTANCE.defaultWidgetThemes.entrySet()) {
                if (!this.widgetThemes.containsKey(entry.getKey())) {
                    this.widgetThemes.put(entry.getKey(), entry.getValue());
                }
            }
        }
        this.panelTheme = this.widgetThemes.get(PANEL);
        this.fallback = this.widgetThemes.get(FALLBACK);
        this.buttonTheme = this.widgetThemes.get(BUTTON);
        this.itemSlotTheme = (WidgetSlotTheme) this.widgetThemes.get(ITEM_SLOT);
        this.fluidSlotTheme = (WidgetSlotTheme) this.widgetThemes.get(FLUID_SLOT);
        this.textFieldTheme = (WidgetTextFieldTheme) this.widgetThemes.get(TEXT_FIELD);
        this.toggleButtonTheme = (WidgetThemeSelectable) this.widgetThemes.get(TOGGLE_BUTTON);
    }

    void setOpenCloseAnimationOverride(int override) {
        this.openCloseAnimationOverride = override;
    }

    void setSmoothProgressBarOverride(boolean smooth) {
        this.smoothProgressBarOverride = smooth;
    }

    void setTooltipPosOverride(RichTooltip.Pos pos) {
        this.tooltipPosOverride = pos;
    }

    public String getId() {
        return this.id;
    }

    public ITheme getParentTheme() {
        return this.parentTheme;
    }

    public WidgetTheme getFallback() {
        return this.fallback;
    }

    public WidgetTheme getPanelTheme() {
        return this.panelTheme;
    }

    public WidgetTheme getButtonTheme() {
        return this.buttonTheme;
    }

    @Override
    public WidgetSlotTheme getItemSlotTheme() {
        return this.itemSlotTheme;
    }

    @Override
    public WidgetSlotTheme getFluidSlotTheme() {
        return this.fluidSlotTheme;
    }

    public WidgetTextFieldTheme getTextFieldTheme() {
        return this.textFieldTheme;
    }

    @Override
    public WidgetThemeSelectable getToggleButtonTheme() {
        return this.toggleButtonTheme;
    }

    public WidgetTheme getWidgetTheme(String id) {
        if (this.widgetThemes.containsKey(id)) {
            return this.widgetThemes.get(id);
        }
        return getFallback();
    }

    @Override
    public boolean getSmoothProgressBarOverride() {
        if (this.smoothProgressBarOverride != null) {
            return this.smoothProgressBarOverride;
        }
        return ModularUIConfig.smoothProgressBar;
    }

    @Override
    public RichTooltip.Pos getTooltipPosOverride() {
        if (this.tooltipPosOverride != null) {
            return this.tooltipPosOverride;
        }
        return ModularUIConfig.tooltipPos;
    }
}
