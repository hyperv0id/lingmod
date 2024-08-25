package lingmod.cards.skill;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.Credit;
import lingmod.powers.DreamIsEndless;
import lingmod.powers.PoemIsShort;

import static lingmod.ModCore.makeID;

/**
 * 免伤50%，转换为1/M缠绕
 */
@Credit(username = "枯荷倚梅cc", platform = "lofter", link = "https://anluochen955.lofter.com/post/1f2a08fc_2baf8eeb3")
public class ShiDuanMengChang extends AbstractEasyCard {

    public static final String ID = makeID(ShiDuanMengChang.class.getSimpleName());

    public ShiDuanMengChang() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
    }

    @Override
    public void upp() {
        updateCost(-1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // 免伤 50%
        addToBot(new ApplyPowerAction(p, m, new PoemIsShort(p, 50)));
        // 转换成缠绕
        addToBot(new ApplyPowerAction(p, p, new DreamIsEndless(p)));
    }

}
