package lingmod.cards.attack;

import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.BaseMod;
import basemod.interfaces.PostExhaustSubscriber;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.CardConfig;
import lingmod.interfaces.CopyField;

@CardConfig(damage = 7, magic = 4)
public class JianShanWenShi extends AbstractEasyCard implements PostExhaustSubscriber {

    public final static String ID = makeID(JianShanWenShi.class.getSimpleName());

    @CopyField
    public boolean exhaustedThisTurn = false; // 本回合是否消耗过牌

    public JianShanWenShi() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);

        BaseMod.subscribe(this);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        if (exhaustedThisTurn) {
            addToBot(new GainEnergyAction(1));
        }
    }

    @Override
    public void upp() {
        upgradeDamage(magicNumber);
    }

    @Override
    public void receivePostExhaust(AbstractCard arg0) {
        this.exhaustedThisTurn = true;
    }

    @Override
    public void atTurnStartPreDraw() {
        super.atTurnStartPreDraw();
        this.exhaustedThisTurn = false;
    }
}
// "lingmod:JianShanWenShi": {
// "NAME": "JianShanWenShi",
// "DESCRIPTION": ""
// }