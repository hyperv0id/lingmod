package lingmod.cards.attack;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import lingmod.ModCore;
import lingmod.actions.MyApplyPower_Action;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.CardConfig;
import lingmod.interfaces.Credit;

@CardConfig(damage = 6, magic = 2)
@Credit(username = "枯荷倚梅cc", platform = "lofter", link = "https://anluochen955.lofter.com/post/1f2a08fc_2baf8eeb3")
public class BattleHymn extends AbstractEasyCard {
    public static final String ID = ModCore.makeID(BattleHymn.class.getSimpleName());

    public BattleHymn() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, null);
        addToBot(new MyApplyPower_Action(m, p, new VulnerablePower(m, magicNumber, false)));
    }

    @Override
    public void upp() {
        upgradeDamage(4);
        upgradeSecondMagic(1);
    }
}