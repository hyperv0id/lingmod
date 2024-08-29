package lingmod.ui;

import static lingmod.ModCore.logger;
import static lingmod.ModCore.makeID;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;

import lingmod.cards.AbstractPoetryCard;
import lingmod.powers.PoeticMoodPower;
import lingmod.util.Wiz;

public class PoetryOrb extends AbstractOrb {
    public static final String ID = makeID(PoetryOrb.class.getSimpleName());
    public static final UIStrings uis = CardCrawlGame.languagePack.getUIString(ID);

    public AbstractPoetryCard card;
    private static final int FONT_SIZE = 22;

    public PoetryOrb(AbstractPoetryCard card) {
        super();
        name = uis.TEXT[0];
        description = uis.TEXT[1];
        this.card = card;
    }

    @Override
    public void updateDescription() {
        description = uis.TEXT[1];
    }

    @Override
    public void onEvoke() {
    }

    @Override
    public void update() {
        super.update();
        if (this.hb.hovered && InputHelper.justClickedLeft) {
            logger.info("PoetryOrb Left Clicked");
            Wiz.addToBotAbstract(() -> {
                AbstractPower poet = AbstractDungeon.player.getPower(PoeticMoodPower.ID);
                int pc = card.remainCost();
                if (poet != null && poet.amount >= pc) {
                    Wiz.att(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, PoeticMoodPower.ID,
                            pc));
                    card.nextVerse();
                    logger.info("Succeed poetry");
                } else
                    logger.info("Failed Poetry");
            });
        } else if (hb.hovered && InputHelper.justClickedRight) {
            logger.info("PoetryOrb Right Clicked");
            CardGroup grp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            grp.addToTop(card);
            AbstractDungeon.gridSelectScreen.open(grp, 0, uis.TEXT[2], true);
        }
    }

    @Override
    public AbstractOrb makeCopy() {
        return null;
    }

    @Override
    public void render(SpriteBatch sb) {
        hb.render(sb);
        if (card != null) {
            AbstractPlayer p = AbstractDungeon.player;
            float x = p.drawX - p.hb_w + FONT_SIZE + 1.5F;
            float y = p.drawY + p.hb_h + FONT_SIZE * 1.5F;
            String text = card.getPoetryTip();

            FontHelper.renderSmartText(sb, FontHelper.tipBodyFont, text, cX - 48, cY + FONT_SIZE, Color.WHITE);
            // FontHelper.renderSmartText(sb, FontHelper.tipBodyFont, text, x, y,
            // Color.WHITE);
        }
        // FontHelper.renderSmartText(sb, FontHelper.tipBodyFont, card.getPoetryTip(),
        // cX - 48, cY - 24, Color
        // .WHITE);
    }

    @Override
    public void playChannelSFX() {
    }
}
