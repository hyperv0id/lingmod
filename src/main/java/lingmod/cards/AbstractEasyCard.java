package lingmod.cards;

import basemod.abstracts.CustomCard;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.cards.mod.NellaFantasiaMod;
import lingmod.cards.mod.PoemMod;
import lingmod.cards.mod.WineMod;
import lingmod.character.Ling;
import lingmod.interfaces.CardConfig;
import lingmod.interfaces.VoidSupplier;
import lingmod.util.CardArtRoller;
import lingmod.util.CustomTags;

import java.util.function.Consumer;

import static lingmod.ModCore.makeImagePath;
import static lingmod.ModCore.modID;
import static lingmod.util.Wiz.*;

/**
 * 卡牌大小：500*380的高分辨率，250*190的低分辨率
 */
public abstract class AbstractEasyCard extends CustomCard {

    protected final CardStrings cardStrings;

    public int secondMagic;
    public int baseSecondMagic;
    public boolean upgradedSecondMagic;
    public boolean isSecondMagicModified;

    public int thirdMagic;
    public int baseThirdMagic;
    public boolean upgradedThirdMagic;
    public boolean isThirdMagicModified;

    public int secondDamage;
    public int baseSecondDamage;
    public boolean upgradedSecondDamage;
    public boolean isSecondDamageModified;

    public int secondBlock;
    public int baseSecondBlock;
    public boolean upgradedSecondBlock;
    public boolean isSecondBlockModified;

    private boolean needsArtRefresh = false;

    public AbstractEasyCard(final String cardID, final int cost, final CardType type, final CardRarity rarity,
                            final CardTarget target) {
        this(cardID, cost, type, rarity, target, Ling.Enums.LING_COLOR);
    }

    public AbstractEasyCard(final String cardID, final int cost, final CardType type, final CardRarity rarity,
                            final CardTarget target, final CardColor color) {
        super(cardID, "", getCardTextureString(cardID.replace(modID + ":", ""), type),
                cost, "", type, color, rarity, target);
        cardStrings = CardCrawlGame.languagePack.getCardStrings(this.cardID);
        rawDescription = cardStrings.DESCRIPTION;
        name = originalName = cardStrings.NAME;
        initializeTitle();
        initializeDescription();

        if (textureImg.contains("ui/missing.png")) {
            if (CardLibrary.cards != null && !CardLibrary.cards.isEmpty()) {
                CardArtRoller.computeCard(this);
            } else
                needsArtRefresh = true;
        }
        initializeCardValues();
    }

    /**
     * 通过注解获取卡牌信息，自动初始化避免出现-1
     */
    protected void initializeCardValues() {
        CardConfig config = getClass().getAnnotation(CardConfig.class);
        // 基本属性
        if (config != null) {
            this.damage = this.baseDamage = config.damage();
            this.secondDamage = this.baseSecondDamage = config.damage2();

            this.magicNumber = this.baseMagicNumber = config.magic();
            this.secondMagic = this.baseSecondMagic = config.magic2();

            this.block = this.baseBlock = config.block();
            this.secondBlock = this.baseSecondBlock = config.block2();
            // process wine
            int wineAmt = config.wineAmount();
            if (wineAmt >= 0) {
                this.tags.add(CustomTags.WINE);
                CardModifierManager.addModifier(this, new WineMod(wineAmt));
            }
            // process poem
            int poemAmt = config.poemAmount();
            if (poemAmt >= 0) {
                this.tags.add(CustomTags.POEM);
                CardModifierManager.addModifier(this, new PoemMod(poemAmt));
            }
            // process dream
            boolean isDream = config.isDream();
            if (isDream) {
                this.tags.add(CustomTags.DREAM);
                CardModifierManager.addModifier(this, new NellaFantasiaMod());
            }
        }
    }

    @Override
    protected Texture getPortraitImage() {
        if (textureImg.contains("ui/missing.png")) {
            return CardArtRoller.getPortraitTexture(this);
        } else {
            return super.getPortraitImage();
        }
    }

    public static String getCardTextureString(final String cardName, final AbstractCard.CardType cardType) {
        String textureString = "";
        String prefix = "cards/";
        boolean missing = false;
        switch (cardType) {
            case ATTACK:
                prefix += "attack/";
                break;
            case POWER:
                prefix += "power/";
                break;
            case SKILL:
                prefix += "skill/";
                break;
            case CURSE:
                prefix += "curse/";
                break;
            case STATUS:
                prefix += "status/";
                break;
            default:
                missing = true;
                textureString = makeImagePath("ui/missing.png");
                break;
        }
        if (!missing)
            textureString = makeImagePath(prefix + cardName + ".png");
        FileHandle h = Gdx.files.internal(textureString);
        if (!h.exists()) {
            // 尝试使用默认图片
            textureString = makeImagePath(prefix + "default.png");
            h = Gdx.files.internal(textureString);
            if (!h.exists()) {
                textureString = makeImagePath("ui/missing.png");
            }
        }
        return textureString;
    }

    @Override
    public void applyPowers() {
        if (baseSecondDamage > -1) {
            secondDamage = baseSecondDamage;

            int tmp = baseDamage;
            baseDamage = baseSecondDamage;

            super.applyPowers();

            secondDamage = damage;
            baseDamage = tmp;

            super.applyPowers();

            isSecondDamageModified = (secondDamage != baseSecondDamage);
        } else
            super.applyPowers();
    }

    @Override
    protected void applyPowersToBlock() {
        if (baseSecondBlock > -1) {
            secondBlock = baseSecondBlock;

            int tmp = baseBlock;
            baseBlock = baseSecondBlock;

            super.applyPowersToBlock();

            secondBlock = block;
            baseBlock = tmp;

            super.applyPowersToBlock();

            isSecondBlockModified = (secondBlock != baseSecondBlock);
        } else
            super.applyPowersToBlock();
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        this.magicNumber = baseMagicNumber;
        this.damage = this.baseDamage;
        this.block = this.baseBlock;
        if (baseSecondDamage > -1) {
            secondDamage = baseSecondDamage;

            int tmp = baseDamage;
            baseDamage = baseSecondDamage;

            super.calculateCardDamage(mo);

            secondDamage = damage;
            baseDamage = tmp;

            super.calculateCardDamage(mo);

            isSecondDamageModified = (secondDamage != baseSecondDamage);
        } else
            super.calculateCardDamage(mo);
    }

    public void resetAttributes() {
        super.resetAttributes();
        secondMagic = baseSecondMagic;
        isSecondMagicModified = false;
        thirdMagic = baseThirdMagic;
        isThirdMagicModified = false;
        secondDamage = baseSecondDamage;
        isSecondDamageModified = false;
        secondBlock = baseSecondBlock;
        isSecondBlockModified = false;
    }

    public void displayUpgrades() {
        super.displayUpgrades();
        if (upgradedSecondMagic) {
            secondMagic = baseSecondMagic;
            isSecondMagicModified = true;
        }
        if (upgradedThirdMagic) {
            thirdMagic = baseThirdMagic;
            isThirdMagicModified = true;
        }
        if (upgradedSecondDamage) {
            secondDamage = baseSecondDamage;
            isSecondDamageModified = true;
        }
        if (upgradedSecondBlock) {
            secondBlock = baseSecondBlock;
            isSecondBlockModified = true;
        }
    }

    protected void upgradeSecondMagic(int amount) {
        baseSecondMagic += amount;
        secondMagic = baseSecondMagic;
        upgradedSecondMagic = true;
    }

    protected void upgradeThirdMagic(int amount) {
        baseThirdMagic += amount;
        thirdMagic = baseThirdMagic;
        upgradedThirdMagic = true;
    }

    protected void upgradeSecondDamage(int amount) {
        baseSecondDamage += amount;
        secondDamage = baseSecondDamage;
        upgradedSecondDamage = true;
    }

    protected void upgradeSecondBlock(int amount) {
        baseSecondBlock += amount;
        secondBlock = baseSecondBlock;
        upgradedSecondBlock = true;
    }

    protected void uDesc() {
        this.rawDescription = this.cardStrings.UPGRADE_DESCRIPTION;
        this.initializeDescription();
    }

    public void upgrade() {
        if (this.canUpgrade()) {
            this.upgradeName();
            this.upp();
            if (this.cardStrings.UPGRADE_DESCRIPTION != null) {
                this.uDesc();
            }
        }
    }

    public abstract void upp();

    public void update() {
        super.update();
        if (needsArtRefresh)
            CardArtRoller.computeCard(this);
    }

    public AbstractCard makeStatEquivalentCopy() {
        AbstractCard result = super.makeStatEquivalentCopy();
        if (result instanceof AbstractEasyCard) {
            AbstractEasyCard c = (AbstractEasyCard) result;
            c.baseSecondDamage = c.secondDamage = baseSecondDamage;
            c.baseSecondBlock = c.secondBlock = baseSecondBlock;
            c.baseSecondMagic = c.secondMagic = baseSecondMagic;
            c.baseThirdMagic = c.thirdMagic = baseThirdMagic;
        }
        if (result instanceof AbstractEasyCard) {
            copyAnnotatedFields(this, (AbstractEasyCard) result);
        }
        return result;
    }

    // These shortcuts are specifically for cards. All other shortcuts that aren't
    // specifically for cards can go in Wiz.
    protected void dmg(AbstractMonster m, AbstractGameAction.AttackEffect fx) {
        if (fx == null)
            fx = AttackEffect.NONE;
        atb(new DamageAction(m, new DamageInfo(AbstractDungeon.player, damage, damageTypeForTurn), fx));
    }

    protected void dmgTop(AbstractMonster m, AbstractGameAction.AttackEffect fx) {
        if (fx == null)
            fx = AttackEffect.NONE;
        att(new DamageAction(m, new DamageInfo(AbstractDungeon.player, damage, damageTypeForTurn), fx));
    }

    protected void allDmg(AbstractGameAction.AttackEffect fx) {
        if (fx == null)
            fx = AttackEffect.NONE;
        atb(new DamageAllEnemiesAction(AbstractDungeon.player, multiDamage, damageTypeForTurn, fx));
    }

    protected void allDmgTop(AbstractGameAction.AttackEffect fx) {
        if (fx == null)
            fx = AttackEffect.NONE;
        att(new DamageAllEnemiesAction(AbstractDungeon.player, multiDamage, damageTypeForTurn, fx));
    }

    protected void altDmg(AbstractMonster m, AbstractGameAction.AttackEffect fx) {
        if (fx == null)
            fx = AttackEffect.NONE;
        atb(new DamageAction(m, new DamageInfo(AbstractDungeon.player, secondDamage, damageTypeForTurn), fx));
    }

    protected void altDmgTop(AbstractMonster m, AbstractGameAction.AttackEffect fx) {
        if (fx == null)
            fx = AttackEffect.NONE;
        att(new DamageAction(m, new DamageInfo(AbstractDungeon.player, secondDamage, damageTypeForTurn), fx));
    }

    private AbstractGameAction dmgRandomAction(AbstractGameAction.AttackEffect fx,
                                               Consumer<AbstractMonster> extraEffectToTarget, Consumer<AbstractMonster> effectBefore) {
        return actionify(() -> {
            AbstractMonster target = AbstractDungeon.getMonsters().getRandomMonster(null, true,
                    AbstractDungeon.cardRandomRng);
            if (target != null) {
                calculateCardDamage(target);
                if (extraEffectToTarget != null)
                    extraEffectToTarget.accept(target);
                att(new DamageAction(target, new DamageInfo(AbstractDungeon.player, damage, damageTypeForTurn), fx));
                if (effectBefore != null)
                    effectBefore.accept(target);
            }
        });
    }

    protected void dmgRandom(AbstractGameAction.AttackEffect fx) {
        if (fx == null)
            fx = AttackEffect.NONE;
        dmgRandom(fx, null, null);
    }

    protected void dmgRandom(AbstractGameAction.AttackEffect fx, Consumer<AbstractMonster> extraEffectToTarget,
                             Consumer<AbstractMonster> effectBefore) {
        if (fx == null)
            fx = AttackEffect.NONE;
        atb(dmgRandomAction(fx, extraEffectToTarget, effectBefore));
    }

    protected void dmgRandomTop(AbstractGameAction.AttackEffect fx) {
        if (fx == null)
            fx = AttackEffect.NONE;
        dmgRandomTop(fx, null, null);
    }

    protected void dmgRandomTop(AbstractGameAction.AttackEffect fx, Consumer<AbstractMonster> extraEffectToTarget,
                                Consumer<AbstractMonster> effectBefore) {
        if (fx == null)
            fx = AttackEffect.NONE;
        att(dmgRandomAction(fx, extraEffectToTarget, effectBefore));
    }

    protected void blck() {
        atb(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, block));
    }

    protected void blckTop() {
        att(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, block));
    }

    protected void altBlck() {
        atb(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, secondBlock));
    }

    protected void altBlckTop() {
        att(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, secondBlock));
    }

    public String cardArtCopy() {
        return null;
    }

    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return null;
    }

    public static void addToBotAbstract(final VoidSupplier func) {
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            public void update() {
                func.get();
                this.isDone = true;
            }
        });
    }

    public static void addToTopAbstract(final VoidSupplier func) {
        AbstractDungeon.actionManager.addToTop(new AbstractGameAction() {
            public void update() {
                func.get();
                this.isDone = true;
            }
        });
    }

}
