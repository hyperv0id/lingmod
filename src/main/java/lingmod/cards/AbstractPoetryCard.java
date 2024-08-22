package lingmod.cards;

import basemod.interfaces.OnCardUseSubscriber;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import lingmod.patch.TypeOverridePatch;
import lingmod.util.CustomTags;
import lingmod.util.PoetryLoader;
import lingmod.util.card.ToneManager;

import static lingmod.ModCore.makeID;

/**
 * 诗词赋曲：规定了整场战斗的格调，按照格调打出额外效果
 */
public abstract class AbstractPoetryCard extends AbstractEasyCard implements OnCardUseSubscriber {
    public final static String ID = makeID(AbstractPoetryCard.class.getSimpleName());
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ID);
    public static final String TYPE = uiStrings.TEXT[0]; // 诗词赋曲
    public PoetryStrings poetryStrings;
    protected ToneManager toneManager;


    public AbstractPoetryCard(String id, CardType cardType, CardRarity cardRarity) {
        super(id, -2, cardType, cardRarity, CardTarget.NONE);
        tags.add(CustomTags.POETRY);
        tags.add(CardTags.HEALING); // 不能被树枝等检索到
        poetryStrings = PoetryLoader.getStr(id);
        this.name = poetryStrings.NAME;
        this.dontTriggerOnUseCard = true; // 使用时不触发XX检查
        // 临时
        this.exhaust = true;
        this.isEthereal = true;

        CardStrings acs = CardCrawlGame.languagePack.getCardStrings(ID);
        TypeOverridePatch.TypeOverrideField.typeOverride.set(this, TYPE); // 更改卡图上的类型描述
        this.cantUseMessage = acs.EXTENDED_DESCRIPTION[0];
        toneManager = new ToneManager(this);
    }

    @Override
    public void onChoseThisOption() {
        addToBot(new MakeTempCardInHandAction(this, false));
    }

    @Override
    public void initializeDescription() {
        if (poetryStrings == null) {
            poetryStrings = PoetryLoader.getStr(cardID);
        }
        if (poetryStrings != null) {
            this.rawDescription = poetryStrings.DESCRIPTION;
        }
        super.initializeDescription();
    }

    /**
     * 在其他卡牌被打出时，会发生什么?
     */
    @Override
    public void receiveCardUsed(AbstractCard c) {
        toneManager.onPlayCard(c);
        setCostForTurn(toneManager.remainToken());
    }


    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);
    }

    @Override
    public void renderInLibrary(SpriteBatch sb) {
        super.renderInLibrary(sb);
    }

    /**
     * 在玩家头上可视化诗词赋曲
     */
    public void renderPoetryTip(SpriteBatch sb) {
        toneManager.render(sb);
    }


    /**
     * 整首诗都被打出了
     */
    public void finishFull() {
        addToBot(new MakeTempCardInHandAction(makeStatEquivalentCopy()));
    }

    /**
     * 打出了一句话
     */
    public void finishOnce() {
        addToBot(new MakeTempCardInHandAction(makeStatEquivalentCopy()));
    }
}
