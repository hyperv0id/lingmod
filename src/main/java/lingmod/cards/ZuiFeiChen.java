package lingmod.cards;

import static lingmod.ModCore.logger;
import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import lingmod.powers.WinePower;
import lingmod.util.CustomTags;

public class ZuiFeiChen extends AbstractEasyCard {

    public static final String NAME = ZuiFeiChen.class.getSimpleName();
    public final static String ID = makeID(NAME);
    public static final CardType TYPE = CardType.ATTACK;

    public static final String IMG_1 = getCardTextureString(NAME, TYPE);
    public static final String IMG_2 = getCardTextureString(NAME + "_0", TYPE);

    protected boolean isLastWine = false;

    public ZuiFeiChen() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        this.baseMagicNumber = 1;
        this.initializeDescription();
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }

    @Override
    public void triggerOnCardPlayed(AbstractCard cardPlayed) {
        super.triggerOnCardPlayed(cardPlayed);
        logger.info("=========check triggered");
        if (cardPlayed.hasTag(CustomTags.WINE)) {
            isLastWine = true;
            this.loadCardImage(IMG_2);
        } else {
            // 如果不是酒，那么
            isLastWine = false;
            this.loadCardImage(IMG_1);
        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        if (isLastWine) {
            // 如果是酒，那么翻倍你的酒
            AbstractPower wine = AbstractDungeon.player.powers.stream().filter(po -> po.ID.equals(WinePower.POWER_ID))
                    .findFirst().orElse(null);
            if (wine != null) {
                addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer,
                        new WinePower(abstractPlayer, wine.amount * magicNumber)));
            }
        } else {
            addToBot(new DrawCardAction(magicNumber));
        }
    }
}