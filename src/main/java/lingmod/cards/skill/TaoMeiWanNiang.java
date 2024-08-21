package lingmod.cards.skill;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.CardConfig;
import lingmod.powers.WinePower;

import static lingmod.ModCore.makeID;

/**
 * 活力变成酒
 */
@CardConfig(wineAmount = 1)
public class TaoMeiWanNiang extends AbstractEasyCard {
    public static final String ID = makeID(TaoMeiWanNiang.class.getSimpleName());

    public TaoMeiWanNiang() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
        AbstractPower vigor = p.getPower(VigorPower.POWER_ID);
        if (vigor != null) {
            if (!upgraded)
                addToBot(new RemoveSpecificPowerAction(p, p, vigor));
            addToBot(new ApplyPowerAction(p, p, new WinePower(p, vigor.amount)));
        }
    }

    @Override
    public void upp() {

    }
}
