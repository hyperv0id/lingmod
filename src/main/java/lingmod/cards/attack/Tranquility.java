package lingmod.cards.attack;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.CardConfig;

import static lingmod.ModCore.makeID;

/**
 * 清平：造成 !D! 点伤害，将一张牌变为清平。 保留时：对随机敌人打出
 */
@CardConfig(damage = 9, isSummon = true)
public class Tranquility extends AbstractEasyCard {

    public static final String ID = makeID(Tranquility.class.getSimpleName());

    public Tranquility() {
        super(ID, 0, CardType.ATTACK, CardRarity.BASIC, CardTarget.ENEMY);
    }

    @Override
    public void upp() {
        upgradeDamage(3);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AttackEffect.NONE);
        addToTop(new MakeTempCardInDrawPileAction(makeStatEquivalentCopy(), 1, true, true));
        addToBotAbstract(() -> this.dontTriggerOnUseCard = false);
    }
}
