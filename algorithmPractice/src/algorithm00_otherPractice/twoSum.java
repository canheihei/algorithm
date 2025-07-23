package algorithm00_otherPractice;

import java.util.HashMap;

public class twoSum {
    // 1.两数相加，哈希法
    public int[] twoSum(int[] numbers, int target) {
        HashMap<Integer,Integer> hashMap = new HashMap<>();
        for (int i = 0; i < numbers.length; i++) {
            int c = target - numbers[i];
            if(hashMap.containsKey(c)) {
                return new int[]{
                  hashMap.get(c),i
                };
            }
            hashMap.put(numbers[i],i);
        }
        return null;
    }
}
