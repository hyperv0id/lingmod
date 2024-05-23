package lingmod.relics;

import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import lingmod.character.Ling;
import lingmod.character.VoiceMaster;
import lingmod.powers.PoeticMoodPower;

/**
 * 一盏灯：灯挑夜，箭如雨，大漠飞火
 */
public class LightRelic extends AbstractEasyRelic {
    public static final String ID = makeID(LightRelic.class.getSimpleName());

    public LightRelic() {
        super(ID, RelicTier.STARTER, LandingSound.FLAT, Ling.Enums.LING_COLOR);
    }


    @Override
    public void atBattleStart() {
        VoiceMaster.getInstance().beginBattle();
//        this.flash();
//        AbstractPlayer p = AbstractDungeon.player;
//        addToBot(new ApplyPowerAction(p, p, new LightPower(p, 0)));
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        super.onPlayCard(c, m);
        AbstractPlayer player = AbstractDungeon.player;
        int level = AbstractDungeon.actNum;
        this.flash();
        addToBot(new ApplyPowerAction(player, player, new PoeticMoodPower(player, 1, level)));
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(DESCRIPTIONS[0]);
    }
}
