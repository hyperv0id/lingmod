package lingmod.events;

import static lingmod.ModCore.logger;
import static lingmod.ModCore.makeID;
import static lingmod.ModCore.makeImagePath;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.potions.PotionSlot;
import com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier;

import basemod.abstracts.events.PhasedEvent;
import basemod.abstracts.events.phases.CombatPhase;
import basemod.abstracts.events.phases.TextPhase;
import lingmod.cards.AbstractAriaCard;
import lingmod.cards.aria.JingYeSiCard;
import lingmod.potions.ForgetPotion;
import lingmod.util.AiraReward;

/**
 * 本子情节，但是永远潇洒的令姐
 */
public class DoujinshiPlot extends PhasedEvent {

    public static final String ID = makeID(DoujinshiPlot.class.getSimpleName());
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private static final String title = eventStrings.NAME;

    public static DoujinshiPlot __inst;

    AbstractAriaCard battleReward;

    public DoujinshiPlot() {
        super(ID, title, makeImagePath("events/DoujinshiPlot_0.png"));
        NAME = eventStrings.NAME;
        super.title = title;
        __inst = this;
        // 售卖药水
        registerPhase(Phases.SALE,
                new TextPhase(DESCRIPTIONS[0])
                        .addOption(OPTIONS[8], (i) -> exit()) // 离开
                        .addOption(OPTIONS[1], (i) -> {
                            // 购买药水
                            if (AbstractDungeon.player.potions.stream()
                                    .anyMatch(potion -> potion instanceof PotionSlot)) {
                                AbstractDungeon.player.obtainPotion(new ForgetPotion());
                                transitionKey(Phases.DRINK);
                            } else {
                                logger.info("No Potion Slot To Use");
                            }
                        })
                        .addOption(OPTIONS[10], (i) -> {
                            transitionKey(Phases.DOUJINSHI);
                            imageEventText.loadImage(makeImagePath("events/DoujinshiPlot_1.png"));
                        }));
        // 怂恿喝下药水
        registerPhase(Phases.DRINK, new TextPhase(DESCRIPTIONS[1])
                .addOption(OPTIONS[9], (i) -> {
                    exit();
                }));
        // 喝下药水，暂时失去权能
        registerPhase(Phases.DOUJINSHI, new TextPhase(DESCRIPTIONS[2])
                // [即兴吟诗] 获得 #g《赤壁赋》
                .addOption(OPTIONS[3], (i) -> {
                    battleReward = new JingYeSiCard();
                    battleReward.name = "《赤壁赋》";
                    transitionKey(Phases.REPLY_1);
                })
                // [喝酒壮胆] 获得 #g《定风波》
                .addOption(OPTIONS[4], (i) -> {
                    battleReward = new JingYeSiCard();
                    battleReward.name = "《定风波》";
                    transitionKey(Phases.REPLY_2);
                })
                // [握紧佩剑] 获得 #g《剑客》
                .addOption(OPTIONS[5], (i) -> {
                    battleReward = new JingYeSiCard();
                    battleReward.name = "《剑客》";
                    transitionKey(Phases.REPLY_3);
                }));
        // 进入战斗的对话
        registerPhase(Phases.REPLY_1,
                new TextPhase(DESCRIPTIONS[4]).addOption(OPTIONS[6], (i) -> transitionKey(Phases.BATTLE)));
        registerPhase(Phases.REPLY_2,
                new TextPhase(DESCRIPTIONS[5]).addOption(OPTIONS[6], (i) -> transitionKey(Phases.BATTLE)));
        registerPhase(Phases.REPLY_3,
                new TextPhase(DESCRIPTIONS[6]).addOption(OPTIONS[6], (i) -> transitionKey(Phases.BATTLE)));
        // 进入战斗
        registerPhase(Phases.BATTLE, new CombatPhase(MonsterHelper.BLUE_SLAVER_ENC).addRewards(true, (room) -> {
            room.rewards.add(new AiraReward(battleReward));
            room.addRelicToRewards(RelicTier.COMMON);
            logger.info(battleReward.name);
        }));

        // 进入事件
        transitionKey(Phases.SALE);
    }

    public void exit() {
        openMap();
    }

    public static enum Phases {
        SALE, DRINK, DOUJINSHI, PRE_BATTLE, BATTLE, REPLY_1, REPLY_2, REPLY_3
    }
}
