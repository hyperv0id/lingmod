package lingmod.cards.attack;


import basemod.cardmods.EtherealMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.CardConfig;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static lingmod.ModCore.makeID;

/**
 * 宁作吾：从右往左消耗卡牌来去除debuff，每消耗一张抽一张牌，获得 [E]
 * 能力数量参与计数
 */
@CardConfig(poemAmount = 3, magic = 1)
public class NingZuoWuCard extends AbstractEasyCard {

    public static final String ID = makeID(NingZuoWuCard.class.getSimpleName());

    public NingZuoWuCard() {
        super(ID, 3, CardType.ATTACK, CardRarity.RARE, CardTarget.SELF);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBotAbstract(() -> {
            List<AbstractPower> debuffs =
                    p.powers.stream().filter(po -> po.type == AbstractPower.PowerType.DEBUFF).collect(Collectors.toList());
            Collections.reverse(debuffs);
            int cnt = Math.min(debuffs.size(), p.hand.size());
            for (int i = 0; i < cnt; i++) {
                // 删除debuff
                addToBot(new RemoveSpecificPowerAction(p, p, debuffs.get(i)));
                // 消耗卡牌
                addToBot(new ExhaustSpecificCardAction(p.hand.group.get(p.hand.size() - i - 1), p.hand, true));
            }
            addToBot(new DrawCardAction(cnt * magicNumber));
            addToBot(new GainEnergyAction(cnt * magicNumber));
        });
        // addToBot(new RemoveAllPowersAction(p, true));
        // addToBot(new ApplyPowerAction(p, p, new PoeticMoodPower(p, powerSum)));
    }

    @Override
    public void upp() {
        CardModifierManager.addModifier(this, new EtherealMod());
    }
}