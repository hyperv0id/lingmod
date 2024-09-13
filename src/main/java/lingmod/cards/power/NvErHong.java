package lingmod.cards.power;

import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.cards.AbstractEasyCard;
import lingmod.cards.mod.NvErHongMod;
import lingmod.interfaces.CardConfig;
import lingmod.interfaces.Credit;
import lingmod.powers.WinePower;
import lingmod.util.Wiz;

import static lingmod.ModCore.makeID;

/**
 * 回合结束时，消耗所有酒，提升手牌中攻击牌的攻击力
 */
@Credit(link = "https://dianzhi1234.lofter.com/post/317cabb7_2bb505ad6", platform = Credit.LOFTER, username = "四戒懒癌")
@CardConfig(magic = 1, wineAmount = 0)
public class NvErHong extends AbstractEasyCard {
    public final static String ID = makeID(NvErHong.class.getSimpleName());

    public NvErHong() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        CardModifierManager.addModifier(this, new ExhaustMod());
    }

    @Override
    public void applyPowers() {
        WinePower wine = (WinePower) Wiz.adp().getPower(WinePower.POWER_ID);
        if (wine != null) {
            magicNumber = wine.amount / (Wiz.adp().hand.size() - 1);
        }
        super.applyPowers();
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        WinePower wine = (WinePower) Wiz.adp().getPower(WinePower.POWER_ID);
        if (wine != null) {
            magicNumber = wine.amount / Wiz.adp().hand.size();
        }
        super.calculateCardDamage(mo);
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