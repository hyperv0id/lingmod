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
@Credit(username = "明日方舟", platform = "鹰角网络", link = "https://prts.wiki/w/令")
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
        // 免伤
        addToBot(new ApplyPowerAction(p, m, new PoemIsShort(p, 25)));
        // 转换成缠绕
        addToBot(new ApplyPowerAction(p, p, new DreamIsEndless(p)));
    }

}
