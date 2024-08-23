package lingmod.cards.skill;

import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.AutoAdd;
import lingmod.actions.EasyXCostAction;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.Credit;

/**
 * 辞旧岁：丢弃所有手牌，每张造成 3X 点伤害
 * TODO: 变化所有手牌
 */
@AutoAdd.Ignore
@Credit(platform = Credit.PIXIV, username = "Chocolatte", link = "https://www.pixiv.net/artworks/95920468")
public class ChiJiuSui extends AbstractEasyCard {
    public static final String ID = makeID(ChiJiuSui.class.getSimpleName());

    public ChiJiuSui() {
        super(ID, -1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 2;
    }

    @Override
    public void upp() {
        upgradeDamage(1);
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        this.damage *= AbstractDungeon.player.hand.size();
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        damage = baseDamage * AbstractDungeon.player.hand.size();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster mo) {
        int amount = p.hand.size();
        addToBot(new DiscardAction(p, p, amount, false));
        addToBot(new EasyXCostAction(this, (effect, params) -> {
            for (int i = 0; i < effect + params[0]; i++)
                dmgTop(mo, AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
            return true;
        }, damage));
    }
}
