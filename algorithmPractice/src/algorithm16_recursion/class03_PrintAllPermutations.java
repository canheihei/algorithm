package algorithm16_recursion;

import java.util.ArrayList;
import java.util.List;

public class class03_PrintAllPermutations {
    public  static List<String> permutation1(String s) {
        List<String> ans = new ArrayList<>();
        if(s == null || s.length() == 0) {
            return ans;
        }
        char[] str = s.toCharArray();

        ArrayList<Character> rest = new ArrayList<>();
        for (char cha : str) {
            rest.add(cha);
        }

        String path = "";
        f(rest,path,ans);
        return ans;
    }

    public static void f(ArrayList<Character> rest,String path,List<String> ans) {
        if(rest.isEmpty()) {
            ans.add(path);
        }else{
            int N = rest.size();
            for (int i = 0; i < N; i++) {
                char cur = rest.get(i);
                rest.remove(i);
                f(rest,path + cur,ans);
                rest.add(i,cur);
            }
        }
    }


    public  static List<String> permutation2(String s) {
        List<String> ans = new ArrayList<>();
        if(s == null || s.length() == 0) {
            return ans;
        }
        char[] str = s.toCharArray();

        ArrayList<Character> rest = new ArrayList<>();
        for (char cha : str) {
            rest.add(cha);
        }

        String path = "";
        f(rest,path,ans);
        return ans;
    }

    public static void g(char[] str,int index,List<String> ans) {
        if(index == str.length) {
            ans.add(String.valueOf(str));
        }else {
            for (int i = index; i < str.length; i++) {
                swap(str,index,i);
                g(str,index + 1, ans);
                swap(str,index,i);

            }
        }

    }


    private static void swap(char[] str, int index, int i) {
        char temp = str[index];
        str[index] = str[i];
        str[i] = temp;
    }
}
