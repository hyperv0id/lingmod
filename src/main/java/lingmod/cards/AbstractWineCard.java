package lingmod.cards;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import lingmod.cards.mod.WineMod;
import lingmod.util.CustomTags;

import static lingmod.ModCore.makeID;

/**
 * 是酒，打出后获得 3 活力?
 */
public abstract class AbstractWineCard extends AbstractEasyCard {
    public static final int WINE_AMOUNT = 3;
    public static String ID = makeID(AbstractWineCard.class.getSimpleName());
    public CardStrings cardStrings;

    public AbstractWineCard(String id, int cost, CardType cardType, CardRarity cardRarity, CardTarget cardTarget) {
        this(id, cost, cardType, cardRarity, cardTarget, WINE_AMOUNT);
    }

    public AbstractWineCard(String id, int cost, CardType cardType, CardRarity cardRarity, CardTarget cardTarget,
                            int wineAmount) {
        super(id, cost, cardType, cardRarity, cardTarget);
        cardStrings = CardCrawlGame.languagePack.getCardStrings(id);
        this.tags.add(CustomTags.WINE);
        CardModifierManager.addModifier(this, new WineMod(wineAmount));
    }
}
