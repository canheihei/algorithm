package algorithm00_otherPractice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class groupAnagrams {
    // 49. 字母异位词分组
    public List<List<String>> groupAnagrams(String[] strs) {
        HashMap<String,List<String>> hashMap = new HashMap<>();
        for (int i = 0; i < strs.length; i++) {
            char[] temp = strs[i].toCharArray();
            Arrays.sort(temp);
            // 通过识别正序字符串这个键来获取值，这个值就是字母异位词存放的链表
            List<String> list = hashMap.getOrDefault(new String(temp),new ArrayList<>());
            list.add(strs[i]);
            hashMap.put(new String(temp),list);
        }
        return new ArrayList<>(hashMap.values());
    }
}
