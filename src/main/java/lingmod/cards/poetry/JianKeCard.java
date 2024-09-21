package lingmod.cards.poetry;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.cards.AbstractPoetryCard;
import lingmod.interfaces.CardConfig;
import lingmod.interfaces.Credit;

import static lingmod.ModCore.makeID;

/**
 * 剑客: 造成你当前格挡值2倍的伤害，失去一半的格挡
 */
@Credit(platform = Credit.LOFTER, link = "https://glksier.lofter.com/post/31e1d6b0_2b7cdd958", username = "狗粮扩散一点点")
@CardConfig(damage = 0, block = 8)
public class JianKeCard extends AbstractPoetryCard {
    public static final String ID = makeID(JianKeCard.class.getSimpleName());

    public JianKeCard() {
        super(ID, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
    }

    @Override
    public void applyPowers() {
        baseDamage = AbstractDungeon.player.currentBlock * 2;
        super.applyPowers();
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        baseDamage = AbstractDungeon.player.currentBlock * 2;

        super.calculateCardDamage(mo);
    }

    @Override
    public void use_p(AbstractPlayer p, AbstractMonster m) {
        if (finishFull) {
            dmg(m, null);
            addToBotAbstract(p::loseBlock);
        } else {
            blck();
        }
    }


}