package xyz.zinglizingli.books.core.utils;

/**
 * @author 11797
 */
public class CatUtil {

    public static int getCatNum(String catName) {
        int catNum;
        switch (catName) {
            case "武侠仙侠": {
                catNum = 2;
                break;
            }
            case "都市言情": {
                catNum = 3;
                break;
            }
            case "历史军事": {
                catNum = 4;
                break;
            }
            case "科幻灵异": {
                catNum = 5;
                break;
            }
            case "网游竞技": {
                catNum = 6;
                break;
            }
            case "女生频道": {
                catNum = 7;
                break;
            }
            default: {
                catNum = 1;
                break;
            }
        }
        return catNum;
    }



    /**
     * 查询轻小说分类名
     * */
    public static String getSoftCatNameById(Integer softCat) {
        String catName = "其他";

        switch (softCat) {
            case 21: {
                catName = "魔幻";
                break;
            }
            case 22: {
                catName = "玄幻";
                break;
            }
            case 23: {
                catName = "古风";
                break;
            }
            case 24: {
                catName = "科幻";
                break;
            }
            case 25: {
                catName = "校园";
                break;
            }
            case 26: {
                catName = "都市";
                break;
            }
            case 27: {
                catName = "游戏";
                break;
            }
            case 28: {
                catName = "同人";
                break;
            }
            case 29: {
                catName = "悬疑";
                break;
            }
            case 0: {
                catName = "动漫";
                break;
            }
            default: {
                break;
            }


        }
        return catName;

    }

    /**
     * 查询漫画分类名
     * */
    public static String getMhCatNameById(Integer softCat) {
        String catName = "其他";

        switch (softCat) {
            case 3262: {
                catName = "少年漫";
                break;
            }
            case 3263: {
                catName = "少女漫";
                break;
            }
            default: {
                break;
            }


        }
        return catName;

    }


    /**
     * 获取分类名
     * */
    public static String getCatNameById(Integer catid) {
        String catName = "其他";

        switch (catid) {
            case 1: {
                catName = "玄幻奇幻";
                break;
            }
            case 2: {
                catName = "武侠仙侠";
                break;
            }
            case 3: {
                catName = "都市言情";
                break;
            }
            case 4: {
                catName = "历史军事";
                break;
            }
            case 5: {
                catName = "科幻灵异";
                break;
            }
            case 6: {
                catName = "网游竞技";
                break;
            }
            case 7: {
                catName = "女生频道";
                break;
            }
            case 8: {
                catName = "轻小说";
                break;
            }
            case 9: {
                catName = "漫画";
                break;
            }
            default: {
                break;
            }


        }
        return catName;
    }
}
