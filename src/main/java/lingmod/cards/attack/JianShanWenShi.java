package lingmod.cards.attack;

import basemod.BaseMod;
import basemod.interfaces.OnPlayerTurnStartSubscriber;
import basemod.interfaces.PostExhaustSubscriber;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.CardConfig;
import lingmod.interfaces.Credit;

import static lingmod.ModCore.makeID;

/**
 * 见山问石：如果消耗过牌，抽一，[E]
 */
@CardConfig(damage = 7, magic = 1)
@Credit(username = "阿尼鸭Any-a", platform = Credit.LOFTER, link = "https://anyaaaaa.lofter.com/post/1d814764_2b82a4712")
public class JianShanWenShi extends AbstractEasyCard implements PostExhaustSubscriber, OnPlayerTurnStartSubscriber {

    public final static String ID = makeID(JianShanWenShi.class.getSimpleName());

    public static boolean exhaustedThisTurn = false; // 本回合是否消耗过牌
    public static JianShanWenShi __instance = null;

    public JianShanWenShi() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        if (__instance == null) {
            __instance = this;
            BaseMod.subscribe(this);
        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        if (exhaustedThisTurn) {
            addToBot(new GainEnergyAction(magicNumber));
            addToBot(new DrawCardAction(magicNumber));
        }
    }

    @Override
    public void upp() {
        upgradeDamage(4);
    }

    @Override
    public void receivePostExhaust(AbstractCard card) {
        if (card.dontTriggerOnUseCard) return;
        exhaustedThisTurn = true;
    }

    @Override
    public boolean shouldGlow_Gold() {
        return exhaustedThisTurn;
    }

    @Override
    public void receiveOnPlayerTurnStart() {
        exhaustedThisTurn = false;
    }
}
// "lingmod:JianShanWenShi": {
// "NAME": "JianShanWenShi",
// "DESCRIPTION": ""
// }