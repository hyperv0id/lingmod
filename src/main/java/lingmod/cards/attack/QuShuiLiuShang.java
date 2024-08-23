package lingmod.cards.attack;

import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import lingmod.ModCore;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.Credit;
import lingmod.patch.PlayerFieldsPatch;
import lingmod.util.Wiz;

/**
 * 曲水流觞: 你每有一种诗词赋曲，造成7伤害
 */
@Credit(username = "聚变之书", platform = Credit.LOFTER, link = "https://shenzhi041.lofter.com/post/2047e763_2bb5a7e85")
public class QuShuiLiuShang extends AbstractEasyCard {
    public static final String ID = ModCore.makeID(QuShuiLiuShang.class.getSimpleName());

    public QuShuiLiuShang() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.baseDamage = 7;
        this.baseMagicNumber = 1;
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        CardGroup cg = PlayerFieldsPatch.poetryCardGroup.get(Wiz.adp());
        this.baseMagicNumber = cg.size();
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
