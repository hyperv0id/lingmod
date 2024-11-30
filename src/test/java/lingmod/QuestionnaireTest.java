package lingmod;

import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.ModInfo;
import com.vdurmont.semver4j.Semver;
import lingmod.util.FeiShuQA;
import lingmod.util.FeiShuQAItem;

import java.lang.reflect.Constructor;

public class QuestionnaireTest {
    public static void main(String[] args) {
        try {
            Constructor<?> constructor = ModInfo.class.getDeclaredConstructor();
            constructor.setAccessible(true); // 改变访问控制
            Loader.MODINFOS = new ModInfo[]{
                    (ModInfo) constructor.newInstance(),
                    (ModInfo) constructor.newInstance(),
            };
            Loader.MODINFOS[0].ID = "lingmod";
            Loader.MODINFOS[0].ModVersion = new Semver("0.1.4");
            Loader.MODINFOS[0].Name = "明日方舟-令";
            Loader.MODINFOS[1].ID = "basemod";
            Loader.MODINFOS[1].Name = "BaseMod";
            Loader.MODINFOS[1].ModVersion = new Semver("0.1.18");
        } catch (Exception e) {
            e.printStackTrace();
        }
        String link = new FeiShuQA()
                .with(FeiShuQAItem.REPORT_TYPE, "BUG（闪退）")
                .with(FeiShuQAItem.MOD_ENABLED, FeiShuQA.getModEnabled())
                .with(FeiShuQAItem.DESCRIPTION, "闪退")
                .with(FeiShuQAItem.ERR_CAUSE, "TODO: 自动采集运行日志")
                .makeLink();
        System.out.println(link);
    }
}
