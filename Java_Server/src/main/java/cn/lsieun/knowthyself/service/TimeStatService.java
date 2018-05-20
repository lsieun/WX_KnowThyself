package cn.lsieun.knowthyself.service;

import java.util.List;

import cn.lsieun.knowthyself.dto.TimeStatDTO;

public interface TimeStatService {
    List<TimeStatDTO> getTimeDayStat(String userid, String currentDay);

    List<TimeStatDTO> getTimeWeekStat(String userid, String mondayDate, String sundayDate);

    List<TimeStatDTO> getTimeMonthStat(String userid, String currentMonth);
}
