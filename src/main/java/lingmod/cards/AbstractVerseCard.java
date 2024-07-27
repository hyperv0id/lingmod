package lingmod.cards;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.patch.TypeOverridePatch;
import lingmod.util.CustomTags;
import lingmod.util.VerseLoader;

import static lingmod.ModCore.makeID;

/**
 * 词牌：规定了整场战斗的格调，按照格调打出额外效果
 */
public abstract class AbstractVerseCard extends AbstractEasyCard {
    public final static String ID = makeID(AbstractVerseCard.class.getSimpleName());
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ID);
    public static final String TYPE = uiStrings.TEXT[0]; // 词牌
    public VerseStrings verseStrings;
    public int poeticCost = 0; // 打出需要消耗多少诗兴
    protected boolean isOptionCard = false;
    protected boolean used = false;
    protected int[] costs;

    public AbstractVerseCard(String id, int poetCost, CardType cardType, CardRarity cardRarity) {
        super(id, -2, cardType, cardRarity, CardTarget.NONE);
        this.poeticCost = poetCost;
        tags.add(CustomTags.VERSE);
        tags.add(CardTags.HEALING); // 不能被树枝等检索到
        verseStrings = VerseLoader.getStr(id);
        this.dontTriggerOnUseCard = true; // 使用时不触发XX检查
        this.selfRetain = true; // 保留

        CardStrings acs = CardCrawlGame.languagePack.getCardStrings(ID);
        TypeOverridePatch.TypeOverrideField.typeOverride.set(this, TYPE); // 更改卡图上的类型描述
        this.cantUseMessage = acs.EXTENDED_DESCRIPTION[0];
    }

    @Override
    public void onChoseThisOption() {
        addToBot(new MakeTempCardInHandAction(this, false));
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public void initializeDescription() {
        if (verseStrings == null) {
            verseStrings = VerseLoader.getStr(cardID);
        }
        if (verseStrings != null) {
            this.rawDescription = verseStrings.DESCRIPTION;
        }
        super.initializeDescription();
    }
}
