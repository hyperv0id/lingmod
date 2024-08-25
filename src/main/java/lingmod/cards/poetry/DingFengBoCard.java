package lingmod.cards.poetry;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.actions.watcher.SkipEnemiesTurnAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.WhirlwindEffect;
import lingmod.cards.AbstractPoetryCard;
import lingmod.interfaces.Credit;
import lingmod.util.PoetryLoader;
import lingmod.util.card.ToneManager;

import static lingmod.ModCore.makeID;

/**
 * 定风波：每完成半阙诗后，结束你的回合，获得额外回合
 */
@Credit(username = "明日方舟", platform = "bilibili", link = "https://www.bilibili.com/video/BV1MR4y1a7KX")
public class DingFengBoCard extends AbstractPoetryCard {
    public static final String ID = makeID(DingFengBoCard.class.getSimpleName());

    public boolean skipTurn = false;
    public boolean isSecondHalf = false;

    public DingFengBoCard() {
        super(ID, CardType.SKILL, CardRarity.BASIC, CardTarget.SELF);
    }

    @Override
    public void use_p(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        if (skipTurn) {
            toneManager = new ToneManager(this);
            this.addToBot(new VFXAction(new WhirlwindEffect(new Color(1.0F, 0.9F, 0.4F, 1.0F), true)));
            this.addToBot(new SkipEnemiesTurnAction());
            this.addToBot(new PressEndTurnButtonAction());
        } else {
            addToBot(new GainEnergyAction(1));
        }
        skipTurn = false;
    }

    @Override
    public void onFinishFull() {
        super.onFinishFull();
        skipTurn = true;
        if (isSecondHalf) {
            poetryStrings = PoetryLoader.getStr(ID + "_1");
        } else {
            poetryStrings = PoetryLoader.getStr(ID);
        }
        toneManager = new ToneManager(this);
    }

    @Override
    public AbstractCard makeCopy() {
        DingFengBoCard cp = new DingFengBoCard();
        cp.skipTurn = this.skipTurn;
        cp.isSecondHalf = this.isSecondHalf;
        return cp;
    }
}