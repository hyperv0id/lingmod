package lingmod.cards.power;

import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.cards.AbstractEasyCard;
import lingmod.cards.mod.NvErHongMod;
import lingmod.interfaces.CardConfig;
import lingmod.powers.WinePower;

import static lingmod.ModCore.makeID;

/**
 * 回合结束时，消耗所有酒，提升手牌中攻击牌的攻击力
 */

@CardConfig(magic = 1, wineAmount = 3)
public class NvErHong extends AbstractEasyCard {
    public final static String ID = makeID(NvErHong.class.getSimpleName());

    public NvErHong() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        CardModifierManager.addModifier(this, new ExhaustMod());
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        WinePower wine = (WinePower) p.getPower(WinePower.POWER_ID);
        if (wine != null) {
            addToBotAbstract(() -> {
                int avg = (int) (wine.amount / p.hand.group.stream().filter(c -> c.type == CardType.ATTACK).count());
                p.hand.group.stream().filter(c -> c.type == CardType.ATTACK).forEach(c -> CardModifierManager.addModifier(c, new NvErHongMod(avg)));
                addToTopAbstract(wine::damp);
            });
        }
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
        CardModifierManager.removeModifiersById(this, ExhaustMod.ID, true);
    }
}
// "lingmod:NvErHong": {
// "NAME": "NvErHong",
// "DESCRIPTION": ""
// }