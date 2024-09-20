package lingmod.cards.power;

import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
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
@CardConfig(magic = 0, wineAmount = 0)
public class NvErHong extends AbstractEasyCard {
    public final static String ID = makeID(NvErHong.class.getSimpleName());

    public NvErHong() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        CardModifierManager.addModifier(this, new ExhaustMod());
    }

    @Override
    public void applyPowers() {
        calcMagic();
        super.applyPowers();
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        calcMagic();
        super.calculateCardDamage(mo);
    }

    public void calcMagic() {
        WinePower wine = (WinePower) Wiz.adp().getPower(WinePower.POWER_ID);
        if (wine != null && !Wiz.adp().hand.isEmpty()) {
            int amt = (int) Wiz.adp().hand.group.stream().filter(c -> c.type == CardType.ATTACK).count();
            if (amt == 0) baseMagicNumber = 0;
            else baseMagicNumber = wine.amount / amt;
        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        WinePower wine = (WinePower) p.getPower(WinePower.POWER_ID);
        if (wine != null) {
            addToBotAbstract(() -> {
                p.hand.group.stream().filter(c -> c.type == CardType.ATTACK).forEach(c -> CardModifierManager.addModifier(c, new NvErHongMod(magicNumber)));
                addToBot(new RemoveSpecificPowerAction(p, p, wine));
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