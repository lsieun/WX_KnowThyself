package cn.lsieun.knowthyself.service;

import java.util.List;

import cn.lsieun.knowthyself.dto.TimeStatDTO;

public interface TimeStatService {
    List<TimeStatDTO> getTimeDayStat(String userid, String yyyy_MM_dd);

    List<TimeStatDTO> getTimeWeekStat(String userid, String yyyyMMdd);

    List<TimeStatDTO> getTimeMonthStat(String userid, String yyyyMMdd);
}
