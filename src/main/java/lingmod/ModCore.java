package lingmod;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.localization.RunModStrings;
import com.megacrit.cardcrawl.localization.StanceStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.ReflectionHacks;
import basemod.TopPanelGroup;
import basemod.TopPanelItem;
import basemod.abstracts.DynamicVariable;
import basemod.eventUtil.AddEventParams;
import basemod.eventUtil.EventUtils.EventType;
import basemod.eventUtil.util.Condition;
import basemod.interfaces.AddAudioSubscriber;
import basemod.interfaces.EditCardsSubscriber;
import basemod.interfaces.EditCharactersSubscriber;
import basemod.interfaces.EditKeywordsSubscriber;
import basemod.interfaces.EditRelicsSubscriber;
import basemod.interfaces.EditStringsSubscriber;
import basemod.interfaces.OnStartBattleSubscriber;
import basemod.interfaces.PostDungeonInitializeSubscriber;
import basemod.interfaces.PostInitializeSubscriber;
import basemod.interfaces.StartGameSubscriber;
import basemod.patches.com.megacrit.cardcrawl.helpers.TopPanel.TopPanelHelper;
import lingmod.cards.AbstractEasyCard;
import lingmod.cards.cardvars.AbstractEasyDynamicVariable;
import lingmod.cards.verse.JingYeSiCard;
import lingmod.character.Ling;
import lingmod.events.Beans_Ling;
import lingmod.events.CampfireEventManager;
import lingmod.events.NianGuestStar;
import lingmod.interfaces.CampfireSleepEvent;
import lingmod.monsters.InnManager;
import lingmod.monsters.MonsterGroups;
import lingmod.monsters.MonsterSui_7_Ji;
import lingmod.monsters.MountainPicker;
import lingmod.patch.PlayerFieldsPatch;
import lingmod.potions.AbstractEasyPotion;
import lingmod.relics.AbstractEasyRelic;
import lingmod.ui.VerseTopPanel;
import lingmod.ui.VerseViewScreen;
import lingmod.util.ProAudio;
import lingmod.util.VerseCardManager;
import lingmod.util.VerseLoader;
import lingmod.util.Wiz;

@SuppressWarnings({ "unused", "WeakerAccess" })
@SpireInitializer
public class ModCore implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        EditCharactersSubscriber,
        PostInitializeSubscriber,
        OnStartBattleSubscriber,
        StartGameSubscriber,
        AddAudioSubscriber,
        PostDungeonInitializeSubscriber {

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
                    // if (relic.color == null) {
                    // BaseMod.addRelic(relic, RelicType.SHARED);
                    // } else {
                    BaseMod.addRelicToCustomPool(relic, Ling.Enums.LING_COLOR); // 默认角色专属
                    // }
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
        BaseMod.loadCustomStringsFile(OrbStrings.class, getStringPathI18N() + "/Orbstrings.json");
        BaseMod.loadCustomStringsFile(StanceStrings.class, getStringPathI18N() + "/Stancestrings.json");
        BaseMod.loadCustomStringsFile(PotionStrings.class, getStringPathI18N() + "/Potionstrings.json");
        BaseMod.loadCustomStringsFile(EventStrings.class, getStringPathI18N() + "/Eventstrings.json");
        BaseMod.loadCustomStringsFile(RunModStrings.class, getStringPathI18N() + "/Modstrings.json");
        BaseMod.loadCustomStringsFile(MonsterStrings.class, getStringPathI18N() + "/MonsterStrings.json");
        // 词牌单独放置
        VerseLoader.init();
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
        addEvents();
        addMonster();
        addScreen();
        // 添加TopPanel按钮
        // BaseMod.addTopPanelItem(new VerseTopPanel());
        BaseMod.addSaveField(VerseTopPanel.ID, new VerseTopPanel());
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
        BaseMod.addCustomScreen(new VerseViewScreen());
    }

    @Override
    public void receiveOnBattleStart(AbstractRoom r) {
        VerseCardManager.onBattleStart(r);
    }

    /**
     * 地牢生成后干什么
     */
    @Override
    public void receivePostDungeonInitialize() {
        if (AbstractDungeon.player.chosenClass != Ling.Enums.PLAYER_LING)
            return;
        // 给玩家生成初始词牌：静夜思
        CardGroup verseCards = PlayerFieldsPatch.verseCardGroup.get(Wiz.adp());
        verseCards.addToTop(new JingYeSiCard());
        // 生成事件
        CampfireEventManager.sleptEvents.clear(); // 重置看过的事件
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
        long cnt = topPanelItems.stream().filter(i -> i instanceof VerseTopPanel).count();
        if (cnt <= 0) {
            BaseMod.addTopPanelItem(new VerseTopPanel());
        }
    }

    public void addMonster() {
        // 添加怪物
        BaseMod.addMonster(MonsterSui_7_Ji.ID, MonsterSui_7_Ji.NAME, MonsterSui_7_Ji::new); // 绩老七
        // BaseMod.addMonster(MonsterSui_2_Wang.ID, () -> new MonsterSui_2_Wang()); //
        // 岁老二

        // 挑山人大战行裕掌柜。
        BaseMod.addMonster(makeID("挑山人大战行裕掌柜"), "挑山人大战行裕掌柜", () -> new MonsterGroup(new AbstractMonster[] {
                new MountainPicker(),
                new InnManager()
        }));

        // 和年一起欺夕
        BaseMod.addMonster(NianGuestStar.ID, NianGuestStar.NAME, () -> MonsterGroups.NIAN_GUEST_STAR);
    }

    public enum ResourceType {
        CARDS, CHAR, EVENTS, MASKS, MISC, MONSTERS, POWERS, RELICS, UI
    }
}
