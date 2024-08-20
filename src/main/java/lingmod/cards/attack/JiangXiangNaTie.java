package lingmod.cards.attack;

import basemod.AutoAdd;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import lingmod.cards.AbstractEasyCard;
import lingmod.cards.mod.WineMod;
import lingmod.interfaces.CardConfig;
import lingmod.util.CustomTags;

import static lingmod.ModCore.makeID;

@CardConfig(damage = 16)
@AutoAdd.Ignore
public class JiangXiangNaTie extends AbstractEasyCard {
    public final static String ID = makeID(JiangXiangNaTie.class.getSimpleName());

    public JiangXiangNaTie() {
        super(ID, 2, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        CardModifierManager.addModifier(this, new WineMod(3));
        tags.add(CustomTags.WINE);
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        AbstractPower vigor = AbstractDungeon.player.getPower(VigorPower.POWER_ID);
        if (vigor != null) {
            int amt = vigor.amount;
            int realDamage = baseDamage;
            this.baseDamage += amt;
            super.calculateCardDamage(mo);
            baseDamage = realDamage;
        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
    }

    @Override
    public void upp() {
        upgradeDamage(4);
    }
}
// "lingmod:JiangXiangNaTie": {
// "NAME": "JiangXiangNaTie",
// "DESCRIPTION": ""
// }
