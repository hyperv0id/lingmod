package lingmod.cards.attack;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.cards.AbstractEasyCard;
import lingmod.powers.PoeticMoodPower;

import static lingmod.ModCore.makeID;

/**
 * 清平：打7,消耗时获得 1 诗兴。
 */
public class Tranquility extends AbstractEasyCard{

    public static final String ID = makeID(Tranquility.class.getSimpleName());

    public Tranquility() {
        super(ID, 1, CardType.ATTACK, CardRarity.BASIC, CardTarget.ENEMY);
        this.baseDamage = 7;
        this.baseMagicNumber = 1;
    }

    @Override
    public void triggerOnExhaust() {
        super.triggerOnExhaust();
        AbstractPlayer p = AbstractDungeon.player;
        addToBot(new ApplyPowerAction(p, p, new PoeticMoodPower(p, magicNumber)));
        addToBot(new MakeTempCardInDrawPileAction(makeCopy(), 1, true, true));
    }

    @Override
    public void upp() {
        upgradeDamage(3);
        upgradeMagicNumber(1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AttackEffect.NONE);
    }
    
}
