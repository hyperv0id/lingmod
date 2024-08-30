package lingmod.util;

import static lingmod.ModCore.logger;
import static lingmod.ModCore.makeID;
import static lingmod.ModCore.makeImagePath;
import static lingmod.ModCore.modID;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.UIStrings;

import basemod.BaseMod;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import lingmod.ui.ModLabeledDropdown;
import lingmod.util.audio.Dialect;

public class ModConfig {
    private static final String DIALECT_OPT_KEY = makeID("VOICE.DIALECT");
    private static final String SKIN_OPT_KEY = makeID("CONF.CHAR_SKIN");
    private static final String SHOW_CREDIT_KEY = makeID("B_SHOW_CREDIT");
    private static final String STATIC_CHAR_KEY = makeID("CONF.STATIC_CHAR");
    public static SpireConfig config = null;
    static Properties defaultSetting = new Properties();
    private static ModPanel settingsPanel;

    public static Dialect dialect = Dialect.CN_TOPOLECT;
    public static SkinInfo skinInfo = SkinInfo.NCG;
    public static boolean showCredit = true;
    public static boolean useStaticCharImg = false;

    public static void initModSettings() {
        defaultSetting.setProperty(DIALECT_OPT_KEY, Dialect.CN_TOPOLECT.toString());
        defaultSetting.setProperty(SKIN_OPT_KEY, SkinInfo.NCG.toString());
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
            // 2. skin option
            loadSkinInfo();
            // 3. show credit
            showCredit = config.getBool(SHOW_CREDIT_KEY);
            // 4. use static char img
            useStaticCharImg = config.getBool(STATIC_CHAR_KEY);
        } catch (Exception e) {
            logger.error("Init Config Failed" + e.getLocalizedMessage());
        }
    }

    public static void initModConfigMenu() {
        settingsPanel = new ModPanel();
        addDialectMenu(); // 选择方言
        addSkinMenu(); // 选择皮肤
        addCreditMenu(); // 借物表
        addStaticCharMenu(); // 静态图
        Texture badge = ImageMaster.loadImage(makeImagePath("badge.png"));
        String modConfDesc = CardCrawlGame.languagePack.getUIString(makeID("Option")).TEXT[0];
        BaseMod.registerModBadge(badge, modID, "jcheng", modConfDesc, settingsPanel);
    }

    private static void addSkinMenu() {
        SkinInfo[] info = SkinInfo.values();
        UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(SKIN_OPT_KEY);
        ModLabeledDropdown skinSelection = new ModLabeledDropdown(uiStrings.TEXT[0], null, 650.0F, 500.0F,
                Settings.CREAM_COLOR, FontHelper.charDescFont, settingsPanel, new ArrayList<>(Arrays.asList(uiStrings.EXTRA_TEXT)),
                (modLabel) -> {
                }, (dropdownMenu) -> {
        }, (i, s) -> {
            skinInfo = info[i];
            saveSkinInfo();
        });
        int idx = 0;
        for (int i = 0; i < info.length; i++) {
            SkinInfo s = info[i];
            if (s.toString().equals(skinInfo.toString())) idx = i;
        }
        skinSelection.dropdownMenu.setSelectedIndex(idx);

        settingsPanel.addUIElement(skinSelection);
    }

    public static void loadSkinInfo() {
        String skinOpt = config.getString(SKIN_OPT_KEY);
        if (skinOpt != null) {
            for (SkinInfo i : SkinInfo.values()) {
                if (i.toString().equals(skinOpt)) {
                    skinInfo = i;
                    break;
                }
            }
        }
        if (skinInfo == null) skinInfo = SkinInfo.NCG;
    }

    public static void saveSkinInfo() {
        config.setString(SKIN_OPT_KEY, skinInfo.toString());
        try {
            config.save();
        } catch (IOException e) {
            logger.warn("load config skinInfo failed" + e.getLocalizedMessage());
        }
    }

    private static void addStaticCharMenu() {
        UIStrings uis = CardCrawlGame.languagePack.getUIString(STATIC_CHAR_KEY);
        ModLabeledToggleButton btn = new ModLabeledToggleButton(uis.TEXT[0], 350F, 600, Settings.CREAM_COLOR,
                FontHelper.charDescFont, useStaticCharImg, settingsPanel, modLabel -> {
        }, modToggleButton -> {
            useStaticCharImg = modToggleButton.enabled;
            config.setString(STATIC_CHAR_KEY, String.valueOf(useStaticCharImg));
            try {
                config.save();
            } catch (IOException e) {
                logger.warn("save config credit failed" + e.getLocalizedMessage());
            }
        });
        settingsPanel.addUIElement(btn);
    }

    public static void addDialectMenu() {
        Dialect[] dialects = Dialect.values();
        UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(DIALECT_OPT_KEY);
        ModLabeledDropdown voiceSelection = new ModLabeledDropdown(uiStrings.TEXT[0], null, 350.0F, 500.0F,
                Settings.CREAM_COLOR, FontHelper.charDescFont, settingsPanel, new ArrayList<>(Arrays.asList(uiStrings.EXTRA_TEXT)),
                (modLabel) -> {
                }, (dropdownMenu) -> {
        }, (i, s) -> {
            dialect = dialects[i];
            config.setString(DIALECT_OPT_KEY, dialect.toString());
            try {
                config.save();
            } catch (IOException e) {
                logger.warn("load config dialect failed" + e.getLocalizedMessage());
            }
        });
        int idx = 0;
        for (int i = 0; i < dialects.length; i++) {
            Dialect d = dialects[i];
            if (d.toString().equals(dialect.toString())) idx = i;
        }
        voiceSelection.dropdownMenu.setSelectedIndex(idx);

        settingsPanel.addUIElement(voiceSelection);
    }

    public static void addCreditMenu() {
        UIStrings uis = CardCrawlGame.languagePack.getUIString(SHOW_CREDIT_KEY);
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
