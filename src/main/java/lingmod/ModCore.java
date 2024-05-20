package lingmod;

import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.abstracts.DynamicVariable;
import basemod.eventUtil.AddEventParams;
import basemod.eventUtil.EventUtils;
import basemod.eventUtil.util.Condition;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import lingmod.Events.BeansEvent;
import lingmod.Events.FallingEvent;
import lingmod.Events.Sui12Event;
import lingmod.cards.AbstractEasyCard;
import lingmod.cards.cardvars.AbstractEasyDynamicVariable;
import lingmod.character.Ling;
import lingmod.potions.AbstractEasyPotion;
import lingmod.relics.AbstractEasyRelic;
import lingmod.util.ProAudio;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;

@SuppressWarnings({"unused", "WeakerAccess"})
@SpireInitializer
public class ModCore implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        EditCharactersSubscriber,
        PostInitializeSubscriber,
        AddAudioSubscriber {

    public static final String modID = "lingmod";
    public static final String resourceRoot = modID + "Resources";
    public static final Logger logger = LogManager.getLogger(modID); //Used to output to the console.

    public static String makeID(String idText) {
        return modID + ":" + idText;
    }

    public static Color characterColor = new Color(MathUtils.random(), MathUtils.random(), MathUtils.random(), 1); // This should be changed eventually

    public static final String SHOULDER1 = makeCharacterPath("ling/shoulder.png");
    public static final String SHOULDER2 = makeCharacterPath("ling/shoulder2.png");
    public static final String CORPSE = makeCharacterPath("ling/corpse.png");
    private static final String ATTACK_S_ART = makeImagePath("512/attack.png");
    private static final String SKILL_S_ART = makeImagePath("512/skill.png");
    private static final String POWER_S_ART = makeImagePath("512/power.png");
    private static final String CARD_ENERGY_S = makeImagePath("512/energy.png");
    private static final String TEXT_ENERGY = makeImagePath("512/text_energy.png");
    private static final String ATTACK_ART_L = makeImagePath("1024/attack.png");
    private static final String SKILL_ART_L = makeImagePath("1024/skill.png");
    private static final String POWER_ART_L = makeImagePath("1024/power.png");
    private static final String CARD_ENERGY_L = makeImagePath("1024/energy.png");
    private static final String CHARSELECT_BUTTON = makeImagePath("ui/char_select/button.png");
    private static final String CHARSELECT_PORTRAIT = makeImagePath("ui/char_select/portrait.png");

    public static Settings.GameLanguage[] SupportedLanguages = {
            Settings.GameLanguage.ENG,
    };

    private static String getLangString() {
        for (Settings.GameLanguage lang : SupportedLanguages) {
            if (lang.equals(Settings.language)) {
                return Settings.language.name().toLowerCase();
            }
        }
        return "zhs";
    }

    public ModCore() {
        BaseMod.subscribe(this);

        BaseMod.addColor(Ling.Enums.LING_COLOR, characterColor, characterColor, characterColor,
                characterColor, characterColor, characterColor, characterColor,
                ATTACK_S_ART, SKILL_S_ART, POWER_S_ART, CARD_ENERGY_S,
                ATTACK_ART_L, SKILL_ART_L, POWER_ART_L,
                CARD_ENERGY_L, TEXT_ENERGY);
    }

    public static String makePath(String resourcePath) {
        return modID + "Resources/" + resourcePath;
    }

    public static String makeImagePath(String resourcePath) {
        return modID + "Resources/images/" + resourcePath;
    }

    public static String makeRelicPath(String resourcePath) {
        return modID + "Resources/images/relics/" + resourcePath;
    }

    public static String makePowerPath(String resourcePath) {
        return modID + "Resources/images/powers/" + resourcePath;
    }

    public static String makeCharacterPath(String resourcePath)
    {
        return modID + "Resources/images/char/" + resourcePath;
    }

    public static String makeCardPath(String resourcePath) {
        return modID + "Resources/images/cards/" + resourcePath;
    }
    public static String makeVoicePath(String resourcePath) {
        return modID + "Resources/audio/voice/" + resourcePath; // 语音路径
    }
    public static String makeMusicPath(String resourcePath) {
        return modID + "Resources/audio/music/" + resourcePath; // 背景音乐路径
    }
    public static String makeSFXPath(String resourcePath) {
        return modID + "Resources/audio/sfx/" + resourcePath; // 音效路径
    }


    public static void initialize() {
        ModCore thismod = new ModCore();
    }

    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(new Ling(Ling.characterStrings.NAMES[1], Ling.Enums.PLAYER_LING),
            CHARSELECT_BUTTON, CHARSELECT_PORTRAIT, Ling.Enums.PLAYER_LING);
        
        new AutoAdd(modID)
            .packageFilter(AbstractEasyPotion.class)
            .any(AbstractEasyPotion.class, (info, potion) -> {
                if (potion.pool == null)
                    BaseMod.addPotion(potion.getClass(), potion.liquidColor, potion.hybridColor, potion.spotsColor, potion.ID);
                else
                    BaseMod.addPotion(potion.getClass(), potion.liquidColor, potion.hybridColor, potion.spotsColor, potion.ID, potion.pool);
            });
    }

    @Override
    public void receiveEditRelics() {
        new AutoAdd(modID)
                .packageFilter(AbstractEasyRelic.class)
                .any(AbstractEasyRelic.class, (info, relic) -> {
                    if (relic.color == null) {
                        BaseMod.addRelic(relic, RelicType.SHARED);
                    } else {
                        BaseMod.addRelicToCustomPool(relic, relic.color);
                    }
                    if (!info.seen) {
                        UnlockTracker.markRelicAsSeen(relic.relicId);
                    }
                });
    }

    @Override
    public void receiveEditCards() {
        new AutoAdd(modID)
            .packageFilter(AbstractEasyDynamicVariable.class)
            .any(DynamicVariable.class, (info, var) -> 
                BaseMod.addDynamicVariable(var));
        new AutoAdd(modID)
                .packageFilter(AbstractEasyCard.class)
                .setDefaultSeen(true)
                .cards();
    }

    public static String getStringPathI18N() {
        return modID + "Resources/localization/" + getLangString();
    }
    @Override
    public void receiveEditStrings() {
        BaseMod.loadCustomStringsFile(CardStrings.class, getStringPathI18N() + "/Cardstrings.json");
        BaseMod.loadCustomStringsFile(RelicStrings.class, getStringPathI18N() + "/Relicstrings.json");
        BaseMod.loadCustomStringsFile(CharacterStrings.class, getStringPathI18N() + "/Charstrings.json");
        BaseMod.loadCustomStringsFile(PowerStrings.class, getStringPathI18N() + "/Powerstrings.json");
        BaseMod.loadCustomStringsFile(UIStrings.class, getStringPathI18N() + "/UIstrings.json");
        BaseMod.loadCustomStringsFile(OrbStrings.class, getStringPathI18N() + "/Orbstrings.json");
        BaseMod.loadCustomStringsFile(StanceStrings.class, getStringPathI18N() + "/Stancestrings.json");
        BaseMod.loadCustomStringsFile(PotionStrings.class, getStringPathI18N() + "/Potionstrings.json");
        BaseMod.loadCustomStringsFile(EventStrings.class, getStringPathI18N() + "/Eventstrings.json");

        BaseMod.loadCustomStringsFile(RunModStrings.class,getStringPathI18N() +  "/Modstrings.json");
    }

    @Override
    public void receiveAddAudio() {
        // 添加音效
        for (ProAudio a : ProAudio.values())
            BaseMod.addAudio(makeID(a.name()), makeSFXPath(a.name().toLowerCase() + ".ogg"));
    }

    @Override
    public void receiveEditKeywords() {
        Gson gson = new Gson();
        String json =
                Gdx.files.internal(getStringPathI18N() + "/Keywordstrings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json, com.evacipated.cardcrawl.mod.stslib.Keyword[].class);

        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(modID, keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
            }
        }
    }

    @Override
    public void receivePostInitialize() {
        Condition allPass = new Condition() {
            @Override
            public boolean test() {
                return true;
            }
        };

        BaseMod.addEvent(
                new AddEventParams.Builder(Sui12Event.ID, Sui12Event.class)
                        .eventType(EventUtils.EventType.NORMAL)
                        .create()
        );
        BaseMod.addEvent(
                new AddEventParams.Builder(FallingEvent.ID, FallingEvent.class)
                        .playerClass(Ling.Enums.PLAYER_LING)
                        .overrideEvent(com.megacrit.cardcrawl.events.beyond.Falling.ID)
                        .bonusCondition(allPass)
                        .spawnCondition(allPass)
                        .eventType(EventUtils.EventType.FULL_REPLACE)
                        .create()
        );
        BaseMod.addEvent(
                new AddEventParams.Builder(BeansEvent.ID, BeansEvent.class)
                        .eventType(EventUtils.EventType.NORMAL)
                        .create()
        );
    }
}
