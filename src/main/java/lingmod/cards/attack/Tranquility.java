package lingmod.cards.attack;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.CardConfig;
import lingmod.interfaces.Credit;
import lingmod.monsters.Thunderer_SummonMonster;
import lingmod.monsters.Tranquility_SummonMonster;
import lingmod.relics.SanYiShiJian;
import lingmod.util.MonsterHelper;
import lingmod.util.Wiz;

import static lingmod.ModCore.makeID;

/**
 * 清平：造成 !D! 点伤害，将一张牌变为清平。 保留时：对随机敌人打出
 */
@CardConfig(damage = 9, isSummon = true, summonClz = Tranquility_SummonMonster.class)
@Credit(link = "https://www.pixiv.net/artworks/106018673", username = "小动物管理员", platform = "pixiv")
public class Tranquility extends AbstractEasyCard {

    public static final String ID = makeID(Tranquility.class.getSimpleName());

    public Tranquility() {
        super(ID, 1, CardType.ATTACK, CardRarity.BASIC, CardTarget.ENEMY);
    }

    @Override
    public void upp() {
        upgradeDamage(3);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        if (Wiz.adp().hasRelic(SanYiShiJian.ID)) {
            CardConfig cc = this.getClass().getAnnotation(CardConfig.class);
            MonsterHelper.summonMonster(cc.summonClz());
            return;
        }
        dmg(m, AttackEffect.NONE);
        addToBotAbstract(() -> this.dontTriggerOnUseCard = false);
    }

    public void summon_use(AbstractPlayer p, AbstractMonster monster) {
        int cnt = MonsterHelper.cntSummons();
        if (cnt >= magicNumber) {
            String msg = String.format("我不能召唤超过 %d 个召唤物", magicNumber);
            addToBot(new TalkAction(true, msg, 2.0F, 2.0F));
        }
        addToBotAbstract(() -> {
            Thunderer_SummonMonster mo = (Thunderer_SummonMonster) MonsterHelper
                    .spawnMonster(Thunderer_SummonMonster.class);
            // mo.animX = 1200F * Settings.xScale;
            mo.setDamage(3);
            mo.init();
            mo.applyPowers();
            mo.useUniversalPreBattleAction();
            mo.showHealthBar();
            mo.createIntent();

            mo.usePreBattleAction();

            // AbstractDungeon.getCurrRoom().monsters.addMonster(0, mo);
        });
    }
}
