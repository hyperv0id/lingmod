package lingmod.cards.attack;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import lingmod.cards.AbstractEasyCard;
import lingmod.powers.AltDemonPower;

import static lingmod.ModCore.makeID;

/**
 * 清平：打5，下次增加3点,升级后打8增加5点。是fibonacci数列
 */
public class Tranquility extends AbstractEasyCard{
    public static final String ID = makeID(Tranquility.class.getSimpleName());

    public static final int COST = 1;

    public static final int F1 = 1;
    public static final int F2 = 1;
    public static final int F3 = 2;


    public Tranquility() {
        super(ID, COST, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        this.baseDamage = F3;
        this.baseSecondDamage = F2;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        addToBot(new DamageAction(monster, new DamageInfo(player, damage, DamageInfo.DamageType.NORMAL)));
        int t = baseDamage;
        baseDamage += baseSecondDamage;
        baseSecondDamage = t;
    }
    @Override
    public void upp() {
        upgradeDamage(F2);
        upgradeSecondDamage(F1);
    }
}
