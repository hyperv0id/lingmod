package lingmod.cards.attack;

import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DuplicationPower;
import lingmod.cards.AbstractEasyCard;
import lingmod.cards.mod.MirrorMod;
import lingmod.interfaces.CardConfig;

import static lingmod.ModCore.makeID;

/**
 * 重进酒：打出卡牌时消耗，并替换为其复制
 */
@CardConfig(poemAmount = 1)
public class ChongJinJiuCard extends AbstractEasyCard {

    public final static String ID = makeID(ChongJinJiuCard.class.getSimpleName());

    public ChongJinJiuCard() {
        super(ID, 1, CardType.SKILL, CardRarity.BASIC, CardTarget.ENEMY);
        this.baseMagicNumber = 1;
        CardModifierManager.addModifier(this, new ExhaustMod());
        CardModifierManager.addModifier(this, new MirrorMod(exhaust));
        this.initializeDescription();
    }

    // @Override
    // public void onPlayCard(AbstractCard c, AbstractMonster m) {
    //     super.onPlayCard(c, m);

    //     this.cardsToPreview = AbstractDungeon.actionManager.lastCard;
    //     lastCard = AbstractDungeon.actionManager.lastCard;
    // }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, m, new DuplicationPower(p, 1)));
    }

    @Override
    public void upp() {
        CardModifierManager.removeModifiersById(this, ExhaustMod.ID, true);
        CardModifierManager.removeModifiersById(this, MirrorMod.ID, true);
        CardModifierManager.addModifier(this, new MirrorMod(this.exhaust));
    }
}
