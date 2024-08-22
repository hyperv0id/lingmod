package lingmod.cards.attack;

import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.ModCore;
import lingmod.cards.AbstractEasyCard;
import lingmod.patch.PlayerFieldsPatch;
import lingmod.util.Wiz;

/**
 * 曲水流觞: 你每有一种诗词赋曲，造成7伤害
 */
public class QuShuiLiuShang extends AbstractEasyCard {
    public static final String ID = ModCore.makeID(QuShuiLiuShang.class.getSimpleName());

    public QuShuiLiuShang() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.baseDamage = 7;
        this.baseMagicNumber = 1;
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        CardGroup cg = PlayerFieldsPatch.poetryCardGroup.get(Wiz.adp());
        this.magicNumber = cg.size();
        super.calculateCardDamage(mo);
    }

    @Override
    public void upp() {
        upgradeDamage(2);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < magicNumber; i++) {
            dmg(m, null);
        }
    }
}
