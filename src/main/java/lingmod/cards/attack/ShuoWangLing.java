package lingmod.cards.attack;

import basemod.BaseMod;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.CardConfig;

import java.util.ArrayList;

import static lingmod.ModCore.makeID;

/**
 * 朔望：卡牌唯一/满：打50
 */
@CardConfig(damage = 50, magic = 1)
public class ShuoWangLing extends AbstractEasyCard {
    public final static String ID = makeID(ShuoWangLing.class.getSimpleName());

    public ArrayList<Integer> validCardNums = new ArrayList<>();

    public ShuoWangLing() {
        super(ID, 0, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        validCardNums.add(1); // 朔
        validCardNums.add(10); // 望
    }

    @Override
    public void triggerOnGlowCheck() {
        if (this.canUse(AbstractDungeon.player, null)) {
            this.glowColor = GOLD_BORDER_GLOW_COLOR;
        }
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        ArrayList<Integer> ok = new ArrayList<>(validCardNums);
        ok.add(BaseMod.MAX_HAND_SIZE); // 特判
        // 随机化无法打出的对话/提示
        String[] exStrings = cardStrings.EXTENDED_DESCRIPTION;
        int lenEx = exStrings.length;
        cantUseMessage = exStrings[MathUtils.random(1, lenEx - 1)];
        return ok.contains(p.hand.size());
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
    }

    @Override
    public void upp() {
        validCardNums.add(3); // 令
        upgradeDamage(10);
        String ling = cardStrings.EXTENDED_DESCRIPTION[0];
        this.name = cardStrings.NAME + ling;
        this.initializeTitle();
    }
}
// "lingmod:ShuoWangLing": {
// "NAME": "ShuoWangLing",
// "DESCRIPTION": ""
// }
