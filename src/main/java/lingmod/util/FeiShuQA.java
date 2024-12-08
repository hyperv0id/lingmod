package lingmod.util;

import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.ModInfo;

import java.util.HashMap;

public class FeiShuQA {

    public static HashMap<String, String> feishuMap = new HashMap<>();
    public HashMap<FeiShuQAItem, String> items = new HashMap<>();

    static {
        feishuMap.put(" ", "%20");
        feishuMap.put(";", "%3B");
        feishuMap.put("(", "%28");
        feishuMap.put(")", "%29");
        feishuMap.put(":", "%3A");
        feishuMap.put("&", "%26");
        feishuMap.put("[", "%5B");
        feishuMap.put("$", "%24");
        feishuMap.put("]", "%5D");
        feishuMap.put(",", "%2C");
        feishuMap.put("{", "%7B");
        feishuMap.put("！", "%21");
        feishuMap.put("}", "%7D");
        feishuMap.put("@", "%40");
        feishuMap.put("#", "%23");
        feishuMap.put("\n", "%0A");
        feishuMap.put("\t", "%09");
    }

    //        public static final String QA_URL = "https://j5xd30acha.feishu.cn/share/base/form/shrcnDH36Z8MdZWvipiTZwDeqie?prefill_问题描述=描述";
    public static final String QA_URL = "https://j5xd30acha.feishu.cn/share/base/form/shrcnJQpQNq0xvTHyv2jpWixymb";


    // 处理特殊字符
    static String encodeSpecialChars(String input) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            String charToReplace = String.valueOf(input.charAt(i));
            String replacement = feishuMap.get(charToReplace);
            result.append(replacement == null ? charToReplace : replacement);
        }
        return result.toString();
    }

    public FeiShuQA with(FeiShuQAItem item, String value) {
        items.put(item, value);
        return this;
    }

    public String makeLink() {
        StringBuilder sb = new StringBuilder();
        sb.append(QA_URL);
        sb.append("?");
        items.forEach((item, value) -> {
            sb.append("prefill_");
            sb.append(item);
            sb.append("=");
            sb.append(encodeSpecialChars(value));
            sb.append("&");
        });
        sb.append("hide_自动采集=1&"); // 隐藏自动采集信息
        //sb.append("hide_报错信息=1&"); // 隐藏报错信息
        return sb.toString();
    }


    /**
     * 获取启用MOD的序列
     *
     * @return 启用MOD的序列
     */
    public static String getModEnabled() {
        StringBuilder builder = new StringBuilder();
        builder.append("自动采集：\n");
        if (Loader.MODINFOS == null) return builder.toString();
        for (ModInfo info : Loader.MODINFOS) {
            builder.append(info.getIDName());
            builder.append("@");
            builder.append(info.ModVersion);
            builder.append("\t");
            builder.append(info.Name);
            builder.append("\n");
        }
        return builder.toString();
    }

}
