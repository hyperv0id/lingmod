package lingmod.cards.skill;

import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import lingmod.cards.AbstractEasyCard;

import java.util.List;

import static lingmod.ModCore.makeID;
import static lingmod.util.Wiz.allPowers;
import static lingmod.util.Wiz.isStanceNell;

/**
 * 移除最后能力，获得E，抽两张。如果在梦中，获得E
 */
public class YaGaoMengYuan extends AbstractEasyCard {
    public final static String ID = makeID(YaGaoMengYuan.class.getSimpleName());

    public YaGaoMengYuan() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = 2; // 抽两张牌
        CardModifierManager.addModifier(this, new ExhaustMod());
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return !allPowers(null).isEmpty();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(magicNumber));
        addToBotAbstract(() -> {
            List<AbstractPower> powers = allPowers(null);
            if (!powers.isEmpty()) {
                addToBot(new RemoveSpecificPowerAction(p, p, powers.get(powers.size() - 1)));
                addToBot(new GainEnergyAction(1));
            }
        });
        if (isStanceNell()) {
            addToBot(new GainEnergyAction(1));
        }
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }
}
// "lingmod:YaGaoMengYuan": {
// "NAME": "YaGaoMengYuan",
// "DESCRIPTION": ""
// }