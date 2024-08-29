package lingmod.relics;

import static lingmod.ModCore.logger;
import static lingmod.ModCore.makeID;

import java.util.ArrayList;
import java.util.stream.Collectors;

import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import lingmod.interfaces.SummonedMonster;
import lingmod.util.Wiz;

/**
 * 打出牌后对随机单位造成 1-12点伤害，生命值越高越容易受到伤害。玩家受到的伤害减半
 */
public class SuiNu extends AbstractEasyRelic {
    public static final String ID = makeID(SuiNu.class.getSimpleName());

    public SuiNu() {
        super(ID, RelicTier.SPECIAL, LandingSound.FLAT);
    }

    @Override
    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction) {
        super.onUseCard(targetCard, useCardAction);
        // ArrayList<AbstractCreature> creatures = new ArrayList<>();
        ArrayList<AbstractCreature> creatures = AbstractDungeon.getMonsters().monsters.stream()
                .filter(mo -> !(mo instanceof SummonedMonster))
                .filter(mo -> !mo.isDeadOrEscaped()).collect(Collectors.toCollection(ArrayList::new));
        creatures.add(Wiz.adp());
        ArrayList<Integer> integers =
                creatures.stream().map(c -> c.currentHealth).collect(Collectors.toCollection(ArrayList::new));
        int idx = Wiz.weightedRandSelect(integers);
        AbstractCreature target = creatures.get(idx);
        logger.info("SuiNu selected target" + target);
        DamageInfo info = new DamageInfo(target, AbstractDungeon.miscRng.random(1, 12), DamageInfo.DamageType.THORNS);
        addToBot(new DamageAction(target, info, true));
    }
}
