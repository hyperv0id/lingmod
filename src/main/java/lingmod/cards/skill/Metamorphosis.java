package lingmod.cards.skill;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.CardConfig;
import lingmod.interfaces.Credit;
import lingmod.powers.YuNiaoPower;

import static lingmod.ModCore.makeID;
import static lingmod.util.MonsterHelper.calcIntentDmg;

@CardConfig(isDream = true)
@Credit(username = "江晚枫", platform = Credit.LOFTER, link = "https://yinghailingwang.lofter.com/post/1e4211fb_2b47abec7")
public class Metamorphosis extends AbstractEasyCard {

    public static final String ID = makeID(Metamorphosis.class.getSimpleName());

    public Metamorphosis() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ENEMY);
    }

    @Override
    public void upp() {
        updateCost(-1);
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        baseDamage = calcIntentDmg();
        super.calculateCardDamage(mo);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster mo) {
        addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new YuNiaoPower(abstractPlayer, mo)));
    }
}
