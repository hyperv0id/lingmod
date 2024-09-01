package lingmod.cards.attack;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.CardConfig;
import lingmod.interfaces.Credit;

import static lingmod.ModCore.makeID;

@CardConfig(damage = 15, block = 15)
@Credit(username = "八雲奥", platform = "lofter", link = "https://yakumoau.lofter.com/post/320b60e2_2b7e5df27")
public class GuoJiaXianMei extends AbstractEasyCard {
    public final static String ID = makeID(GuoJiaXianMei.class.getSimpleName());

    public GuoJiaXianMei() {
        super(ID, 3, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, null);
        blck();
        // addToBot(new WallopAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL)));
    }

    @Override
    public void upp() {
        upgradeDamage(5);
        upgradeBlock(5);
    }
}