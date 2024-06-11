package lingmod.cards.attack;

import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import lingmod.actions.ExhaustAllAction;
import lingmod.cards.AbstractPoemCard;
import lingmod.powers.PoeticMoodPower;
import lingmod.util.Wiz;

import java.util.List;

import static java.lang.Math.max;
import static lingmod.ModCore.makeID;

/**
 * 宁作吾：消耗所有手牌，抽等量牌，然后所有debuff变成诗
 * 能力数量参与计数
 */
public class NingZuoWuCard extends AbstractPoemCard {

    public static final String ID = makeID(NingZuoWuCard.class.getSimpleName());

    public NingZuoWuCard() {
        super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.SELF, 3);
        baseDamage = 5;
        CardModifierManager.addModifier(this, new ExhaustMod());
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBotAbstract(() -> {
            // 统计
            long cnt = AbstractDungeon.player.hand.size();
            if (upgraded)
                cnt += AbstractDungeon.player.powers.size();
            List<AbstractPower> powerList = Wiz.allPowers(null);

            cnt += powerList.stream().mapToInt(power -> max(power.amount, 1)).count();
            // 4. 转换成诗意
            addToTop(new ApplyPowerAction(p, p, new PoeticMoodPower(p, (int) cnt)));
            // 3. 移除所有能力
            powerList.forEach(power -> {
                addToTop(new RemoveSpecificPowerAction(p, p, power));
            });
            // 2. 抽牌
            addToTop(new DrawCardAction((int) cnt));
            // 1. 消耗
            addToTop(new ExhaustAllAction());
        });
        // addToBot(new RemoveAllPowersAction(p, true));
        // addToBot(new ApplyPowerAction(p, p, new PoeticMoodPower(p, powerSum)));
    }

    @Override
    public void upp() {
        upgradeDamage(1);
    }
}