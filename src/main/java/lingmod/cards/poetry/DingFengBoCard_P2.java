package lingmod.cards.poetry;

import basemod.AutoAdd;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.actions.watcher.SkipEnemiesTurnAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.WhirlwindEffect;
import lingmod.interfaces.Credit;
import lingmod.util.card.ToneManager;

import static lingmod.ModCore.makeID;

/**
 * 定风波：每完成半阙诗后，结束你的回合，获得额外回合
 */
@AutoAdd.Ignore
@Credit(username = "明日方舟", platform = "bilibili", link = "https://www.bilibili.com/video/BV1MR4y1a7KX")
public class DingFengBoCard_P2 extends DingFengBoCard {
    public static final String ID = makeID(DingFengBoCard_P2.class.getSimpleName());

    public DingFengBoCard_P2() {
        super(ID);
        String img = getCardTextureString(DingFengBoCard.class.getSimpleName(), this.type);
        loadCardImage(img);
    }

    @Override
    public void use_p(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        toneManager = new ToneManager(this);
        this.addToBot(new VFXAction(new WhirlwindEffect(new Color(1.0F, 0.9F, 0.4F, 1.0F), true)));
        this.addToBot(new SkipEnemiesTurnAction());
        this.addToBot(new PressEndTurnButtonAction());
    }

}