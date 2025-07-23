package algorithm16_recursion;

import java.util.ArrayList;
import java.util.List;

public class PringAllSubsquences {
    public static List<String> subs(String s){
        char[] str = s.toCharArray();
        String path = "";
        List<String> ans = new ArrayList<>();
        process1(str,0,ans,path);

        return ans;
    }

    private static void process1(char[] str, int index, List<String> ans, String path) {
        if(index == str.length) {
            ans.add(path);
            return;
        }
        String no = path;
        process1(str,index + 1,ans,no);
        String yes = path + String.valueOf(str[index]);
        process1(str,index + 1,ans,yes);
    }
}
