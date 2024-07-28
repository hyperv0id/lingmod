package lingmod.cards;

import static lingmod.ModCore.makeID;

import java.util.Arrays;
import java.util.List;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import lingmod.patch.TypeOverridePatch;
import lingmod.util.CustomTags;
import lingmod.util.VerseLoader;

/**
 * 词牌：规定了整场战斗的格调，按照格调打出额外效果
 */
public abstract class AbstractVerseCard extends AbstractEasyCard {
    public final static String ID = makeID(AbstractVerseCard.class.getSimpleName());
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ID);
    public static final String TYPE = uiStrings.TEXT[0]; // 词牌
    public VerseStrings verseStrings;
    public ToneManager toneManager;

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
        this.name = verseStrings.NAME;
        this.dontTriggerOnUseCard = true; // 使用时不触发XX检查
        this.selfRetain = true; // 保留

        CardStrings acs = CardCrawlGame.languagePack.getCardStrings(ID);
        TypeOverridePatch.TypeOverrideField.typeOverride.set(this, TYPE); // 更改卡图上的类型描述
        this.cantUseMessage = acs.EXTENDED_DESCRIPTION[0];
        toneManager = new ToneManager(verseStrings);

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

    /**
     * 在其他卡牌被打出时，会发生什么?
     * TODO: 根据打出的平仄修改费用，英文：level tone、oblique tone
     */
    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        if (c.type == CardType.ATTACK && toneManager.peekTone() == Tone.OBLIQUE) {
            // 攻击牌对应"仄声"
            toneManager.next();
        } else if (c.type == CardType.SKILL && toneManager.peekTone() == Tone.LEVEL) {
            // 技能牌对应"平声"
            toneManager.next();
        } else if (c.type != CardType.ATTACK && c.type != CardType.SKILL) {
            // 其他牌，BOTH
            toneManager.next();
        } else if (toneManager.peekTone() == Tone.BOTH) {
            toneManager.next();
        }

        this.cost = toneManager.remainToken();

    }

    protected enum Tone {
        // 平声，仄声，都可以
        LEVEL, OBLIQUE, BOTH
    }

    protected class ToneManager {
        public List<String> tokens;
        public List<String> toneTokens;
        public int idx_1, idx_2;

        public ToneManager(VerseStrings vs) {
            tokens = Arrays.asList(vs.CONTENT);
            toneTokens = Arrays.asList(vs.TONE_PATTERN);
            idx_1 = 0;
            idx_2 = 0;
        }

        public int remainToken() {
            return tokens.get(idx_1).length() - idx_2;
        }

        public char peek() {
            char tk = tokens.get(idx_1).charAt(idx_2);
            return tk;
        }

        public Tone peekTone() {
            char tk = toneTokens.get(idx_1).charAt(idx_2);
            if (tk == '平') {
                return Tone.LEVEL;
            } else if (tk == '仄') {
                return Tone.OBLIQUE;
            }
            return Tone.BOTH;
        }

        private void __next() {
            idx_2++;
            if (idx_2 >= tokens.get(idx_1).length()) {
                idx_2 = 0;

                idx_1++;
                if (idx_1 >= tokens.size())
                    idx_1 = 0;
            }
        }

        public char next() {
            char ret = peek();
            __next();
            return ret;
        }

        public Tone nextTone() {
            Tone ret = peekTone();
            __next();
            return ret;
        }
    }
}
