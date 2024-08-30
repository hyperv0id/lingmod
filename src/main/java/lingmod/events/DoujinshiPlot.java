package lingmod.events;

import static lingmod.ModCore.logger;
import static lingmod.ModCore.makeID;
import static lingmod.ModCore.makeImagePath;

import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.PotionSlot;
import com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier;

import basemod.abstracts.events.PhasedEvent;
import basemod.abstracts.events.phases.CombatPhase;
import basemod.abstracts.events.phases.TextPhase;
import lingmod.cards.poetry.ChiBiFuCard;
import lingmod.cards.poetry.DingFengBoCard;
import lingmod.cards.poetry.JianKeCard;
import lingmod.patch.PlayerFieldsPatch;
import lingmod.potions.ForgetPotion;
import lingmod.util.PoetryReward;
import lingmod.util.Wiz;

/**
 * 本子情节，但是永远潇洒的令姐
 */
public class DoujinshiPlot extends PhasedEvent {

    public static final String ID = makeID(DoujinshiPlot.class.getSimpleName());
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;

    public static DoujinshiPlot __inst;

    public static String NAME = eventStrings.NAME;

    public DoujinshiPlot() {
        super(ID, eventStrings.NAME, makeImagePath("events/DoujinshiPlot_0.png"));
        NAME = eventStrings.NAME;
        body = DESCRIPTIONS[0];
        __inst = this;
    }

    @Override
    public void onEnterRoom() {
        super.onEnterRoom();
        CardGroup cg = PlayerFieldsPatch.poetryCardGroup.get(Wiz.adp());
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
                        }));
        // 怂恿喝下药水
        registerPhase(Phases.DRINK, new TextPhase(DESCRIPTIONS[1])
                .addOption(OPTIONS[9], (i) -> exit())
                .addOption(OPTIONS[10], (i) -> {
                    AbstractPotion potion = Wiz.adp().potions.stream().filter(pot -> pot instanceof ForgetPotion).findFirst().orElse(null);
                    int idx = Wiz.adp().potions.indexOf(potion);
                    Wiz.adp().potions.set(idx, new PotionSlot(idx));
                    transitionKey(Phases.DOUJINSHI);
                    imageEventText.loadImage(makeImagePath("events/DoujinshiPlot_1.png"));
                }));
        // 喝下药水，暂时失去权能
        registerPhase(Phases.DOUJINSHI, new TextPhase(DESCRIPTIONS[2])
                // [即兴吟诗] 获得 #g《赤壁赋》
                .addOption(OPTIONS[3], (i) -> {
                    cg.addToTop(new ChiBiFuCard());
                    transitionKey(Phases.REPLY_1);
                })
                // [喝酒壮胆] 获得 #g《定风波》
                .addOption(OPTIONS[4], (i) -> {
                    cg.addToTop(new DingFengBoCard());
                    transitionKey(Phases.REPLY_2);
                })
                // [握紧佩剑] 获得 #g《剑客》
                .addOption(OPTIONS[5], (i) -> {
                    cg.addToTop(new JianKeCard());
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
            room.rewards.add(new PoetryReward());
            room.addRelicToRewards(RelicTier.COMMON);
        }));

        // 进入事件
        transitionKey(Phases.SALE);
    }

    public void exit() {
        openMap();
    }

    public enum Phases {
        SALE, DRINK, DOUJINSHI, PRE_BATTLE, BATTLE, REPLY_1, REPLY_2, REPLY_3
    }
}
