package lingmod.cards.skill;

import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import lingmod.cards.AbstractEasyCard;
import lingmod.cards.mod.NellaFantasiaMod;

public class AGiftCard extends AbstractEasyCard {

    public static final String ID = makeID(AGiftCard.class.getSimpleName());

    public AGiftCard() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ALL);
        baseMagicNumber = 3;
        baseSecondMagic = 1;
        CardModifierManager.addModifier(this, new ExhaustMod());
        CardModifierManager.addModifier(this, new NellaFantasiaMod());
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            this.addToBot(new ApplyPowerAction(mo, p, new WeakPower(mo, this.magicNumber, false), this.magicNumber,
                    true, AttackEffect.NONE));
            this.addToBot(new ApplyPowerAction(mo, p, new VulnerablePower(mo, this.magicNumber, false),
                    this.magicNumber, true, AttackEffect.NONE));
        }
        // addToBot(new ApplyPowerAction(p, p, new VulnerablePower(p, secondMagic, false)));
        addToBot(new ApplyPowerAction(p, p, new WeakPower(p, secondMagic, false)));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(2);
    }
}