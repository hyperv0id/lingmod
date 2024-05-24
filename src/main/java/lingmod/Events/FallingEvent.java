package lingmod.Events;

import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;

import lingmod.ModCore;
public class FallingEvent extends AbstractImageEvent {
    public static final String ID = makeID(FallingEvent.class.getSimpleName());
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;
    static {
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
    }
    private static final String DIALOG_1 = DESCRIPTIONS[0];
    private static final String DIALOG_2 = DESCRIPTIONS[1];
    private FallingEvent.CurScreen screen = FallingEvent.CurScreen.INTRO;
    private boolean attack;
    private boolean skill;
    private boolean power;
    private AbstractCard attackCard;
    private AbstractCard skillCard;
    private AbstractCard powerCard;

    public FallingEvent() {
        super(NAME, DIALOG_1, ModCore.makeImagePath("events/Falling.png"));
        this.setCards();
        this.imageEventText.setDialogOption(OPTIONS[0]);
    }


    public void onEnterRoom() {
        if (Settings.AMBIANCE_ON) {
            CardCrawlGame.sound.play("EVENT_FALLING");
        }
    }

    private void setCards() {
        this.attack = CardHelper.hasCardWithType(CardType.ATTACK);
        this.skill = CardHelper.hasCardWithType(CardType.SKILL);
        this.power = CardHelper.hasCardWithType(CardType.POWER);
        if (this.attack) {
            this.attackCard = CardHelper.returnCardOfType(CardType.ATTACK, AbstractDungeon.miscRng);
        }

        if (this.skill) {
            this.skillCard = CardHelper.returnCardOfType(CardType.SKILL, AbstractDungeon.miscRng);
        }

        if (this.power) {
            this.powerCard = CardHelper.returnCardOfType(CardType.POWER, AbstractDungeon.miscRng);
        }
    }

    protected void buttonEffect(int buttonPressed) {
        switch(this.screen) {
            case INTRO:
                this.screen = FallingEvent.CurScreen.CHOICE;
                this.imageEventText.updateBodyText(DIALOG_2);
                this.imageEventText.clearAllDialogs();
                if (!this.skill && !this.power && !this.attack) {
                    this.imageEventText.setDialogOption(OPTIONS[8]);
                } else {
                    if (this.skill) {
                        this.imageEventText
                            .setDialogOption(OPTIONS[1] + FontHelper.colorString(this.skillCard.name, "r"), this.skillCard.makeStatEquivalentCopy());
                    } else {
                        this.imageEventText.setDialogOption(OPTIONS[2], true);
                    }

                    if (this.power) {
                        this.imageEventText
                            .setDialogOption(OPTIONS[3] + FontHelper.colorString(this.powerCard.name, "r"), this.powerCard.makeStatEquivalentCopy());
                    } else {
                        this.imageEventText.setDialogOption(OPTIONS[4], true);
                    }

                    if (this.attack) {
                        this.imageEventText
                            .setDialogOption(OPTIONS[5] + FontHelper.colorString(this.attackCard.name, "r"), this.attackCard.makeStatEquivalentCopy());
                    } else {
                        this.imageEventText.setDialogOption(OPTIONS[6], true);
                    }
                    this.imageEventText.setDialogOption(OPTIONS[9]); // 直接落地
                }
                break;
            case CHOICE:
                this.screen = FallingEvent.CurScreen.RESULT;
                this.imageEventText.clearAllDialogs();
                this.imageEventText.setDialogOption(OPTIONS[7]);
                switch(buttonPressed) {
                    case 0:
                        if (!this.skill && !this.power && !this.attack) {
                            this.imageEventText.updateBodyText(DESCRIPTIONS[5]);
                            logMetricIgnored("Falling");
                        } else {
                            this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                            AbstractDungeon.effectList.add(new PurgeCardEffect(this.skillCard));
                            AbstractDungeon.player.masterDeck.removeCard(this.skillCard);
                            logMetricCardRemoval("Falling", "Removed Skill", this.skillCard);
                        }

                        return;
                    case 1:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[3]);
                        AbstractDungeon.effectList.add(new PurgeCardEffect(this.powerCard));
                        AbstractDungeon.player.masterDeck.removeCard(this.powerCard);
                        logMetricCardRemoval("Falling", "Removed Power", this.powerCard);
                        return;
                    case 2:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[4]);
                        AbstractDungeon.effectList.add(new PurgeCardEffect(this.attackCard));
                        logMetricCardRemoval("Falling", "Removed Attack", this.attackCard);
                        AbstractDungeon.player.masterDeck.removeCard(this.attackCard);
                        return;
                    case 3:
                        AbstractPlayer p = AbstractDungeon.player;
                        CardCrawlGame.music.fadeOutTempBGM();
                        // TODO: 回到地图底层
                        // 扣一半血
                        AbstractDungeon.actionManager.addToBottom(new DamageAction(p, new DamageInfo(
                                p, p.currentHealth/2)));
                        this.openMap();
                        return;
                    default:
                        return;
                }
            default:
                this.openMap();
        }
    }



    private static enum CurScreen {
        INTRO,
        CHOICE,
        RESULT;

        private CurScreen() {
        }
    }
}
