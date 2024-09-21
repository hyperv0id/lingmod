package lingmod.monsters;

import basemod.ReflectionHacks;
import basemod.abstracts.CustomMonster;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import lingmod.powers.AbstractSummonDescPower;
import lingmod.util.MonsterHelper;
import lingmod.util.Wiz;

import java.util.HashMap;

import static lingmod.ModCore.logger;

public abstract class AbsSummonMonster extends CustomMonster {
    public String img_up;
    public int baseMaxHP;
    private AbstractCard lastHoveredCard;

    public AbsSummonMonster(String name, String id, int maxHealth, float hb_x, float hb_y, float hb_w, float hb_h,
                            String imgUrl, String img_up) {
        super(name, id, maxHealth, hb_x, hb_y, hb_w, hb_h, imgUrl);
        MonsterHelper.loadImage(this, imgUrl);
        this.baseMaxHP = maxHealth;
        isPlayer = true;
        this.img_up = img_up;
    }

    @Override
    public void usePreBattleAction() {
        super.usePreBattleAction();
        addToBot(new ApplyPowerAction(this, this, new AbstractSummonDescPower(this)));
        showHealthBar();
        createIntent();
        MonsterHelper.MoveMonster(this, AbstractDungeon.player.drawX + 200.0F * Settings.scale,
                AbstractDungeon.player.drawY + 100.0F * Settings.scale);
    }

    @Override
    protected void getMove(int arg0) {
        setMove((byte) 0, Intent.ATTACK, damage.get(0).base);
    }

    private final HashMap<AbstractCard, AbstractCard.CardTarget> cardTargetCache = new HashMap<>();

    @Override
    public void update() {
        super.update();

        if (hb.hovered || intentHb.hovered || healthHb.hovered)
            hover();

        AbstractPlayer p = Wiz.adp();
        if (!(boolean) ReflectionHacks.privateMethod(AbstractPlayer.class, "clickAndDragCards").invoke(Wiz.adp())) {
            // 切换选牌是修改target
            if (lastHoveredCard != null && p.hoveredCard != lastHoveredCard) {
                AbstractCard.CardTarget oldTarget = cardTargetCache.get(lastHoveredCard);
                if (oldTarget != null) {
                    lastHoveredCard.target = oldTarget;
                    logger.info("Reset Target: {} as {}", lastHoveredCard.name, oldTarget);
                    cardTargetCache.remove(lastHoveredCard);
                }
            }
        }
    }

    public void hover() {
        if (this.isDeadOrEscaped()) return;
        AbstractPlayer p = Wiz.adp();
        if ((boolean) ReflectionHacks.privateMethod(AbstractPlayer.class, "clickAndDragCards").invoke(p)) {
            AbstractCard card = p.hoveredCard;
            lastHoveredCard = card;
            if (card != null && (card.type == AbstractCard.CardType.ATTACK || card.type == AbstractCard.CardType.SKILL)) {
                cardTargetCache.put(card, card.target);
                card.target = AbstractCard.CardTarget.ENEMY;
                logger.info("Set Target: {} ENEMY", card.name);
            }
        }
    }

    @Override
    public void unhover() {
        super.unhover();
    }

    public void setDamage(int amt) {
        DamageInfo info = damage.get(0);
        info.base = info.output = amt;
        this.damage.set(0, info);
        this.setIntentBaseDmg(amt);
    }

    @Override
    public void die() {
        super.die();
        die_summon();
    }

    /**
     * 合成召唤物
     */
    public void combine() {
        this.increaseMaxHp(this.baseMaxHP, true);
        MonsterHelper.loadImage(this, img_up);

        AbstractPower po = addCombinePower();
        addToBot(new ApplyPowerAction(this, this, po));
        Wiz.addToBotAbstract(() -> {
            this.getMove(0);
            this.createIntent();
        });

    }

    protected abstract AbstractPower addCombinePower();

    public void die_summon() {
        Wiz.atb(new GainEnergyAction(1));
    }
}
