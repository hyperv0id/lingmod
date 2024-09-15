package lingmod.cards.attack;

import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.ModCore;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.CardConfig;
import lingmod.interfaces.Credit;
import lingmod.patch.PlayerFieldsPatch;
import lingmod.util.Wiz;

/**
 * 曲水流觞: 你每有一种诗词赋曲，造成7伤害
 */
@Credit(username = "聚变之书", platform = Credit.LOFTER, link = "https://shenzhi041.lofter.com/post/2047e763_2bb5a7e85")
@CardConfig(magic = 4, damage = 4)
public class QuShuiLiuShang extends AbstractEasyCard {
    public static final String ID = ModCore.makeID(QuShuiLiuShang.class.getSimpleName());

    public QuShuiLiuShang() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
    }

    @Override
    public void applyPowers() {
        CardGroup cg = PlayerFieldsPatch.poetryCardGroup.get(Wiz.adp());
        if (cg != null)
            this.baseDamage = cg.size() * magicNumber;
        super.applyPowers();
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        CardGroup cg = PlayerFieldsPatch.poetryCardGroup.get(Wiz.adp());
        if (cg != null)
            this.baseDamage = cg.size() * magicNumber;
        super.calculateCardDamage(mo);
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, null);
    }
}
