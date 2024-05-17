package lingmod.cutscenes;

import static lingmod.ModCore.makeID;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.GameCursor;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.cutscenes.NeowEye;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen.CurScreen;
import com.megacrit.cardcrawl.ui.DialogWord.AppearEffect;
import com.megacrit.cardcrawl.ui.DialogWord.WordColor;
import com.megacrit.cardcrawl.ui.DialogWord.WordEffect;
import com.megacrit.cardcrawl.ui.SpeechWord;

/**
 * LingNarrationScreen: 通关后 替代/增加 尼奥的台词
 */
public class LingNarrationScreen {
    private Color bgColor;
    private Color eyeColor;
    private NeowEye eye1;
    private NeowEye eye2;
    private NeowEye eye3;
    private int currentDialog = 0;
    private int clickCount = 0;
    private static float curLineWidth;
    private static int curLine;
    private static Scanner s;
    private static GlyphLayout gl;
    private static ArrayList<SpeechWord> words;
    private static CharacterStrings charStrings;
    private static final float CHAR_SPACING;
    private static final float LINE_SPACING;
    private BitmapFont font;
    private float x;
    private float y;
    private float wordTimer;
    private boolean textDone;
    private boolean fadingOut;
    private float fadeOutTimer;

    public LingNarrationScreen() {
        // CardCrawlGame.mainMenuScreen.neowNarrateScreen
        this.font = FontHelper.panelNameFont;
        this.x = (float) Settings.WIDTH / 2.0F;
        this.y = (float) Settings.HEIGHT / 2.0F;
        this.wordTimer = 1.0F;
        this.textDone = false;
        this.fadingOut = false;
        this.fadeOutTimer = 3.0F;
    }

    public void open() {
        this.fadingOut = false;
        this.fadeOutTimer = 3.0F;
        this.playSfx();
        s = new Scanner(charStrings.TEXT[0]);
        this.textDone = false;
        this.wordTimer = 1.0F;
        words.clear();
        curLineWidth = 0.0F;
        curLine = 0;
        this.currentDialog = 0;
        this.clickCount = 0;
        this.eye1 = new NeowEye(0);
        this.eye2 = new NeowEye(1);
        this.eye3 = new NeowEye(2);
        this.bgColor = new Color(320149504);
        this.eyeColor = new Color(1.0F, 1.0F, 1.0F, 0.0F);
    }

    public void update() {
        this.bgColor.a = MathHelper.slowColorLerpSnap(this.bgColor.a, 1.0F);
        this.eyeColor.a = this.bgColor.a;
        this.eye1.update();
        this.eye2.update();
        this.eye3.update();
        this.wordTimer -= Gdx.graphics.getDeltaTime();
        if (this.wordTimer < 0.0F && !this.textDone) {
            if (this.clickCount > 4) {
                this.wordTimer = 0.1F;
            } else {
                this.wordTimer += 0.4F;
            }

            if (Settings.lineBreakViaCharacter) {
                this.addWordCN();
            } else {
                this.addWord();
            }
        }

        Iterator<SpeechWord> var1 = words.iterator();

        while (var1.hasNext()) {
            SpeechWord w = (SpeechWord) var1.next();
            w.update();
        }

        if (InputHelper.justClickedLeft || CInputActionSet.select.isJustPressed()) {
            ++this.clickCount;
        }

        if (this.fadingOut) {
            if (this.clickCount > 4) {
                this.fadeOutTimer -= Gdx.graphics.getDeltaTime() * 3.0F;
            } else {
                this.fadeOutTimer -= Gdx.graphics.getDeltaTime();
            }

            if (this.fadeOutTimer < 0.0F) {
                this.fadeOutTimer = 0.0F;
                this.exit();
                return;
            }
        } else if ((InputHelper.justClickedLeft || CInputActionSet.select.isJustPressed() || this.clickCount > 3)
                && this.textDone) {
            ++this.currentDialog;
            if (this.currentDialog > 2) {
                this.fadingOut = true;
                return;
            }

            this.playSfx();
            s = new Scanner(charStrings.TEXT[this.currentDialog]);
            this.textDone = false;
            if (this.clickCount > 4) {
                this.wordTimer = 0.1F;
            } else {
                this.wordTimer = 0.3F;
            }

            words.clear();
            curLineWidth = 0.0F;
            curLine = 0;
        }

    }

    private void playSfx() {
        int roll = MathUtils.random(3);
        if (roll == 0) {
            CardCrawlGame.sound.playA("VO_NEOW_1A", -0.2F);
            CardCrawlGame.sound.playA("VO_NEOW_1A", -0.4F);
        } else if (roll == 1) {
            CardCrawlGame.sound.playA("VO_NEOW_1B", -0.2F);
            CardCrawlGame.sound.playA("VO_NEOW_1B", -0.4F);
        } else if (roll == 2) {
            CardCrawlGame.sound.playA("VO_NEOW_2A", -0.2F);
            CardCrawlGame.sound.playA("VO_NEOW_2A", -0.4F);
        } else {
            CardCrawlGame.sound.playA("VO_NEOW_2B", -0.2F);
            CardCrawlGame.sound.playA("VO_NEOW_2B", -0.4F);
        }

    }

    private void addWord() {
        if (s.hasNext()) {
            String word = s.next();
            if (word.equals("NL")) {
                ++curLine;
                Iterator<SpeechWord> var5 = words.iterator();

                while (var5.hasNext()) {
                    SpeechWord w = (SpeechWord) var5.next();
                    w.shiftY(LINE_SPACING);
                }

                curLineWidth = 0.0F;
                return;
            }

            gl.setText(this.font, word);
            float temp = 0.0F;
            Iterator<SpeechWord> var3;
            SpeechWord w;
            if (curLineWidth + gl.width > 9999.0F) {
                ++curLine;
                var3 = words.iterator();

                while (var3.hasNext()) {
                    w = (SpeechWord) var3.next();
                    w.shiftY(LINE_SPACING);
                }

                curLineWidth = gl.width + CHAR_SPACING;
                temp = -curLineWidth / 2.0F;
            } else {
                curLineWidth += gl.width;
                temp = -curLineWidth / 2.0F;
                var3 = words.iterator();

                while (var3.hasNext()) {
                    w = (SpeechWord) var3.next();
                    if (w.line == curLine) {
                        w.setX(this.x + temp);
                        gl.setText(this.font, w.word);
                        temp += gl.width + CHAR_SPACING;
                    }
                }

                curLineWidth += CHAR_SPACING;
                gl.setText(this.font, word + " ");
            }

            words.add(new SpeechWord(this.font, word, AppearEffect.FADE_IN, WordEffect.SLOW_WAVY, WordColor.WHITE,
                    this.x + temp, this.y - LINE_SPACING * (float) curLine, curLine));
        } else {
            this.textDone = true;
            s.close();
        }

    }

    private void addWordCN() {
        if (s.hasNext()) {
            String word = s.next();
            if (word.equals("NL")) {
                ++curLine;
                Iterator<SpeechWord> var7 = words.iterator();

                while (var7.hasNext()) {
                    SpeechWord w = (SpeechWord) var7.next();
                    w.shiftY(LINE_SPACING);
                }

                curLineWidth = 0.0F;
                return;
            }

            for (int i = 0; i < word.length(); ++i) {
                String tmp = Character.toString(word.charAt(i));
                gl.setText(this.font, tmp);
                float temp = 0.0F;
                Iterator<SpeechWord> var5;
                SpeechWord w;
                if (curLineWidth + gl.width > 9999.0F) {
                    ++curLine;
                    var5 = words.iterator();

                    while (var5.hasNext()) {
                        w = (SpeechWord) var5.next();
                        w.shiftY(LINE_SPACING);
                    }

                    curLineWidth = gl.width;
                    temp = -curLineWidth / 2.0F;
                } else {
                    curLineWidth += gl.width;
                    temp = -curLineWidth / 2.0F;
                    var5 = words.iterator();

                    while (var5.hasNext()) {
                        w = (SpeechWord) var5.next();
                        if (w.line == curLine) {
                            w.setX(this.x + temp);
                            gl.setText(this.font, w.word);
                            temp += gl.width;
                        }
                    }

                    gl.setText(this.font, tmp + " ");
                }

                words.add(new SpeechWord(this.font, tmp, AppearEffect.FADE_IN, WordEffect.SLOW_WAVY, WordColor.WHITE,
                        this.x + temp, this.y - LINE_SPACING * (float) curLine, curLine));
            }
        } else {
            this.textDone = true;
            s.close();
        }

    }

    private void exit() {
        GameCursor.hidden = false;
        CardCrawlGame.mainMenuScreen.lighten();
        CardCrawlGame.mainMenuScreen.screen = CurScreen.MAIN_MENU;
        CardCrawlGame.music.changeBGM("MENU");
    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.bgColor);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, (float) Settings.WIDTH, (float) Settings.HEIGHT);
        sb.setColor(this.eyeColor);
        this.eye1.renderRight(sb);
        this.eye1.renderLeft(sb);
        this.eye2.renderRight(sb);
        this.eye2.renderLeft(sb);
        this.eye3.renderRight(sb);
        this.eye3.renderLeft(sb);
        Iterator<SpeechWord> var2 = words.iterator();

        while (var2.hasNext()) {
            SpeechWord w = (SpeechWord) var2.next();
            w.render(sb);
        }

        if (this.fadingOut) {
            sb.setColor(new Color(0.0F, 0.0F, 0.0F, Interpolation.fade.apply(1.0F, 0.0F, this.fadeOutTimer / 3.0F)));
            sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, (float) Settings.WIDTH, (float) Settings.HEIGHT);
        }

    }

    static {
        charStrings = CardCrawlGame.languagePack
                .getCharacterString(makeID("LingHeartKill"));
        curLineWidth = 0.0F;
        curLine = 0;
        gl = new GlyphLayout();
        words = new ArrayList<SpeechWord>();
        CHAR_SPACING = 8.0F * Settings.scale;
        LINE_SPACING = 38.0F * Settings.scale;
    }
}
