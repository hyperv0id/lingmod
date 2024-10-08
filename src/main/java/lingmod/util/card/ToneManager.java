package lingmod.util.card;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.FontHelper;
import lingmod.cards.AbstractPoetryCard;
import lingmod.cards.PoetryStrings;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static lingmod.ModCore.logger;
import static lingmod.ModCore.makePath;
import static lingmod.util.Wiz.addToBotAbstract;

/**
 * 管理诗歌的韵律
 */
public class ToneManager {
    public static final String FONT_PATH = makePath("华文行楷.ttf");
    static final float FONT_SIZE = 22.0F;
    static BitmapFont font;
    private final AbstractPoetryCard owner;
    public List<String> tokens; // 诗歌内容
    public List<String> toneTokens; // 平仄内容
    public int idx_1, idx_2;
    public int totalProgress = 0;
    String tipStrCache = "";

    public ToneManager(AbstractPoetryCard card) {
        PoetryStrings vs = card.poetryStrings;
        this.owner = card;
        tokens = Arrays.asList(vs.CONTENT);
        toneTokens = Arrays.asList(vs.TONE_PATTERN);
        idx_1 = 0;
        idx_2 = 0;
        loadFont();
    }

    public ToneManager(AbstractPoetryCard card, String[] content, String[] tone) {
        this.owner = card;
        tokens = Arrays.asList(content);
        toneTokens = Arrays.asList(tone);
        idx_1 = 0;
        idx_2 = 0;
        loadFont();
    }

    static void loadFont() {
        if (font == null) {
            FileHandle fontFile = Gdx.files.internal(FONT_PATH);
            FreeTypeFontGenerator g = new FreeTypeFontGenerator(fontFile);
            try {
                Method f = FontHelper.class.getDeclaredMethod("prepFont", FreeTypeFontGenerator.class, Float.TYPE,
                        Boolean.TYPE);
                f.setAccessible(true);
                font = (BitmapFont) f.invoke(FontHelper.class.getName(), g, FONT_SIZE, true);
            } catch (Exception e) {
                font = FontHelper.tipBodyFont;
            }
        }
    }

    public String toSmartText() {
        if (tipStrCache.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            int now = 0;
            for (; now < idx_2; now++) {
                if (isLastVerse()) {
                    sb.append(" #y");
                }
                sb.append(tokens.get(idx_1).charAt(now));
                sb.append(" ");
            }

            for (int i = now; i < tokens.get(idx_1).length(); i++) {
                Tone tone = toneFromChar(toneTokens.get(idx_1).charAt(i));
                char ch = tokens.get(idx_1).charAt(i);
                switch (tone) {
                    case LEVEL:
                        sb.append("#b");
                        break;
                    case OBLIQUE:
                        sb.append("#r");
                        break;
                    case OTHER:
                        break;
                }
                sb.append(ch);
                sb.append(" ");
            }
            tipStrCache = sb.toString();
            logger.info("Poetry Changed: " + tipStrCache);
        }
        return tipStrCache;
    }

    private boolean isLastVerse() {
        return idx_1 == tokens.size() - 1;
    }

    public int remainToken() {
        return tokens.get(idx_1).length() - idx_2;
    }

    public int remainProgress() {
        if (idx_1 >= tokens.size()) return 0;
        int ret = tokens.get(idx_1).length() - idx_2;
        for (int i1 = idx_1 + 1; i1 < tokens.size(); i1++) {
            ret += tokens.get(i1).length();
        }
        return ret;
    }

    public char peek() {
        return tokens.get(idx_1).charAt(idx_2);
    }

    public Tone peekTone() {
        char c = toneTokens.get(idx_1).charAt(idx_2);
        return toneFromChar(c);
    }

    public Tone toneFromChar(char tk) {
        if (tk == '平') {
            return Tone.LEVEL;
        } else if (tk == '仄') {
            return Tone.OBLIQUE;
        }
        return Tone.OTHER;
    }

    public void next() {
        totalProgress++;
        idx_2++;
        if (idx_2 >= tokens.get(idx_1).length()) {
            idx_2 = 0;
            // Finished Verse
            idx_1++;
            if (idx_1 >= tokens.size()) {
                idx_1 = 0;
                // 必须延迟添加
                owner.applyPowers_poetry();
                addToBotAbstract(owner::onFinishFull);
            } else {
                // 必须延迟添加
                owner.applyPowers_poetry();
                addToBotAbstract(owner::onFinishOnce);
            }
        }
        tipStrCache = "";
        if (peekTone() != Tone.LEVEL && peekTone() != Tone.OBLIQUE)
            next();
    }

    public Tone nextTone() {
        Tone ret = peekTone();
        next();
        return ret;
    }

    /**
     * 根据打出的卡牌，判断是否前进
     */
    public void onPlayCard(AbstractCard c) {
        if (c.type == AbstractCard.CardType.ATTACK && this.peekTone() == Tone.OBLIQUE) {
            // 攻击牌对应"仄声"
            this.next();
        } else if (c.type == AbstractCard.CardType.SKILL && this.peekTone() == Tone.LEVEL) {
            // 技能牌对应"平声"
            this.next();
        } else if (c.type != AbstractCard.CardType.ATTACK && c.type != AbstractCard.CardType.SKILL) {
            // 其他牌，BOTH
            this.next();
        } else if (this.peekTone() == Tone.OTHER) {
            this.next();
        }
    }

    public void skipVerse() {
        if (tokens.size() == 1) {
            boolean i20 = idx_2 == 0;
            while (idx_2 != 0 || i20) {
                next();
                i20 = false;
            }
        } else {
            int i1 = idx_1;
            while (idx_1 == i1) {
                next();
            }
        }
//        idx_1++;
//        owner.onFinishOnce();
//        idx_2 = 0;
//        if (idx_1 >= tokens.size()) {
//            owner.onFinishFull();
//            idx_1 = 0;
//        }
//        tipStrCache = "";
    }
}
