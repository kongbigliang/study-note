package com.kongbig.java8.date;

import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Set;

/**
 * @Description:
 * @author: lianggangda
 * @date: 2020/5/4 16:50
 */
public class TestLocalDateTime {

    /**
     * 1. LocalDate、LocalTime、LocalDateTime
     */
    @Test
    public void test1() {
        LocalDateTime ld1 = LocalDateTime.now();
        System.out.println(ld1);

        LocalDateTime ld2 = LocalDateTime.of(2020, 5, 4, 9, 10, 10);
        System.out.println(ld2);

        // +5年
        LocalDateTime ld3 = ld2.plusYears(5);
        System.out.println(ld3);

        // -2月
        LocalDateTime ld4 = ld2.minusMonths(2);
        System.out.println(ld4);

        System.out.println(ld1.getYear());
        System.out.println(ld1.getMonthValue());
        System.out.println(ld1.getDayOfMonth());
        System.out.println(ld1.getHour());
        System.out.println(ld1.getMinute());
        System.out.println(ld1.getSecond());
    }

    /**
     * 2. Instant : 时间戳。（使用 Unix 元年  1970年1月1日 00:00:00 所经历的毫秒值）
     */
    @Test
    public void test2() {
        // 默认使用 UTC 时区
        Instant ins = Instant.now();
        System.out.println(ins);

        OffsetDateTime odt = ins.atOffset(ZoneOffset.ofHours(8));
        System.out.println(odt);

        System.out.println(ins.getNano());

        Instant ins2 = Instant.ofEpochSecond(5);
        System.out.println(ins2);
    }

    /**
     * 3.
     * Duration : 用于计算两个“时间”间隔
     * Period : 用于计算两个“日期”间隔
     */
    @Test
    public void test3() {
        Instant ins1 = Instant.now();

        System.out.println("--------------------");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }

        Instant ins2 = Instant.now();

        System.out.println("所耗费时间为：" + Duration.between(ins1, ins2).toMillis());

        System.out.println("----------------------------------");

        LocalDate ld1 = LocalDate.now();
        LocalDate ld2 = LocalDate.of(2011, 1, 1);

        Period pe = Period.between(ld2, ld1);
        System.out.println(pe.getYears());
        System.out.println(pe.getMonths());
        System.out.println(pe.getDays());
    }

    /**
     * 4. TemporalAdjuster : 时间校正器
     */
    @Test
    public void test4() {
        LocalDateTime ld1 = LocalDateTime.now();
        System.out.println(ld1);

        LocalDateTime ld2 = ld1.withDayOfMonth(10);
        System.out.println(ld2);

        LocalDateTime ld3 = ld1.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
        System.out.println(ld3);

        // 自定义：下一个工作日
        LocalDateTime ld5 = ld1.with((l) -> {
            LocalDateTime ld4 = (LocalDateTime) l;

            DayOfWeek dow = ld4.getDayOfWeek();

            if (dow.equals(DayOfWeek.FRIDAY)) {
                return ld4.plusDays(3);
            } else if (dow.equals(DayOfWeek.SATURDAY)) {
                return ld4.plusDays(2);
            } else {
                return ld4.plusDays(1);
            }
        });
        System.out.println(ld5);
    }

    /**
     * 5. DateTimeFormatter : 解析和格式化日期或时间
     */
    @Test
    public void test5() {
        // DateTimeFormatter dtf = DateTimeFormatter.ISO_LOCAL_DATE;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss E");

        LocalDateTime ldt = LocalDateTime.now();
        String strDate = ldt.format(dtf);

        System.out.println(strDate);

        LocalDateTime newLdt = ldt.parse(strDate, dtf);
        System.out.println(newLdt);
    }

    /**
     * 6.ZonedDate、ZonedTime、ZonedDateTime ：带时区的时间或日期
     */
    @Test
    public void test6() {
        Set<String> set = ZoneId.getAvailableZoneIds();
        set.forEach(System.out::println);
    }

    @Test
    public void test7() {
        LocalDateTime ldt = LocalDateTime.now(ZoneId.of("Asia/Shanghai"));
        System.out.println(ldt);

        ZonedDateTime zdt = ZonedDateTime.now(ZoneId.of("US/Pacific"));
        System.out.println(zdt);
    }

}
