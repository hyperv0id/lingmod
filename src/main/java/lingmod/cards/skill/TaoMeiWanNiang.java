package lingmod.cards.skill;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.CardConfig;
import lingmod.interfaces.Credit;
import lingmod.powers.TaoMeiWanNiangPower;
import lingmod.util.Wiz;

import static lingmod.ModCore.makeID;

/**
 * 活力变成酒
 */
@CardConfig(magic = 3, magic2 = 1)
@Credit(platform = Credit.LOFTER, link = "https://evon075063.lofter.com/post/31abe460_2b467a812", username = "限定赏味期小狗")
public class TaoMeiWanNiang extends AbstractEasyCard {
    public static final String ID = makeID(TaoMeiWanNiang.class.getSimpleName());

    public TaoMeiWanNiang() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
        Wiz.applyToSelf(new TaoMeiWanNiangPower(p, 3, 1));
    }

    @Override
    public void upp() {
        updateCost(-1);
    }
}
