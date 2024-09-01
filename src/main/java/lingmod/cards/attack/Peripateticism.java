package lingmod.cards.attack;

import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ConstrictedPower;
import lingmod.actions.MyApplyPower_Action;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.CardConfig;
import lingmod.interfaces.Credit;
import lingmod.monsters.AbsSummonMonster;
import lingmod.monsters.Peripateticism_SummonMonster;
import lingmod.monsters.Thunderer_SummonMonster;
import lingmod.relics.SanYiShiJian;
import lingmod.util.MonsterHelper;
import lingmod.util.Wiz;

import java.util.List;
import java.util.stream.Collectors;

import static lingmod.ModCore.makeID;

/**
 * “逍遥”：消耗所有技能牌，每张给予 8 缠绕
 */
@CardConfig(magic = 6, isSummon = true, summonClz = Peripateticism_SummonMonster.class)
@Credit(username = "没有名字", platform = Credit.LOFTER, link = "https://gohanduck.lofter.com/post/1f3831ee_2b8230298")
public class Peripateticism extends AbstractEasyCard {
    public static final String ID = makeID(Peripateticism.class.getSimpleName());

    public Peripateticism() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (Wiz.adp().hasRelic(SanYiShiJian.ID)) {
            CardConfig cc = this.getClass().getAnnotation(CardConfig.class);
            MonsterHelper.summonMonster(cc.summonClz());
            return;
        }
        addToBotAbstract(() -> {
            // 获取所有技能牌
            List<AbstractCard> cardsToExhaust = AbstractDungeon.player.hand.group.stream()
                    .filter(c -> c.type != CardType.ATTACK)
                    .collect(Collectors.toList());
            cardsToExhaust.forEach(c -> {
                // 消耗
                addToTop(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand));
                // 对怪物施加缠绕效果
                MonsterHelper.allMonsters().forEach(mo -> addToTop(new MyApplyPower_Action(mo, p, new ConstrictedPower(mo, p, magicNumber))));
            });
            addToBotAbstract(() -> {
                for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
                    ConstrictedPower pw = (ConstrictedPower) mo.getPower(ConstrictedPower.POWER_ID);
                    if (pw != null) {
                        pw.atEndOfTurn(false);
                    }
                }
            });
        });
    }


    public void summon_use(AbstractPlayer p, AbstractMonster monster) {
        int cnt = MonsterHelper.cntSummons();
        if (cnt >= magicNumber) {
            String msg = String.format("我不能召唤超过 %d 个召唤物", magicNumber);
            addToBot(new TalkAction(true, msg, 2.0F, 2.0F));
        }
        addToBotAbstract(() -> {
            AbsSummonMonster mo = (AbsSummonMonster) MonsterHelper
                    .spawnMonster(Thunderer_SummonMonster.class);
            // mo.animX = 1200F * Settings.xScale;
            mo.setDamage(3);
            mo.init();
            mo.applyPowers();
            mo.useUniversalPreBattleAction();
            mo.showHealthBar();
            mo.createIntent();

            mo.usePreBattleAction();

            // AbstractDungeon.getCurrRoom().monsters.addMonster(0, mo);
        });
    }
}
