package lingmod.util;

import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.ModInfo;

import java.util.HashMap;

public class BrowserHandler {

    public static HashMap<String, String> feishuMap = new HashMap<>();

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
    }

    //        public static final String QA_URL = "https://j5xd30acha.feishu.cn/share/base/form/shrcnDH36Z8MdZWvipiTZwDeqie?prefill_问题描述=描述";
    public static final String QA_URL = "https://j5xd30acha.feishu.cn/share/base/form/shrcnJQpQNq0xvTHyv2jpWixymb";


    public static String buildQALink() {
        StringBuilder sb = new StringBuilder();

        sb.append(QA_URL);
        sb.append("?");
        String mods = buildModEnabled();
        sb.append("prefill_自动采集=1&hide_自动采集=1&");
        // 自动填充MOD启用表
        if (!mods.isEmpty()) {
            sb.append("prefill_反馈类型=BUG（闪退）&");
            mods = encodeSpecialChars(mods);
            sb.append("prefill_MOD启用=");
            sb.append(mods);
            sb.append("&");
        }
        return sb.toString();
    }

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

    /**
     * 处理MOD启用
     *
     * @return 启用MOD的序列
     */
    static String buildModEnabled() {
        StringBuilder builder = new StringBuilder();
        if (Loader.MODINFOS == null) return "";
        for (ModInfo info : Loader.MODINFOS) {
            builder.append(info.getIDName());
            builder.append(":");
            builder.append(info.Name);
            builder.append(";");
        }
        return builder.toString();
    }
}
