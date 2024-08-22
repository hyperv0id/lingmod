package lingmod.util.card;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import lingmod.cards.AbstractPoetryCard;
import lingmod.cards.PoetryStrings;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static lingmod.ModCore.logger;
import static lingmod.ModCore.makePath;

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

    static void loadFont() {
        if (font == null) {
            FileHandle fontFile = Gdx.files.internal(FONT_PATH);
            FreeTypeFontGenerator g = new FreeTypeFontGenerator(fontFile);
            try {
                Method f = FontHelper.class.getDeclaredMethod("prepFont", FreeTypeFontGenerator.class, Float.TYPE, Boolean.TYPE);
                f.setAccessible(true);
                font = (BitmapFont) f.invoke(FontHelper.class.getName(), g, FONT_SIZE, true);
            } catch (Exception e) {
                font = FontHelper.tipBodyFont;
            }
        }
    }

    public void render(SpriteBatch sb) {
        AbstractPlayer p = AbstractDungeon.player;
        float x = p.drawX - p.hb_w / 2;
        float y = p.drawY + p.hb_h;
        FontHelper.renderSmartText(sb, FontHelper.tipBodyFont, toSmartText(), x, y, Color.WHITE);
    }

    String toSmartText() {
        if (tipStrCache.isEmpty()) {
            String testText = tokens.get(idx_1);
            StringBuilder sb = new StringBuilder();
            int now = 0;
            for (; now < idx_2; now++) {
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
                    case BOTH:
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

    public int remainToken() {
        return tokens.get(idx_1).length() - idx_2;
    }

    public char peek() {
        char c = tokens.get(idx_1).charAt(idx_2);
        return c;
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
        return Tone.BOTH;
    }

    private void next() {
        idx_2++;
        if (idx_2 >= tokens.get(idx_1).length()) {
            idx_2 = 0;
            // Finished Verse
            idx_1++;
            if (idx_1 >= tokens.size()) {
                idx_1 = 0;
                owner.finishFull();
            } else {
                owner.finishOnce();
            }
        }
        tipStrCache = "";
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
        } else if (this.peekTone() == Tone.BOTH) {
            this.next();
        }
    }
}
