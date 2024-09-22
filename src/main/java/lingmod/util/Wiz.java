package lingmod.util;

import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import lingmod.actions.TimedVFXAction;
import lingmod.cards.AbstractEasyCard;
import lingmod.cards.attack.JiangXiangNaTie;
import lingmod.character.Ling;
import lingmod.interfaces.CopyField;
import lingmod.interfaces.VoidSupplier;
import lingmod.monsters.AbsSummonMonster;
import lingmod.patch.PlayerPatch;
import lingmod.powers.PoeticMoodPower;
import lingmod.stance.NellaFantasiaStance;
import lingmod.util.audio.ProAudio;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static lingmod.ModCore.logger;
import static lingmod.ModCore.makeID;

public class Wiz {
    // The wonderful Wizard of Oz allows access to most easy compilations of data,
    // or functions.

    /**
     * 获取当前玩家
     *
     * @return 当前文件
     */
    public static AbstractPlayer adp() {
        return AbstractDungeon.player;
    }

    public static void forAllCardsInList(Consumer<AbstractCard> consumer, ArrayList<AbstractCard> cardsList) {
        cardsList.forEach(consumer);
    }

    public static ArrayList<AbstractCard> getAllCardsInCardGroups(boolean includeHand, boolean includeExhaust) {
        ArrayList<AbstractCard> masterCardsList = new ArrayList<>();
        masterCardsList.addAll(AbstractDungeon.player.drawPile.group);
        masterCardsList.addAll(AbstractDungeon.player.discardPile.group);
        if (includeHand)
            masterCardsList.addAll(AbstractDungeon.player.hand.group);
        if (includeExhaust)
            masterCardsList.addAll(AbstractDungeon.player.exhaustPile.group);
        return masterCardsList;
    }

    public static void forAllMonstersLiving(Consumer<AbstractMonster> consumer) {
        getEnemies().forEach(consumer);
    }

    public static void forAllMonstersLivingTop(Consumer<AbstractMonster> consumer) {
        ArrayList<AbstractMonster> enemies = getEnemies();
        Collections.reverse(enemies);
        enemies.forEach(consumer);
    }

    public static ArrayList<AbstractMonster> getEnemies() {
        ArrayList<AbstractMonster> monsters = new ArrayList<>(AbstractDungeon.getMonsters().monsters);
        monsters.removeIf(AbstractCreature::isDeadOrEscaped);
        return monsters;
    }

    public static ArrayList<AbstractCard> getCardsMatchingPredicate(Predicate<AbstractCard> pred) {
        return getCardsMatchingPredicate(pred, false);
    }

    public static ArrayList<AbstractCard> getCardsMatchingPredicate(Predicate<AbstractCard> pred, boolean allcards) {
        if (allcards)
            return (ArrayList<AbstractCard>) CardLibrary.getAllCards().stream().filter(pred)
                    .collect(Collectors.toList());
        else {
            ArrayList<AbstractCard> cardsList = new ArrayList<>();
            cardsList.addAll(AbstractDungeon.srcCommonCardPool.group);
            cardsList.addAll(AbstractDungeon.srcUncommonCardPool.group);
            cardsList.addAll(AbstractDungeon.srcRareCardPool.group);
            cardsList.removeIf(c -> !pred.test(c));
            return cardsList;
        }
    }

    public static AbstractCard getTrulyRandPrediCardInCombat(Predicate<AbstractCard> pred, boolean allCards) {
        return getRandomItem(getCardsMatchingPredicate(pred, allCards));
    }

    public static AbstractCard getTrulyRandPrediCardInCombat(Predicate<AbstractCard> pred) {
        return getTrulyRandPrediCardInCombat(pred, false);
    }

    public static <T> T getRandomItem(ArrayList<T> list, Random rng) {
        return list.isEmpty() ? null : list.get(rng.random(list.size() - 1));
    }

    public static <T> T getRandomItem(ArrayList<T> list) {
        return getRandomItem(list, AbstractDungeon.cardRandomRng);
    }

    public static boolean actuallyHovered(Hitbox hb) {
        return InputHelper.mX > hb.x && InputHelper.mX < hb.x + hb.width && InputHelper.mY > hb.y
                && InputHelper.mY < hb.y + hb.height;
    }

    public static boolean isInCombat() {
        return CardCrawlGame.isInARun() && AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom() != null
                && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT;
    }

    public static void atb(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }

    public static void att(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToTop(action);
    }

    public static void vfx(AbstractGameEffect gameEffect) {
        atb(new VFXAction(gameEffect));
    }

    public static void vfx(AbstractGameEffect gameEffect, float duration) {
        atb(new VFXAction(gameEffect, duration));
    }

    public static void tfx(AbstractGameEffect gameEffect) {
        atb(new TimedVFXAction(gameEffect));
    }

    public static void makeInHand(AbstractCard c, int i) {
        atb(new MakeTempCardInHandAction(c, i));
    }

    public static void makeInHand(AbstractCard c) {
        makeInHand(c, 1);
    }

    public static void shuffleIn(AbstractCard c, int i) {
        atb(new MakeTempCardInDrawPileAction(c, i, true, true));
    }

    public static void shuffleIn(AbstractCard c) {
        shuffleIn(c, 1);
    }

    public static void shuffleInTop(AbstractCard c, int i) {
        att(new MakeTempCardInDrawPileAction(c, i, true, true));
    }

    public static void shuffleInTop(AbstractCard c) {
        shuffleInTop(c, 1);
    }

    public static void topDeck(AbstractCard c, int i) {
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(c, i, false, true));
    }

    public static void topDeck(AbstractCard c) {
        topDeck(c, 1);
    }

    public static void applyToEnemy(AbstractMonster m, AbstractPower po) {
        atb(new ApplyPowerAction(m, AbstractDungeon.player, po, po.amount));
    }

    public static void applyToEnemyTop(AbstractMonster m, AbstractPower po) {
        att(new ApplyPowerAction(m, AbstractDungeon.player, po, po.amount));
    }

    public static void applyToSelf(AbstractPower po) {
        atb(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, po, po.amount));
    }

    public static void applyToSelfTop(AbstractPower po) {
        att(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, po, po.amount));
    }

    public static void thornDmg(AbstractCreature m, int amount, AbstractGameAction.AttackEffect AtkFX) {
        atb(new DamageAction(m, new DamageInfo(AbstractDungeon.player, amount, DamageInfo.DamageType.THORNS), AtkFX));
    }

    public static void thornDmg(AbstractCreature m, int amount) {
        thornDmg(m, amount, AbstractGameAction.AttackEffect.NONE);
    }

    public static void discard(int amount, boolean isRandom) {
        atb(new DiscardAction(adp(), adp(), amount, isRandom));
    }

    public static void discard(int amount) {
        discard(amount, false);
    }

    public static int pwrAmt(AbstractCreature check, String ID) {
        AbstractPower found = check.getPower(ID);
        if (found != null)
            return found.amount;
        return 0;
    }

    public static AbstractGameAction actionify(Runnable todo) {
        return new AbstractGameAction() {
            public void update() {
                isDone = true;
                todo.run();
            }
        };
    }

    public static void actB(Runnable todo) {
        atb(actionify(todo));
    }

    public static void actT(Runnable todo) {
        att(actionify(todo));
    }

    public static AbstractGameAction multiAction(AbstractGameAction... actions) {
        return actionify(() -> {
            ArrayList<AbstractGameAction> actionsList = (ArrayList<AbstractGameAction>) Arrays.asList(actions);
            Collections.reverse(actionsList);
            for (AbstractGameAction action : actions)
                att(action);
        });
    }

    public static void playAudio(ProAudio a) {
        CardCrawlGame.sound.play(makeID(a.toString()));
    }

    /**
     * @return 姿态是不是梦
     */
    public static boolean isStanceNell() {
        AbstractPlayer p = AbstractDungeon.player;
        return p.stance != null && p.stance.ID.equals(NellaFantasiaStance.STANCE_ID);
    }

    public static List<AbstractCard> allCardsInBattle(boolean exhaust) {
        List<AbstractCard> ret = new ArrayList<>();
        AbstractPlayer p = AbstractDungeon.player;
        ret.addAll(p.hand.group);
        ret.addAll(p.discardPile.group);
        ret.addAll(p.drawPile.group);
        if (exhaust) {
            ret.addAll(p.exhaustPile.group);
        }
        return ret;
    }

    /**
     * 角色的所有可见能力
     *
     * @param type 筛选类型，null表示不筛选
     * @return 满足类型的所有可见能力
     */
    public static List<AbstractPower> allPowers(PowerType type) {
        return AbstractDungeon.player.powers.stream()
                .filter(p -> !(p instanceof InvisiblePower))
                .filter(p -> !p.ID.equals(PoeticMoodPower.ID)) // 诗有单独UI
                .filter(p -> (type == null || p.type == type))
                .collect(Collectors.toList());
    }

    /**
     * @return 卡牌是不是在手牌中
     */
    public static boolean isInHand(AbstractCard card) {
        return AbstractDungeon.player.hand.contains(card);
    }

    /**
     * 交换两个卡牌的费用，特判了诅咒牌
     *
     * @param c1       卡牌1
     * @param c2       卡牌2
     * @param turnOnly 是否仅限本回合
     */
    public static void swapCardCost(AbstractCard c1, AbstractCard c2, boolean turnOnly) {
        // 特判X牌、状态、诅咒
        if (c1.cost == -1 || c1.type == CardType.CURSE || c1.type == CardType.STATUS) {
            c2.costForTurn = 0;
            if (!turnOnly)
                c2.cost = 0;
            return;
        }
        if (c2.cost == -1 || c2.type == CardType.CURSE || c2.type == CardType.STATUS) {
            c1.costForTurn = 0;
            if (!turnOnly)
                c1.cost = 0;
            return;
        }
        // 一般交换
        {
            int tmp = c1.costForTurn;
            c1.costForTurn = c2.costForTurn;
            if (c1.costForTurn <= 0)
                c1.costForTurn = 0;
            c2.costForTurn = tmp;
            if (c2.costForTurn <= 0)
                c2.costForTurn = 0;
        }
        if (!turnOnly) {
            int tmp = c1.cost;
            c1.cost = Math.max(c2.cost, 0);
            c2.cost = Math.max(tmp, 0);
        }
    }

    /**
     * 辅助方法，用于判断卡牌是否具有起始防御或攻击标签
     *
     * @return 是否是打防
     */
    public static boolean isStart_SD(AbstractCard card) {
        return card.hasTag(AbstractCard.CardTags.STARTER_DEFEND) || card.hasTag(AbstractCard.CardTags.STARTER_STRIKE);
    }

    /**
     * makeStatEquivalentCopy 自动 复制注解
     *
     * @param source 原始类
     * @param target 新的复制类
     */
    public static void copyAnnotatedFields(AbstractEasyCard source, AbstractEasyCard target) {
        // 必须是同类
        if (source == null || target == null) {
            return;
        }
        Class<?> currentClass = source.getClass();
        while (currentClass != null && AbstractEasyCard.class.isAssignableFrom(currentClass)) {
            for (Field field : currentClass.getDeclaredFields()) {
                if (field.isAnnotationPresent(CopyField.class)) {
                    try {
                        field.setAccessible(true);
                        field.set(target, field.get(source));
                    } catch (IllegalAccessException e) {
                        logger.error("copy @CopyField failed TAT");
                    }
                }
            }
            currentClass = currentClass.getSuperclass();
        }
    }

    public static void addToBotAbstract(final VoidSupplier func) {
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            public void update() {
                func.get();
                this.isDone = true;
            }
        });
    }

    public static boolean isPlayerLing() {
        if (AbstractDungeon.player != null) {
            if (AbstractDungeon.player.chosenClass == Ling.Enums.PLAYER_LING) {
                return true;
            } else
                return AbstractDungeon.player instanceof Ling;
        }
        return false;
    }

    /**
     * 随机化手牌费用，你的手牌平均费用越低，那么随机化后的越低
     * 不过会随着卡牌的打出而变得“倒霉”
     */
    public static void shuffleHandCost(boolean noWine) {
        int maxCost = AbstractDungeon.player.hand.group.stream()
                .max(Comparator.comparingInt(a -> a.costForTurn))
                .map(card -> card.costForTurn)
                .orElse(3); // Replace with a default value if no card is found
        AbstractDungeon.player.hand.group.forEach(card -> {
            if (noWine && card.hasTag(CustomTags.WINE))
                return;
            if (card.cost >= 0) {
                int newCost = AbstractDungeon.cardRandomRng.random(maxCost);
                if (card.cost != newCost) {
                    card.cost = newCost;
                    card.costForTurn = card.cost;
                    card.isCostModified = true;
                }

                card.freeToPlayOnce = false;
            }
        });
    }

    /**
     * 改变卡牌的费用
     *
     * @param card   卡牌
     * @param addAmt 增加多少，负数减少
     */
    public static void addCost(AbstractCard card, int addAmt) {
        card.cost += addAmt;
        card.cost = Math.max(0, card.cost);
        card.costForTurn += addAmt;
        card.costForTurn = Math.max(0, card.costForTurn);
        card.isCostModified = true;
        card.isCostModifiedForTurn = true;
    }

    public static int weightedRandSelect(List<Integer> lis) {
        long total = lis.stream().mapToInt(i -> i).sum();
        long r = AbstractDungeon.miscRng.random(total);
        for (int i = 0; i < lis.size(); i++) {
            r -= lis.get(i);
            if (r <= 0)
                return i;
        }
        return 0;
    }

    public static boolean isVisibleDebuff(AbstractPower p) {
        return !(p instanceof InvisiblePower) && p.type == PowerType.DEBUFF;
    }

    public static List<Integer> redPacketRand(int totalAmount, int peopleCount) {
        int MIN_AMOUNT = 0, MAX_AMOUNT = 3;

        List<Integer> distribution = new ArrayList<>();
        int remainingAmount = totalAmount;
        int remainingPeople = peopleCount;

        for (int i = 0; i < peopleCount - 1; i++) {
            int maxPossible = Math.min(MAX_AMOUNT, remainingAmount);
            int minPossible = Math.max(MIN_AMOUNT, remainingAmount - (remainingPeople - 1) * MAX_AMOUNT);

            int amount = AbstractDungeon.cardRng.random(minPossible, maxPossible);
            distribution.add(amount);

            remainingAmount -= amount;
            remainingPeople--;
        }

        distribution.add(remainingAmount);

        return distribution;
    }

    public static void redistributeCardCosts(List<AbstractCard> cards) {
        List<AbstractCard> eligibleCards = new ArrayList<>();
        int total = 0;

        // 单次遍历手牌，计算总成本并收集符合条件的卡牌
        for (AbstractCard card : cards) {
            if (card.costForTurn >= 0) {
                eligibleCards.add(card);
                total += card.costForTurn;
            }
        }

        int people = eligibleCards.size();

        if (people == 0) {
            return; // 没有符合条件的卡牌，直接返回
        }

        // 调用红包算法获取新的成本分配
        List<Integer> newCosts = Wiz.redPacketRand(total, people);

        // 更新符合条件的卡牌的成本
        for (int i = 0; i < people; i++) {
            AbstractCard card = eligibleCards.get(i);
            int newCost = newCosts.get(i);
            if (card.cost != newCost) {
                card.cost = newCost;
                card.costForTurn = newCost;
                card.isCostModified = true;
            }
            card.freeToPlayOnce = false;
        }
    }

    /**
     * 遍历手牌、弃牌堆、抽牌堆。计算有多少满足条件的
     */
    public static int countCards(Function<AbstractCard, Boolean> cardChecker) {
        int count = 0;

        // 遍历手牌
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (cardChecker.apply(c)) {
                ++count;
            }
        }

        // 遍历抽牌堆
        for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
            if (cardChecker.apply(c)) {
                ++count;
            }
        }

        // 遍历弃牌堆
        for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
            if (cardChecker.apply(c)) {
                ++count;
            }
        }
        return count;
    }

    public static void applyPower(AbstractCreature src, AbstractCreature tar, AbstractPower inst) {
        HashMap<String, Object> tabel = new HashMap<>();
        if (tar instanceof AbstractMonster) {
            // isSourceMonster
            tabel.put("justApplied", true); // WeakPower
            tabel.put(AbstractCreature.class.getName() + "\n" + "owner", tar);
        }
        AbstractPower p2a = PowerUtils.copyPower(inst, tabel);
        if (p2a != null)
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(src, tar, p2a));
    }


    /**
     * 对召唤物使用卡牌
     */
    public static void useCardOnSummon(AbstractCard card, AbstractMonster monster) {
        logger.info("use card on summon: {}", monster.name);
        card.exhaust = true;
        card.exhaustOnUseOnce = true;

        if (!(monster instanceof AbsSummonMonster))
            return;
        int maxNumber = maxNumberInCard(card);
        if (card.type == CardType.ATTACK || card.cardID.equals(JiangXiangNaTie.ID)) {
            Wiz.atb(new ApplyPowerAction(monster, monster, new StrengthPower(monster, maxNumber)));
            Wiz.addToBotAbstract(monster::applyPowers);
        }
        if (card.type == CardType.SKILL) {
            Wiz.addToBotAbstract(() -> monster.increaseMaxHp(maxNumber, true));
        } else if (card.cardID.equals(JiangXiangNaTie.ID)) {
            Wiz.addToBotAbstract(() -> monster.increaseMaxHp(card.damage, true));
        }
    }

    /**
     * 求卡牌变量中的最大值
     */
    public static int maxNumberInCard(AbstractCard card) {
        card.applyPowers();
        int[] numbers = {card.cost, card.costForTurn, card.damage, card.block, card.magicNumber};
        int ans = 0;
        for (int number : numbers) {
            ans = Math.max(ans, number);
        }
        if (card instanceof AbstractEasyCard) {
            AbstractEasyCard aec = (AbstractEasyCard) card;
            int[] n2 = {aec.baseSecondBlock, aec.baseSecondDamage, aec.baseSecondMagic};
            for (int number : n2) {
                ans = Math.max(ans, number);
            }
        }
        return ans;
    }


    public static AbsSummonMonster getSummon() {
        return PlayerPatch.Fields.summonedMonster.get(Wiz.adp());
    }
}
