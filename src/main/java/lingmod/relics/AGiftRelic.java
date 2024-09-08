package lingmod.relics;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import lingmod.actions.FastApplyPower_Action;

import static lingmod.ModCore.makeID;

/**
 * 金刚杵的Alt：战斗开始时，所有敌人失去 1 力量。二哥在剧情里送的，似乎能止戈
 */
@AutoAdd.Ignore
public class AGiftRelic extends AbstractEasyRelic {
    public static final String ID = makeID(AGiftRelic.class.getSimpleName());

    public AGiftRelic() {
        super(ID, RelicTier.SPECIAL, LandingSound.FLAT);
    }

    @Override
    public void atBattleStart() {
        super.atBattleStart();
        AbstractPlayer player = AbstractDungeon.player;
        for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters)
            addToBot(new FastApplyPower_Action(mo, player, new StrengthPower(mo, -1)));
    }
}
