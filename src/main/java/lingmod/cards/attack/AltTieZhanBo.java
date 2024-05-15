package lingmod.cards.attack;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;

import lingmod.cards.AbstractEasyCard;

import static lingmod.ModCore.makeID;

public class AltTieZhanBo extends AbstractEasyCard {
    public final static String ID = makeID(AltTieZhanBo.class.getSimpleName());

    public final static int BASE_DAMAGE = 5; // 造成伤害
    public final static int BASE_DAMAGE_P = 2;
    public final static int PA_NUM = 4; // 多层护甲
    public final static int PA_NUM_P = 1;


    public AltTieZhanBo() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = BASE_DAMAGE;
        baseMagicNumber = PA_NUM;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL)));
        addToBot(new ApplyPowerAction(p, p, new PlatedArmorPower(p, baseMagicNumber)));
    }

    @Override
    public void upp() {
        upgradeBlock(3);upgradeDamage(BASE_DAMAGE_P);
        upgradeMagicNumber(PA_NUM_P);
    }
}