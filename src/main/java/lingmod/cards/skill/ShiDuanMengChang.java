package lingmod.cards.skill;

import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.cards.AbstractEasyCard;
import lingmod.powers.DreamIsEndless;
import lingmod.powers.PoemIsShort;

import static lingmod.ModCore.makeID;

/**
 * 免伤50%，转换为1/M缠绕
 */
public class ShiDuanMengChang extends AbstractEasyCard {

    public static final String ID = makeID(ShiDuanMengChang.class.getSimpleName());

    public ShiDuanMengChang() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        CardModifierManager.addModifier(this, new ExhaustMod());
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
