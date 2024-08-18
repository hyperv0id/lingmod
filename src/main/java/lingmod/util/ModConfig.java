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
            Dialect.JAPANESE
    };
    private static final String DIALECT_OPT_KEY = "VOICE.DIALECT";
    private static final String SHOW_CREDIT_KEY = "B_SHOW_CREDIT";
    public static SpireConfig config = null;
    static Properties defaultSetting = new Properties();
    private static ModPanel settingsPanel;

    public static Dialect dialect = Dialect.CN_TOPOLECT;
    public static boolean showCredit = true;

    public static void initModSettings() {
        defaultSetting.setProperty(DIALECT_OPT_KEY, Dialect.CN_TOPOLECT.toString());
        defaultSetting.setProperty(SHOW_CREDIT_KEY, String.valueOf(true));
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void initModConfigMenu() {
        settingsPanel = new ModPanel();
        addCreditMenu(); // 借物表
        addDialectMenu(); // 选择方言
        Texture badge = ImageMaster.loadImage(makeImagePath("badge.png"));
        String modConfDesc = CardCrawlGame.languagePack.getUIString(makeID("Option")).TEXT[0];
        BaseMod.registerModBadge(badge, modID, "jcheng", modConfDesc, settingsPanel);
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
                e.printStackTrace();
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
                e.printStackTrace();
            }
        });
        settingsPanel.addUIElement(btn);
    }
}
