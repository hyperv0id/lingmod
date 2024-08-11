package lingmod.cards.skill;

import static lingmod.ModCore.logger;
import static lingmod.ModCore.makeID;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.screens.stats.RunData;

import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import lingmod.actions.NostalgiaAndCuriosityAction;
import lingmod.cards.AbstractEasyCard;
import lingmod.cards.attack.Strike;
import lingmod.character.Ling;
import lingmod.interfaces.CardConfig;

/**
 * 从之前获得过的牌中 3 选 1
 * TODO: 没有事件联动，无法获得
 */
@CardConfig(magic = 3)
public class NostalgiaAndCuriosity extends AbstractEasyCard {
    public final static String ID = makeID(NostalgiaAndCuriosity.class.getSimpleName());

    private static ArrayList<RunData> Runs = new ArrayList<>();

    private static final Gson gson = new Gson();
    public static final AbstractCard defaultCard = new Strike();

    private static final ArrayList<String> cardsSelected = new ArrayList<>();

    public NostalgiaAndCuriosity() {
        super(ID, 1, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF);
        CardModifierManager.addModifier(this, new ExhaustMod());
        if (cardsSelected.isEmpty())
            loadRunData();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new NostalgiaAndCuriosityAction(generateCardChoices(), upgraded));
    }

    private ArrayList<AbstractCard> generateCardChoices() {
        ArrayList<AbstractCard> ret = new ArrayList<>();
        for (int i = 0; i < magicNumber; i++) {
            ret.add(getRandCard());
        }
        return ret;
    }

    public static AbstractCard getRandCard() {
        AbstractCard result = defaultCard.makeCopy();
        String key = cardsSelected.get(AbstractDungeon.cardRng.random(cardsSelected.size() - 1));
        key = key.split("\\+")[0];
        logger.info("Try 1: " + CardLibrary.getCard(key));
        logger.info("Try 2: " + CardLibrary.getCard(Ling.Enums.PLAYER_LING, key));
        AbstractCard res = CardLibrary.getCard(key);
        if (res == null) {
            return result;
        } else {
            return res;
        }
    }

    public static void loadRunData() {
        FileHandle[] folders = Gdx.files.local("runs").list();
        int amt = folders.length;
        for (int i = 0; i < amt; i++) {
            FileHandle folder = folders[i];

            if (CardCrawlGame.saveSlot == 0) {
                if (folder.name().contains("0_") || folder.name().contains("1_") || folder.name().contains("2_")) {
                    continue;
                }
            } else {
                if (!folder.name().contains(CardCrawlGame.saveSlot + "_")) {
                    continue;
                }
            }

            FileHandle[] files = folder.list();

            for (int j = 0; j < files.length; j++) {
                FileHandle file = files[j];
                try {
                    RunData data = (RunData) gson.fromJson(file.readString(), RunData.class);
                    if (data != null && data.timestamp == null) {
                        data.timestamp = file.nameWithoutExtension();
                        String exampleDaysSinceUnixStr = "17586";
                        boolean assumeDaysSinceUnix = (data.timestamp
                                .length() == exampleDaysSinceUnixStr.length());
                        if (assumeDaysSinceUnix)
                            try {
                                long days = Long.parseLong(data.timestamp);
                                data.timestamp = Long.toString(days * 86400L);
                            } catch (NumberFormatException var18) {
                                logger.info(
                                        "Run file " + file.path() +
                                                " name is could not be parsed into a Timestamp.");
                                data = null;
                            }
                    }
                    if (data != null)
                        try {
                            AbstractPlayer.PlayerClass.valueOf(data.character_chosen);
                            logger.info("Load Run File For: " + data.character_chosen);
                            Runs.add(data);
                        } catch (NullPointerException | IllegalArgumentException var17) {
                            logger.info("Run file " + file.path() +
                                    " does not use a real character: " +
                                    data.character_chosen);
                        }
                } catch (JsonSyntaxException ex) {
                    logger.info("Failed to load RunData from JSON file: " + file.path());
                }
            }
        }

        String check = Ling.Enums.PLAYER_LING.toString();

        cardsSelected.clear();
        Runs.stream()
                .filter(run -> (!run.is_daily && !run.is_special_run && !run.is_endless)) // 特殊模式
                .filter(run -> run.character_chosen.equals(check) || run.character_chosen.equals(Ling.NAMES[0])) // 角色筛选
                .forEach(run -> {
                    run.card_choices.stream()
                            .map(cho -> cho.picked)
                            .filter(pic -> !pic.equals("SKIP"))
                            .forEach(pic -> cardsSelected.add(pic));
                });
        logger.info("Cards Selected: " + cardsSelected);
    }

    @Override
    public void upp() {
    }

}
// "lingmod:NostalgiaAndCuriosity": {
// "NAME": "NostalgiaAndCuriosity",
// "DESCRIPTION": ""
// }