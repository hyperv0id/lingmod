package lingmod.monsters;

import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;

import lingmod.monsters.whoisreal.AYao;

public class MonsterGroups {
    public static MonsterGroup NIAN_GUEST_STAR = new MonsterGroup(new AbstractMonster[]{
            new AYao(-320F, 25F),
            // new XiaoZao(-160F, 12F),
            new AYao(25F, 35F),
            // new XiaoZao(205F, 40F),
    });
}
