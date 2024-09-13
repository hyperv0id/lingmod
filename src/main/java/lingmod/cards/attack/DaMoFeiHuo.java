package lingmod.cards.attack;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.CardConfig;
import lingmod.interfaces.Credit;
import lingmod.util.Wiz;

import static lingmod.ModCore.makeID;

/**
 * 伤害 = 消耗堆/2
 */
@CardConfig(damage = 9, magic = 10)
@Credit(link = "https://www.pixiv.net/artworks/111160788", platform = Credit.PIXIV, username = "WHO808")
public class DaMoFeiHuo extends AbstractEasyCard {
    public final static String ID = makeID(DaMoFeiHuo.class.getSimpleName());

    public DaMoFeiHuo() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ENEMY);
    }


    @Override
    public void applyPowers() {
        this.baseDamage = Wiz.adp().exhaustPile.size() / 2;
        super.applyPowers();
    }
    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        this.baseDamage = Wiz.adp().exhaustPile.size() / 2;
        super.calculateCardDamage(mo);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, null);
    }

    @Override
    public void upp() {
    }
}
// "lingmod:DaMoFeiHuo": {
// "NAME": "DaMoFeiHuo",
// "DESCRIPTION": ""
// }
