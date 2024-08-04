package lingmod.cards.skill;

import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.CardConfig;
import lingmod.powers.DrinkAlonePower;
import lingmod.powers.WinePower;

import static lingmod.ModCore.makeID;

/**
 * 独酌：获得 X*X 酒 NL 下一张卡牌消耗全部能量，打出X次
 */
@CardConfig(magic = 0)
public class DrinkAlone extends AbstractEasyCard {
    public final static String ID = makeID(DrinkAlone.class.getSimpleName());

    public DrinkAlone() {
        super(ID, -1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        CardModifierManager.addModifier(this, new ExhaustMod());
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        magicNumber = EnergyPanel.totalCount + baseMagicNumber;
        if (AbstractDungeon.player.hasRelic(ChemicalX.ID)) {
            AbstractRelic chemicalX = AbstractDungeon.player.getRelic(ChemicalX.ID);
            chemicalX.flash();
            magicNumber += 2;
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new WinePower(p, magicNumber * magicNumber)));
        addToBot(new ApplyPowerAction(p, p, new DrinkAlonePower(p, magicNumber)));
        // addToBot(new GainEnergyAction(EnergyPanel.totalCount));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }
}
