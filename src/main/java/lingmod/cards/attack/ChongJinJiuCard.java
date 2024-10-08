package lingmod.cards.attack;

import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.cards.AbstractEasyCard;
import lingmod.cards.mod.MirrorMod;
import lingmod.interfaces.Credit;

import static lingmod.ModCore.makeID;

/**
 * 重进酒：打出卡牌时消耗，并替换为其复制
 */
@Credit(username = "明日方舟", platform = "鹰角网络", link = "https://prts.wiki/w/令")
public class ChongJinJiuCard extends AbstractEasyCard {

    public final static String ID = makeID(ChongJinJiuCard.class.getSimpleName());

    public ChongJinJiuCard() {
        super(ID, 1, CardType.SKILL, CardRarity.BASIC, CardTarget.ENEMY);
        this.baseMagicNumber = 1;
        CardModifierManager.addModifier(this, new ExhaustMod());
        CardModifierManager.addModifier(this, new MirrorMod(exhaust));
        this.initializeDescription();
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return false;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public void upp() {
        CardModifierManager.removeModifiersById(this, ExhaustMod.ID, true);
        CardModifierManager.removeModifiersById(this, MirrorMod.ID, true);
        CardModifierManager.addModifier(this, new MirrorMod(this.exhaust));
    }
}
