package cn.gface.face;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class TestMain {
    public static void main(String[] args) {
        String aa = "1,2,3,4";
        String[] split = split(aa, ",");
        System.out.println("长度1：" + split.length + "---" + countStr(aa, ","));
        aa = ",,,,";
        String[] split2 = split(aa, ",");
        System.out.println("长度2：" + split2.length + "---" + countStr(aa, ","));
    }

    /**
     * 判断str1中包含regex的个数(大于0加1)
     *
     * @param str
     * @param regex
     * @return counter
     */
    public static int countStr(String str, String regex) {
        int count=0;
        while (str.contains(regex)){
            str=str.substring(str.indexOf(regex)+1);
            ++count;
        }
        if (count>0){
            count++;
        }
        return count;
    }


    public static String[] split(String valueStr, String regex) {
        int limit = 0;
        char ch = 0;
        char[] value = valueStr.toCharArray();
        if (((regex.toCharArray().length == 1 &&
                ".$|()[{^?*+\\".indexOf(ch = regex.charAt(0)) == -1) ||
                (regex.length() == 2 &&
                        regex.charAt(0) == '\\' &&
                        (((ch = regex.charAt(1)) - '0') | ('9' - ch)) < 0 &&
                        ((ch - 'a') | ('z' - ch)) < 0 &&
                        ((ch - 'A') | ('Z' - ch)) < 0)) &&
                (ch < Character.MIN_HIGH_SURROGATE ||
                        ch > Character.MAX_LOW_SURROGATE)) {
            int off = 0;
            int next = 0;
            boolean limited = limit > 0;
            ArrayList<String> list = new ArrayList<>();
            while ((next = valueStr.indexOf(ch, off)) != -1) {
                if (!limited || list.size() < limit - 1) {
                    list.add(valueStr.substring(off, next));
                    off = next + 1;
                } else {    // last one
                    //assert (list.size() == limit - 1);
                    list.add(valueStr.substring(off, value.length));
                    off = value.length;
                    break;
                }
            }
            // If no match was found, return this
            if (off == 0)
                return new String[]{valueStr};

            // Add remaining segment
            if (!limited || list.size() < limit)
                list.add(valueStr.substring(off, value.length));

            // Construct result
            int resultSize = list.size();
            if (limit == 0) {
                while (resultSize > 0 && list.get(resultSize - 1).length() == 0) {
                    resultSize--;
                }
            }
            String[] result = new String[resultSize];
            return list.subList(0, resultSize).toArray(result);
        }
        return Pattern.compile(regex).split(valueStr, limit);
    }
}
