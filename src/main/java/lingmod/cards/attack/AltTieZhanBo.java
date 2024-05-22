package lingmod.cards.attack;

import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.actions.watcher.WallopAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import lingmod.cards.AbstractEasyCard;

public class AltTieZhanBo extends AbstractEasyCard {
    public final static String ID = makeID(AltTieZhanBo.class.getSimpleName());

    public final static int BASE_DAMAGE = 17; // 造成伤害
    public final static int BASE_DAMAGE_P = 5;


    public AltTieZhanBo() {
        super(ID, 3, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = BASE_DAMAGE;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new WallopAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL)));
    }

    @Override
    public void upp() {
        upgradeBlock(3);
        upgradeDamage(BASE_DAMAGE_P);
    }
}