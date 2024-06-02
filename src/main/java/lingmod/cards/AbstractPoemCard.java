package lingmod.cards;

import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;

import basemod.helpers.CardModifierManager;
import lingmod.cards.mod.PoemMod;
import lingmod.util.CustomTags;

public abstract class AbstractPoemCard extends AbstractEasyCard {
    public final static String ID = makeID(AbstractPoemCard.class.getSimpleName());
    // intellij stuff skill, self, basic, , , 5, 3, ,
    public CardStrings cardStrings;

    public AbstractPoemCard(String id, int cost, CardType cardType, CardRarity cardRarity, CardTarget cardTarget, int poem) {
        super(id, cost, cardType, cardRarity, cardTarget);
        cardStrings = CardCrawlGame.languagePack.getCardStrings(id);
        tags.add(CustomTags.POEM);
        // 诗类卡牌的 前缀
        // CardStrings cs =
        // CardCrawlGame.languagePack.getCardStrings(AbstractPoetCard.ID);
        CardModifierManager.addModifier(this, new PoemMod(poem));
    }
}
