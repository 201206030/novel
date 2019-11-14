package xyz.zinglizingli.common.utils;


public class NumberUtil {

    public static int solve(String s) {
        int i = s.indexOf("万");
        if (i != -1) {
            int l = solve(s.substring(0, i));
            int r = solve(s.substring(i+1));
            return l*10000 + r;
        }
        i = s.indexOf("千");
        if (i != -1) {
            int l = solve(s.substring(0, i));
            int r = solve(s.substring(i+1));
            return l*1000 + r;
        }
        i = s.indexOf("百");
        if (i != -1) {
            int l = solve(s.substring(0, i));
            int r = solve(s.substring(i+1));
            return l*100 + r;
        }
        i = s.indexOf("十");
        if (i != -1) {
            int l = solve(s.substring(0, i));
            if (l == 0)
                l = 1;
            int r = solve(s.substring(i+1));
            return l*10 + r;
        }
        i = s.indexOf("零");
        if (i != -1) {
            int l = solve(s.substring(0, i));
            int r = solve(s.substring(i+1));
            return l + r;
        }
        i = 0;
        switch (s) {
            case "九":
                return 9;
            case "八":
                return 8;
            case "七":
                return 7;
            case "六":
                return 6;
            case "五":
                return 5;
            case "四":
                return 4;
            case "三":
                return 3;
            case "二":
                return 2;
            case "一":
                return 1;
        }
        return 0;
    }
    public static String solve(int n) {
        int w = n / 10000, q = n / 1000, b = n / 100, s = n / 10;
        if (w > 0) {
            String l = solve(n/10000);
            String r = solve(n%10000);
            if ((n%10000)/1000 == 0)
                r = "零" + r;
            return l + "万" + r;
        }
        if (q > 0) {
            String l = solve(n/1000);
            String r = solve(n%1000);
            if ((n%1000)/100 == 0)
                r = "零" + r;
            return l + "千" + r;
        }
        if (b > 0) {
            String l = solve(n/100);
            String r = solve(n%100);
            if ((n%100)/10 == 0)
                r = "零" + r;
            return l + "百" + r;
        }
        if (s > 0) {
            String l = solve(n/10);
            String r = solve(n%10);
            return l + "十" + r;
        }
        switch (n){
            case 1:
                return "一";
            case 2:
                return "二";
            case 3:
                return "三";
            case 4:
                return "四";
            case 5:
                return "五";
            case 6:
                return "六";
            case 7:
                return "七";
            case 8:
                return "八";
            case 9:
                return "九";
        }
        return "";
    }

    public static void main(String[] args) {
        System.out.println(solve("五百七十八"));
        System.out.println(solve(3786));
    }
}
