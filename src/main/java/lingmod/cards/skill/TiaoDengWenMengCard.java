package lingmod.cards.skill;

import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.ModCore;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.Credit;
import lingmod.util.CardHelper;

import static lingmod.ModCore.makeID;

/**
 * 挑灯问梦：随机获得2张梦
 */
@Credit(link = "https://www.pixiv.net/artworks/118238598", username = "feijiu", platform = "pixiv")
public class TiaoDengWenMengCard extends AbstractEasyCard {

    public static final String ID = makeID(TiaoDengWenMengCard.class.getSimpleName());

    public TiaoDengWenMengCard() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        CardModifierManager.addModifier(this, new ExhaustMod());
        this.baseMagicNumber = 2;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        for (int i = 0; i < magicNumber; i++) {
            AbstractCard c = CardHelper.getTrulyRandDreamInCombat();
            if (c == null)
                ModCore.logger.warn("Cannot found card with tag DREAM");
            else {
                if (this.upgraded) c.upgrade();
                addToBot(new MakeTempCardInHandAction(c));
            }
        }
    }

    @Override
    public void upp() {
        // CardModifierManager.removeModifiersById(this, ExhaustMod.ID, false);
    }
}
