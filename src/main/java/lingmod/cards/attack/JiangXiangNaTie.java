package lingmod.cards.attack;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.cards.AbstractEasyCard;
import lingmod.cards.mod.WineMod;
import lingmod.interfaces.CardConfig;
import lingmod.monsters.AbsSummonMonster;
import lingmod.util.CustomTags;

import static lingmod.ModCore.makeID;

@CardConfig(damage = 16)
public class JiangXiangNaTie extends AbstractEasyCard {
    public final static String ID = makeID(JiangXiangNaTie.class.getSimpleName());

    public JiangXiangNaTie() {
        super(ID, 2, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        CardModifierManager.addModifier(this, new WineMod(3));
        tags.add(CustomTags.WINE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        if (m instanceof AbsSummonMonster) {
            addToBotAbstract(() -> {
                m.increaseMaxHp(this.damage, true);
            });
        }
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
