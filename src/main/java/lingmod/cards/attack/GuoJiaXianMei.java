package lingmod.cards.attack;

import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.CardConfig;

@CardConfig(damage = 17, block = 17, poemAmount = 3)
public class GuoJiaXianMei extends AbstractEasyCard {
    public final static String ID = makeID(GuoJiaXianMei.class.getSimpleName());

    public final static int BASE_DAMAGE = 17; // 造成伤害
    public final static int BASE_BLOCK = 17;
    public final static int BASE_BLOCK_P = 5;
    public final static int BASE_DAMAGE_P = 5;


    public GuoJiaXianMei() {
        super(ID, 3, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = BASE_DAMAGE;
        baseBlock = BASE_BLOCK;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, null);
        blck();
        // addToBot(new WallopAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL)));
    }

    @Override
    public void upp() {
        upgradeDamage(BASE_DAMAGE_P);
        upgradeBlock(BASE_BLOCK_P);
    }
}