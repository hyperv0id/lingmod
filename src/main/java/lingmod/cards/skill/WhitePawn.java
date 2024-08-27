package lingmod.cards.skill;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.actions.TransformDrawPileAction;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.CardConfig;
import lingmod.interfaces.Credit;
import lingmod.util.Wiz;

import static lingmod.ModCore.makeID;

/**
 * 白子：防6
 */
@Credit(username = "DaylightAllure", platform = Credit.PIXIV, link = "https://www.pixiv.net/artworks/105018495")
@CardConfig(block = 4, magic = 1)
public class WhitePawn extends AbstractEasyCard {
    public final static String ID = makeID(WhitePawn.class.getSimpleName());

    public WhitePawn() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        this.baseMagicNumber = (int) Wiz.allCardsInBattle(false).stream().filter(c -> c.cardID.equals(ID)).count();
        super.calculateCardDamage(mo);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < magicNumber; i++) {
            blck();
        }
        if (Wiz.isStanceNell()) {
            //            if (p.drawPile.isEmpty())
            //                addToBot(new EmptyDeckShuffleAction()); // 触发洗牌
            addToBot(new TransformDrawPileAction(this, cardStrings.EXTENDED_DESCRIPTION[0]));
        }
    }

    @Override
    public void upp() {
        upgradeBlock(2);
    }
}
// "lingmod:WhitePawn": {
// "NAME": "WhitePawn",
// "DESCRIPTION": ""
// }