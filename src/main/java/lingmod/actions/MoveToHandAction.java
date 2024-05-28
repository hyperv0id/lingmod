package lingmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class MoveToHandAction extends AbstractGameAction {
    private static float DRAW_PILE_X;
    private static float DRAW_PILE_Y;
    private static float DISCARD_PILE_X;
    protected CardGroup group = null;
    private AbstractCard c;

    public MoveToHandAction(AbstractCard card, CardGroup group) {
        this.c = card;
        this.group = group;
        duration = DEFAULT_DURATION;
    }

    @Override
    public void update() {
        if(this.duration == DEFAULT_DURATION){
            c.unhover();
            c.lighten(true);
            c.setAngle(0.0F);
            c.drawScale = 0.12F;
            c.targetDrawScale = 0.75F;
            c.current_x = DRAW_PILE_X;
            c.current_y = DRAW_PILE_Y;
            group.removeCard(c);
            AbstractDungeon.player.hand.addToTop(c);
            AbstractDungeon.player.hand.refreshHandLayout();
            AbstractDungeon.player.hand.applyPowers();
        }
        tickDuration();
    }

    static {
        DRAW_PILE_X = (float) Settings.WIDTH * 0.04F;
        DRAW_PILE_Y = 50.0F * Settings.scale;
        DISCARD_PILE_X = (int) ((float) Settings.WIDTH + AbstractCard.IMG_WIDTH_S / 2.0F + 100.0F * Settings.scale);
    }
}
