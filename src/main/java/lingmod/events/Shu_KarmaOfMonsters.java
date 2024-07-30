package lingmod.events;

import static lingmod.ModCore.makeImagePath;

import java.util.ArrayList;

import org.apache.logging.log4j.Logger;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Circlet;
import com.megacrit.cardcrawl.relics.Ginger;
import com.megacrit.cardcrawl.relics.Mango;
import com.megacrit.cardcrawl.relics.Pear;
import com.megacrit.cardcrawl.relics.Strawberry;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import basemod.ReflectionHacks;
import basemod.abstracts.events.PhasedEvent;
import basemod.abstracts.events.phases.TextPhase;
import lingmod.ModCore;
import lingmod.ModCore.ResourceType;

/**
 * 黍预见后面的怪物，假设只在火堆中触发，不会有弱怪池
 * TODO: BOSS房前、4层特判
 */
public class Shu_KarmaOfMonsters extends PhasedEvent {

    public static final String ID = ModCore.makeID(Shu_KarmaOfMonsters.class.getSimpleName());
    public static final String IMG_PATH = ModCore.makeImagePath("events/Sui12Event.png");
    public static final Logger logger = ModCore.logger;
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;

    public String phase = "START";
    public AbstractRelic fruit;
    public String monsters;
    public String elites;

    public static final int SMALL_AMT = 5;
    public static final int ELITE_AMT = 3;

    public Shu_KarmaOfMonsters() {
        super(ID, eventStrings.NAME, makeImagePath("Shu_KarmaOfMonsters", ResourceType.EVENTS));
        noCardsInRewards = true; // 没有卡牌奖励
        calcFruit();
        calcStrongEnemy();
        calcElite();
        OPTIONS[3] = String.format(OPTIONS[3], SMALL_AMT);
        OPTIONS[4] = String.format(OPTIONS[4], ELITE_AMT);
        // 开始
        registerPhase("START", new TextPhase(DESCRIPTIONS[0])
                .addOption(OPTIONS[1], (i) -> transitionKey("LOOK"))
                .addOption(OPTIONS[2], (i) -> transitionKey("FRUIT")));
        // 看手相
        registerPhase("LOOK", new TextPhase(DESCRIPTIONS[1])
                .addOption(OPTIONS[3], (i) -> transitionKey("MONSTER"))
                .addOption(OPTIONS[4], (i) -> transitionKey("ELITE")));
        registerPhase("MONSTER", new TextPhase(DESCRIPTIONS[2] + monsters)
                .addOption(OPTIONS[5], (i) -> transitionKey("FRUIT")));
        registerPhase("ELITE", new TextPhase(DESCRIPTIONS[3] + elites)
                .addOption(OPTIONS[5], (i) -> transitionKey("FRUIT")));

        // 获得水果
        registerPhase("FRUIT", new TextPhase(DESCRIPTIONS[4] + fruit.name)
                .addOption(OPTIONS[0], (i) -> openMap())
                .addOption(OPTIONS[6] + fruit.name, fruit, (i) -> {
                    AbstractDungeon.getCurrRoom().rewards.clear();
                    AbstractDungeon.getCurrRoom().addRelicToRewards(fruit);
                    AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
                    AbstractDungeon.combatRewardScreen.open();
                    transitionKey("EXIT");
                }));
        // 离开
        registerPhase("EXIT", new TextPhase(DESCRIPTIONS[5])
                .addOption(OPTIONS[0], (i) -> openMap()));

        transitionKey("START");
    }

    protected void calcStrongEnemy() {
        monsters = "";
        if (AbstractDungeon.monsterList.size() < SMALL_AMT) {
            ReflectionHacks.privateMethod(AbstractDungeon.class, "generateStrongEnemies", Integer.class)
                    .invoke(CardCrawlGame.dungeon, 12);
        }
        for (int index = 0; index < SMALL_AMT; index++) {
            String name = AbstractDungeon.monsterList.get(index);
            String name_i18n = MonsterHelper.getEncounterName(name);
            if (!name_i18n.isEmpty()) {
                monsters += " NL NL " + name_i18n;
            }
            // logger.info("接下来的强怪将是：" + name);
        }

    }

    protected void calcElite() {
        elites = "";
        if (AbstractDungeon.monsterList.size() < ELITE_AMT) {
            ReflectionHacks.privateMethod(AbstractDungeon.class, "generateElites", Integer.class)
                    .invoke(CardCrawlGame.dungeon, 10);
        }
        for (int index = 0; index < SMALL_AMT; index++) {
            String name = AbstractDungeon.eliteMonsterList.get(index);
            String name_i18n = MonsterHelper.getEncounterName(name);
            if (!name_i18n.isEmpty()) {
                elites += " NL NL " + name_i18n;
            }
            // logger.info("接下来的精英将是：" + name);
        }
    }

    protected void calcFruit() {
        ArrayList<AbstractRelic> relics = new ArrayList<>();
        if (!AbstractDungeon.player.hasRelic(Strawberry.ID)) {
            relics.add(new Strawberry());
        }
        if (!AbstractDungeon.player.hasRelic(Pear.ID)) {
            relics.add(new Pear());
        }
        if (!AbstractDungeon.player.hasRelic(Mango.ID)) {
            relics.add(new Mango());
        }
        if (!AbstractDungeon.player.hasRelic(Ginger.ID)) {
            relics.add(new Ginger());
        }
        // 不能添加水果类遗物
        if (relics.isEmpty())
            relics.add(new Circlet());
        int idx = AbstractDungeon.miscRng.random(relics.size() - 1);
        fruit = relics.get(idx);
    }

}
