package lingmod.cards.mod;

import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import basemod.abstracts.AbstractCardModifier;
import lingmod.powers.WinePower;
import lingmod.util.Wiz;

/**
 * 此牌是酒
 */
public class WineMod extends AbsLingCardModifier {
    public static final String ID = makeID(WineMod.class.getSimpleName());
    public int amount = 0;
    public static final UIStrings uis = CardCrawlGame.languagePack.getUIString(ID);

    public WineMod(int wineAmount) {
        this.amount = wineAmount;
    }

    /**
     * 如果费用改变了，那么恢复
     */
    public void onCardDraw(AbstractCard card) {
        if (card.isCostModifiedForTurn) {
            if (card.cost != card.costForTurn) {
                card.costForTurn = card.cost;
                card.isCostModified = false;
            }
        }
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        if (amount <= 0)
            return rawDescription;
        String format = uis.TEXT[0];
        return String.format(format, amount, rawDescription);
    }

    /**
     * 伤害增加 wine * 费用 %
     */
    @Override
    public float modifyDamage(float damage, DamageType type, AbstractCard card, AbstractMonster target) {
        if(type != DamageType.NORMAL) return damage;
        AbstractPower winPower = AbstractDungeon.player.getPower(WinePower.POWER_ID);
        int amt = winPower != null ? winPower.amount : 0;
        damage *= 1.00F + card.costForTurn * amt / 100.0F;
        return damage;
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        super.onUse(card, target, action);
        AbstractPlayer p = AbstractDungeon.player;
        addToTop(new ApplyPowerAction(p, p, new WinePower(p, amount)));
        Wiz.addToBotAbstract(Wiz::shuffleHandCost); // 打乱手牌费用
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new WineMod(amount);
    }
}
