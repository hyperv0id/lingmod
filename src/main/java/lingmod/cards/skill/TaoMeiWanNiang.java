package lingmod.cards.skill;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import lingmod.actions.MyApplyPower_Action;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.CardConfig;
import lingmod.interfaces.Credit;
import lingmod.powers.WinePower;

import static lingmod.ModCore.makeID;

/**
 * 活力变成酒
 */
@CardConfig(wineAmount = 1)
@Credit(platform = Credit.LOFTER, link = "https://evon075063.lofter.com/post/31abe460_2b467a812", username = "限定赏味期小狗")
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
            addToBot(new MyApplyPower_Action(p, p, new WinePower(p, vigor.amount)));
        }
    }

    @Override
    public void upp() {

    }
}
