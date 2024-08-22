package lingmod.cards;

import basemod.BaseMod;
import basemod.interfaces.OnCardUseSubscriber;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import lingmod.ModCore;
import lingmod.character.Ling;
import lingmod.patch.TypeOverridePatch;
import lingmod.util.CustomTags;
import lingmod.util.PoetryLoader;
import lingmod.util.Wiz;
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
        TypeOverridePatch.TypeOverrideField.typeOverride.set(this, TYPE + "-" + getTypeText()); // 更改卡图上的类型描述
        this.cantUseMessage = acs.EXTENDED_DESCRIPTION[0];
        toneManager = new ToneManager(this);
        BaseMod.subscribe(this);
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

    public String getPoetryTip() {
        return toneManager.toSmartText();
    }

    @Override
    public void receiveCardUsed(AbstractCard c) {
        if (!Wiz.isPlayerLing()) {
            BaseMod.unsubscribeLater(this);
            return;
        }
        Ling p = (Ling) AbstractDungeon.player;
        if (p.getPoetryCard() != this) {
            BaseMod.unsubscribeLater(this);
            return;
        }
        toneManager.onPlayCard(c);
    }


    @Override
    public void renderInLibrary(SpriteBatch sb) {
        super.renderInLibrary(sb);
    }

    /**
     * 整首诗都被打出了
     */
    public void onFinishFull() {
        ModCore.logger.info(name + " Finished");
    }

    /**
     * 打出了一句话
     */
    public void onFinishOnce() {
        addToBot(new MakeTempCardInHandAction(makeStatEquivalentCopy()));
    }

    public int remainCost() {
        return toneManager.remainToken();
    }

    public void nextVerse() {
        toneManager.skipVerse();
    }

    public String getTypeText() {
        String text;
        switch (type) {
            case ATTACK:
                text = TEXT[0];
                break;
            case CURSE:
                text = TEXT[3];
                break;
            case STATUS:
                text = TEXT[7];
                break;
            case SKILL:
                text = TEXT[1];
                break;
            case POWER:
                text = TEXT[2];
                break;
            default:
                text = TEXT[5];
        }
        return text;
    }
}
