package lingmod.cards.skill;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import lingmod.ModCore;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.CardConfig;
import lingmod.interfaces.Credit;
import lingmod.powers.ShanHeYuanKuoPower;

/**
 * 本回合你每打出一张牌就抽一张牌
 */
@CardConfig(magic = 1)
@Credit(username = "下野宏铭", link = "主页限制")
public class ShanHeYuanKuo extends AbstractEasyCard {
    public static final String ID = ModCore.makeID(ShanHeYuanKuo.class.getSimpleName());

    public ShanHeYuanKuo() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster mo) {
        addToBot(new ApplyPowerAction(p, p, new ShanHeYuanKuoPower(p, magicNumber)));
    }

    @Override
    public void upp() {
        updateCost(-1);
    }
}