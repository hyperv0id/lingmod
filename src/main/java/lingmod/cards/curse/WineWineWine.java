package lingmod.cards.curse;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.cards.AbstractEasyCard;
import lingmod.cards.mod.WineMod;
import lingmod.interfaces.Credit;
import lingmod.util.CustomTags;

import static lingmod.ModCore.makeID;
import static lingmod.util.Wiz.isInHand;

@Credit(username = "会说话的粉红DJ熊", platform = Credit.LOFTER, link = "https://songpujuli.lofter.com/post/1f06186c_2bb25fa09")
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
