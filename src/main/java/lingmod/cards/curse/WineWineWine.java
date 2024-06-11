package lingmod.cards.curse;

import static lingmod.ModCore.makeID;
import static lingmod.util.Wiz.isInHand;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.helpers.CardModifierManager;
import lingmod.cards.AbstractEasyCard;
import lingmod.cards.mod.WineMod;
import lingmod.util.CustomTags;

public class WineWineWine extends AbstractEasyCard {
    public final static String ID = makeID(WineWineWine.class.getSimpleName());

    public WineWineWine() {
        super(ID, -2, CardType.CURSE, CardRarity.CURSE, CardTarget.NONE);
        this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
    }

    @Override
    public boolean canPlay(AbstractCard card) {
        if (isInHand(this)) {
            boolean isWine = card.hasTag(CustomTags.WINE) && CardModifierManager.hasModifier(card, WineMod.ID);
            if (isWine) {
                if (card.cantUseMessage == null || card.cantUseMessage.isEmpty()) {
                    card.cantUseMessage = this.cantUseMessage;
                }
            }
            return !isWine; // 不是酒才能打出
        }
        return super.canPlay(card);
    }

    @Override
    public void use(AbstractPlayer arg0, AbstractMonster arg1) {
    }

    @Override
    public void upp() {
    }
}
