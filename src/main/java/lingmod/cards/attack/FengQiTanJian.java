package lingmod.cards.attack;

import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.CardConfig;
import lingmod.interfaces.Credit;

/**
 * 0费打8抽2,打出后耗能+1
 */
@CardConfig(damage = 6, magic = 2)
@Credit(username = "花冢なし", platform = Credit.LOFTER, link = "https://buliqiaoqiaodibuliduo606.lofter.com/post/30bac242_2b8b36986")
public class FengQiTanJian extends AbstractEasyCard {
    public final static String ID = makeID(FengQiTanJian.class.getSimpleName());

    public FengQiTanJian() {
        super(ID, 0, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AttackEffect.SLASH_HORIZONTAL);
        addToBot(new DrawCardAction(magicNumber));
        addToBotAbstract(() -> updateCost(1));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }
}
// "${ModID}:FengQiTanJian": {
// "NAME": "FengQiTanJian",
// "DESCRIPTION": ""
// }