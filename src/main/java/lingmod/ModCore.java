package lingmod;

import basemod.*;
import basemod.abstracts.DynamicVariable;
import basemod.eventUtil.AddEventParams;
import basemod.eventUtil.EventUtils.EventType;
import basemod.eventUtil.util.Condition;
import basemod.interfaces.*;
import basemod.patches.com.megacrit.cardcrawl.helpers.TopPanel.TopPanelHelper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import lingmod.cards.AbstractEasyCard;
import lingmod.cards.cardvars.AbstractEasyDynamicVariable;
import lingmod.cards.poetry.JingYeSiCard;
import lingmod.cards.poetry.PoZhenZiCard;
import lingmod.cards.poetry.QingPingYueCard;
import lingmod.character.Ling;
import lingmod.events.AGiftEvent;
import lingmod.events.Beans_Ling;
import lingmod.events.CampfireEventManager;
import lingmod.events.NianGuestStar;
import lingmod.interfaces.CampfireSleepEvent;
import lingmod.monsters.*;
import lingmod.monsters.family.Sui_9_Nian;
import lingmod.patch.PlayerFieldsPatch;
import lingmod.potions.AbstractEasyPotion;
import lingmod.relics.AbstractEasyRelic;
import lingmod.ui.PoetryTopPanel;
import lingmod.ui.PoetryViewScreen;
import lingmod.ui.SelectedPoetryViewScreen;
import lingmod.util.ModConfig;
import lingmod.util.PoetryLoader;
import lingmod.util.Wiz;
import lingmod.util.audio.ProAudio;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

@SuppressWarnings({"unused", "WeakerAccess"})
@SpireInitializer
public class ModCore implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        EditCharactersSubscriber,
        PostInitializeSubscriber,
        StartGameSubscriber,
        AddAudioSubscriber,
        PostDungeonInitializeSubscriber {

    public static SpireConfig modConfig;
    public static final String modID = "lingmod";
    public static final String resourceRoot = modID + "Resources";
    public static final Logger logger = LogManager.getLogger(modID); // Used to output to the console.
    private static final String ATTACK_S_ART = makeImagePath("512/attack.png");
    private static final String SKILL_S_ART = makeImagePath("512/skill.png");
    // should
    // be
    // changed
    // eventually
    private static final String POWER_S_ART = makeImagePath("512/power.png");
    private static final String CARD_ENERGY_S = makeImagePath("512/energy.png");
    private static final String TEXT_ENERGY = makeImagePath("512/text_energy.png");
    private static final String ATTACK_ART_L = makeImagePath("1024/attack.png");
    private static final String SKILL_ART_L = makeImagePath("1024/skill.png");
    private static final String POWER_ART_L = makeImagePath("1024/power.png");
    private static final String CARD_ENERGY_L = makeImagePath("1024/energy.png");
    private static final String CHARSELECT_BUTTON = makeImagePath("ui/char_select/button.png");
    private static final String CHARSELECT_PORTRAIT = makeImagePath("ui/char_select/portrait.png");
    public static Color characterColor = new Color(MathUtils.random(), MathUtils.random(), MathUtils.random(), 1); // This
    public static Settings.GameLanguage[] SupportedLanguages = {
            Settings.GameLanguage.ENG,
    };

    public ModCore() {
        BaseMod.subscribe(this);

        BaseMod.addColor(Ling.Enums.LING_COLOR, characterColor, characterColor, characterColor,
                characterColor, characterColor, characterColor, characterColor,
                ATTACK_S_ART, SKILL_S_ART, POWER_S_ART, CARD_ENERGY_S,
                ATTACK_ART_L, SKILL_ART_L, POWER_ART_L,
                CARD_ENERGY_L, TEXT_ENERGY);
        BaseMod.addColor(Ling.Enums.LING_POEM_COLOR, characterColor, characterColor, characterColor,
                characterColor, characterColor, characterColor, characterColor,
                ATTACK_S_ART, SKILL_S_ART, POWER_S_ART, CARD_ENERGY_S,
                ATTACK_ART_L, SKILL_ART_L, POWER_ART_L,
                CARD_ENERGY_L, TEXT_ENERGY);

        ModConfig.initModSettings();
    }

    public static String makeID(String idText) {
        return modID + ":" + idText;
    }

    private static String getLangString() {
        // for (Settings.GameLanguage lang : SupportedLanguages) {
        // if (lang.equals(Settings.language)) {
        // return Settings.language.name().toLowerCase();
        // }
        // }
        return "zhs";
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

    public static String makeCharacterPath(String resourcePath) {
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

    public static String getStringPathI18N() {
        return modID + "Resources/localization/" + getLangString();
    }

    public static String makeImagePath(String path, ResourceType type) {
        path = type.toString().toLowerCase() + "/" + path;
        return makeImagePath(path);
    }

    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(new Ling(Ling.characterStrings.NAMES[1], Ling.Enums.PLAYER_LING),
                CHARSELECT_BUTTON, CHARSELECT_PORTRAIT, Ling.Enums.PLAYER_LING);

        new AutoAdd(modID)
                .packageFilter(AbstractEasyPotion.class)
                .any(AbstractEasyPotion.class, (info, potion) -> {
                    if (potion.pool == null)
                        BaseMod.addPotion(potion.getClass(), potion.liquidColor, potion.hybridColor, potion.spotsColor,
                                potion.ID);
                    else
                        BaseMod.addPotion(potion.getClass(), potion.liquidColor, potion.hybridColor, potion.spotsColor,
                                potion.ID, potion.pool);
                });
    }

    @Override
    public void receiveEditRelics() {
        new AutoAdd(modID)
                .packageFilter(AbstractEasyRelic.class)
                .any(AbstractEasyRelic.class, (info, relic) -> {
                    BaseMod.addRelicToCustomPool(relic, Ling.Enums.LING_COLOR); // 默认角色专属
                    if (!info.seen) {
                        UnlockTracker.markRelicAsSeen(relic.relicId);
                    }
                });
    }

    @Override
    public void receiveEditCards() {
        new AutoAdd(modID)
                .packageFilter(AbstractEasyDynamicVariable.class)
                .any(DynamicVariable.class, (info, var) -> BaseMod.addDynamicVariable(var));
        new AutoAdd(modID)
                .packageFilter(AbstractEasyCard.class)
                .setDefaultSeen(true)
                .cards();
    }

    @Override
    public void receiveEditStrings() {
        BaseMod.loadCustomStringsFile(CardStrings.class, getStringPathI18N() + "/Cardstrings.json");
        BaseMod.loadCustomStringsFile(RelicStrings.class, getStringPathI18N() + "/Relicstrings.json");
        BaseMod.loadCustomStringsFile(CharacterStrings.class, getStringPathI18N() + "/Charstrings.json");
        BaseMod.loadCustomStringsFile(PowerStrings.class, getStringPathI18N() + "/Powerstrings.json");
        BaseMod.loadCustomStringsFile(UIStrings.class, getStringPathI18N() + "/UIstrings.json");
        BaseMod.loadCustomStringsFile(StanceStrings.class, getStringPathI18N() + "/Stancestrings.json");
        BaseMod.loadCustomStringsFile(PotionStrings.class, getStringPathI18N() + "/Potionstrings.json");
        BaseMod.loadCustomStringsFile(EventStrings.class, getStringPathI18N() + "/Eventstrings.json");
        BaseMod.loadCustomStringsFile(RunModStrings.class, getStringPathI18N() + "/Modstrings.json");
        BaseMod.loadCustomStringsFile(MonsterStrings.class, getStringPathI18N() + "/MonsterStrings.json");
        // 诗词赋曲单独放置
        PoetryLoader.init();
    }

    /**
     * 添加音效
     */
    @Override
    public void receiveAddAudio() {
        // 1. 添加语音
        for (ProAudio p : ProAudio.getAllVoices()) {
            BaseMod.addAudio(p.key(), makeVoicePath(p.toString()));
        }
    }

    @Override
    public void receiveEditKeywords() {
        Gson gson = new Gson();
        String json = Gdx.files.internal(getStringPathI18N() + "/Keywordstrings.json")
                .readString(String.valueOf(StandardCharsets.UTF_8));
        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json,
                com.evacipated.cardcrawl.mod.stslib.Keyword[].class);

        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(modID, keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
            }
        }
    }

    @Override
    public void receivePostInitialize() {
        ModConfig.initModConfigMenu();
        addEvents();
        addMonster();
        addScreen();
        // 添加TopPanel按钮
        // BaseMod.addTopPanelItem(new PoetryTopPanel());
        BaseMod.addSaveField(PoetryTopPanel.ID, new PoetryTopPanel());
        BaseMod.addSaveField(CampfireEventManager.class.getName(), new CampfireEventManager());
    }


    public void addEvents() {
        Condition falseCondition = () -> false;
        new AutoAdd(modID).packageFilter(Beans_Ling.class).any(AbstractImageEvent.class, (info, event) -> {
            String eid = makeID(event.getClass().getSimpleName());
            if (event.getClass().getAnnotation(CampfireSleepEvent.class) == null) {
                BaseMod.addEvent(new AddEventParams.Builder(eid, event.getClass())
                        .eventType(EventType.NORMAL)
                        .create());
            } else {
                BaseMod.addEvent(new AddEventParams.Builder(eid, event.getClass())
                        .eventType(EventType.NORMAL)
                        .spawnCondition(falseCondition)
                        .create());
            }
        });
    }

    public void addScreen() {
        BaseMod.addCustomScreen(new PoetryViewScreen());
        BaseMod.addCustomScreen(new SelectedPoetryViewScreen());
    }

    /**
     * 地牢生成后干什么
     */
    @Override
    public void receivePostDungeonInitialize() {
        if (AbstractDungeon.player.chosenClass != Ling.Enums.PLAYER_LING)
            return;
        // 给玩家生成初始诗词赋曲：静夜思
        CardGroup poetryCards = PlayerFieldsPatch.poetryCardGroup.get(Wiz.adp());
        poetryCards.addToTop(new JingYeSiCard());
        poetryCards.addToTop(new QingPingYueCard());
        poetryCards.addToTop(new PoZhenZiCard());
        // 生成事件
        CampfireEventManager.sleepEvents.clear(); // 清空
        CampfireEventManager.initSleepEvents();
    }

    /**
     *
     */
    @Override
    public void receiveStartGame() {
        ArrayList<TopPanelItem> topPanelItems = ReflectionHacks.getPrivate(TopPanelHelper.topPanelGroup,
                TopPanelGroup.class, "topPanelItems");
        long cnt = topPanelItems.stream().filter(i -> i instanceof PoetryTopPanel).count();
        if (cnt <= 0) {
            BaseMod.addTopPanelItem(new PoetryTopPanel());
        }
    }

    public void addMonster() {
        // 添加怪物
        BaseMod.addMonster(MonsterSui_7_Ji.ID, MonsterSui_7_Ji.NAME, MonsterSui_7_Ji::new); // 绩老七
        // BaseMod.addMonster(MonsterSui_2_Wang.ID, () -> new MonsterSui_2_Wang()); //
        // 岁老二
        BaseMod.addMonster(Wang_MountainGhost.ID, Wang_MountainGhost::new);
        // 岁老七
        BaseMod.addMonster(MonsterSui_7_Ji.ID, MonsterSui_7_Ji::new);

        // 挑山人大战行裕掌柜。
        BaseMod.addMonster(AGiftEvent.ID, AGiftEvent.NAME, () -> new MonsterGroup(new AbstractMonster[]{
                new MountainPicker(),
                new InnManager()
        }));
        // 和年一起欺夕
        BaseMod.addMonster(NianGuestStar.ID, NianGuestStar.NAME, () -> MonsterGroups.NIAN_GUEST_STAR);
        // 召唤物
//        BaseMod.addMonster(Thunderer_SummonMonster.ID, Thunderer_SummonMonster::new);
//        BaseMod.addMonster(Tranquility_SummonMonster.ID, Tranquility_SummonMonster::new);
//        BaseMod.addMonster(Peripateticism_SummonMonster.ID, Peripateticism_SummonMonster::new);
        // 岁家的家庭战争
        BaseMod.addMonster(Sui_9_Nian.ID, Sui_9_Nian::new);
    }

    public enum ResourceType {
        CARDS, CHAR, EVENTS, MASKS, MISC, MONSTERS, POWERS, RELICS, UI
    }
}
