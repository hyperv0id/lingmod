package lingmod.cards;

import basemod.BaseMod;
import basemod.interfaces.OnCardUseSubscriber;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.patches.FlavorText;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.ModCore;
import lingmod.character.Ling;
import lingmod.interfaces.CopyField;
import lingmod.patch.TypeOverridePatch;
import lingmod.util.CustomTags;
import lingmod.util.PoetryLoader;
import lingmod.util.VoiceMaster;
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
    @CopyField
    protected boolean finishFull = false;

    protected CardType realType;


    public AbstractPoetryCard(String id, CardType cardType, CardRarity cardRarity, CardTarget target) {
        super(id, -2, cardType, cardRarity, target, Ling.Enums.LING_POEM_COLOR);

        realType = cardType;
        tags.add(CustomTags.POEM);
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
        addPoetryTip();
    }

    public void applyPowers_poetry() {
        super.applyPowers();
    }

    public void addPoetryTip() {
        // 改成展示诗词：
        if (poetryStrings.CONTENT != null && poetryStrings.CONTENT.length != 0) {
            Color textColor = Color.BLACK.cpy();
            Color.rgb888ToColor(textColor, 0xdfe4ea);
            Color boxColor = Color.BLACK.cpy();
            Color.rgb888ToColor(boxColor, 0x5976ba);
            FlavorText.AbstractCardFlavorFields.textColor.set(this, textColor);
            FlavorText.AbstractCardFlavorFields.boxColor.set(this, boxColor);
            FlavorText.AbstractCardFlavorFields.flavorBoxType.set(this, FlavorText.boxType.WHITE);
            String poetryString = String.join(" NL ", poetryStrings.CONTENT);
            FlavorText.AbstractCardFlavorFields.flavor.set(this, poetryString);
        }
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    /**
     * 诗词专用use方法
     */
    public abstract void use_p(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster);

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        VoiceMaster.attack();
        use_p(abstractPlayer, abstractMonster);
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

    @Override
    public void upp() {
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

    /**
     * 整首诗都被打出了
     */
    public void onFinishFull() {
        finishFull = true;
        addToBot(new MakeTempCardInHandAction(makeStatEquivalentCopy()));
        finishFull = false;
        ModCore.logger.info("{} Finished", name);
    }

    @Override
    public void triggerOnGlowCheck() {
        if (finishFull) this.glowColor = GOLD_BORDER_GLOW_COLOR.cpy();
        else this.glowColor = BLUE_BORDER_GLOW_COLOR.cpy();
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

    public void skipOnce() {
        toneManager.next();
    }

    public String getTypeText() {
        String text;
        switch (this.realType) {
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
