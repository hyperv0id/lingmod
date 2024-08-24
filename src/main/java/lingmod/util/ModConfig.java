package lingmod.util;

import basemod.BaseMod;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.UIStrings;
import lingmod.ui.ModLabeledDropdown;
import lingmod.util.audio.Dialect;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

import static lingmod.ModCore.*;

public class ModConfig {
    private static final Dialect[] DIALECT_LIST = new Dialect[]{
            Dialect.CN,
            Dialect.CN_TOPOLECT,
            Dialect.ENGLISH,
            Dialect.JAPANESE,
            Dialect.NONE
    };
    private static final String DIALECT_OPT_KEY = "VOICE.DIALECT";
    private static final String SHOW_CREDIT_KEY = "B_SHOW_CREDIT";
    private static final String STATIC_CHAR_KEY = "CONF.STATIC_CHAR";
    public static SpireConfig config = null;
    static Properties defaultSetting = new Properties();
    private static ModPanel settingsPanel;

    public static Dialect dialect = Dialect.CN_TOPOLECT;
    public static boolean showCredit = true;
    public static boolean useStaticCharImg = false;

    public static void initModSettings() {
        defaultSetting.setProperty(DIALECT_OPT_KEY, Dialect.CN_TOPOLECT.toString());
        defaultSetting.setProperty(SHOW_CREDIT_KEY, String.valueOf(true));
        defaultSetting.setProperty(STATIC_CHAR_KEY, String.valueOf(false));
        try {
            config = new SpireConfig(modID, modID, defaultSetting);
            config.load();
            // 1. dialect
            String dialectOption = config.getString(DIALECT_OPT_KEY);
            if (dialectOption != null) {
                for (Dialect d : Dialect.values()) {
                    if (d.toString().equals(dialectOption)) {
                        dialect = d;
                        break;
                    }
                }
            }
            if (dialect == null) dialect = Dialect.CN_TOPOLECT;
            // 2. show credit
            showCredit = config.getBool(SHOW_CREDIT_KEY);
            // 3. use static char img
            useStaticCharImg = config.getBool(STATIC_CHAR_KEY);
        } catch (Exception e) {
            logger.error("Init Config Failed" + e.getLocalizedMessage());
        }
    }

    public static void initModConfigMenu() {
        settingsPanel = new ModPanel();
        addCreditMenu(); // 借物表
        addDialectMenu(); // 选择方言
        addStatCharMenu(); // 静态图
        Texture badge = ImageMaster.loadImage(makeImagePath("badge.png"));
        String modConfDesc = CardCrawlGame.languagePack.getUIString(makeID("Option")).TEXT[0];
        BaseMod.registerModBadge(badge, modID, "jcheng", modConfDesc, settingsPanel);
    }

    private static void addStatCharMenu() {
        UIStrings uis = CardCrawlGame.languagePack.getUIString(makeID(STATIC_CHAR_KEY));
        ModLabeledToggleButton btn = new ModLabeledToggleButton(uis.TEXT[0], 350F, 600, Settings.CREAM_COLOR,
                FontHelper.charDescFont, useStaticCharImg, settingsPanel, modLabel -> {
        }, modToggleButton -> {
            useStaticCharImg = modToggleButton.enabled;
            config.setString(STATIC_CHAR_KEY, String.valueOf(useStaticCharImg));
            try {
                config.save();
            } catch (IOException e) {
                logger.warn("load config credit failed" + e.getLocalizedMessage());
            }
        });
        settingsPanel.addUIElement(btn);
    }

    public static void addDialectMenu() {
        UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(makeID(DIALECT_OPT_KEY));
        ArrayList<String> options = new ArrayList<>(Arrays.asList(uiStrings.EXTRA_TEXT));
        ModLabeledDropdown voiceSelection = new ModLabeledDropdown(uiStrings.TEXT[0], null, 350.0F, 500.0F,
                Settings.CREAM_COLOR, FontHelper.charDescFont, settingsPanel, options,
                (modLabel) -> {
                }, (dropdownMenu) -> {
        }, (i, s) -> {
            dialect = DIALECT_LIST[i];
            config.setString(DIALECT_OPT_KEY, dialect.toString());
            try {
                config.save();
            } catch (IOException e) {
                logger.warn("load config dialect failed" + e.getLocalizedMessage());
            }
        });
        int idx = 0;
        for (int i = 0; i < DIALECT_LIST.length; i++) {
            Dialect d = DIALECT_LIST[i];
            if (d.toString().equals(dialect.toString())) idx = i;
        }
        voiceSelection.dropdownMenu.setSelectedIndex(idx);

        settingsPanel.addUIElement(voiceSelection);
    }

    public static void addCreditMenu() {
        UIStrings uis = CardCrawlGame.languagePack.getUIString(makeID(SHOW_CREDIT_KEY));
        ModLabeledToggleButton btn = new ModLabeledToggleButton(uis.TEXT[0], 350F, 750F, Settings.CREAM_COLOR,
                FontHelper.charDescFont, showCredit, settingsPanel, modLabel -> {
        }, modToggleButton -> {
            showCredit = modToggleButton.enabled;
            config.setString(SHOW_CREDIT_KEY, String.valueOf(showCredit));
            try {
                config.save();
            } catch (IOException e) {
                logger.warn("load config credit failed" + e.getLocalizedMessage());
            }
        });
        settingsPanel.addUIElement(btn);
    }
}
