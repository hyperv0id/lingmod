package lingmod.cards.skill;

import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import basemod.AutoAdd;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.CardConfig;
import lingmod.interfaces.Credit;
import lingmod.powers.WinePower;

/**
 * 活力变成酒
 */
@CardConfig(wineAmount = 1)
@AutoAdd.Ignore
@Credit(platform = Credit.LOFTER, link = "https://evon075063.lofter.com/post/31abe460_2b467a812", username = "限定赏味期小狗")
public class TaoMeiWanNiang extends AbstractEasyCard {
    public static final String ID = makeID(TaoMeiWanNiang.class.getSimpleName());

    public TaoMeiWanNiang() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
        AbstractPower winePo = p.getPower(WinePower.POWER_ID);
        if (winePo != null) {
            if (!upgraded)
                addToBot(new RemoveSpecificPowerAction(p, p, winePo));
            addToBot(new ApplyPowerAction(p, p, new WinePower(p, winePo.amount)));
        }
    }

    @Override
    public void upp() {

    }
}
