package cn.lsieun.knowthyself.util.page;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.Date;

public class PageUtil {
    private static final int MIN_PAGE_SIZE = 5;
    private static final int PAGE_SIZE = 20;

    public static String formatFirstResult(String firstResult){
        long start = NumberUtils.toLong(firstResult, 0L);
        return String.valueOf(start);
    }

    public static String formatMaxResult(String maxResult){
        long pagesize = NumberUtils.toLong(maxResult, MIN_PAGE_SIZE);
        if(pagesize < 1 || pagesize > PAGE_SIZE) pagesize = PAGE_SIZE;
        return String.valueOf(pagesize);
    }

    public static String formatyyyyMMdd(String yyyyMMdd){
        Date currentDate = null;
        try {
            currentDate = DateUtils.parseDate(yyyyMMdd,"yyyyMMdd", "yyyy-MM-dd", "yyyy-MM-dd HH:mm", "yyyy-MM-dd HH:mm:ss");
        } catch (ParseException e) {
            currentDate = new Date();
            e.printStackTrace();
        }
        return DateFormatUtils.format(currentDate, "yyyy-MM-dd");
    }

}
