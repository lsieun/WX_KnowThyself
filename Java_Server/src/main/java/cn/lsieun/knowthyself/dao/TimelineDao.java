package cn.lsieun.knowthyself.dao;

import cn.lsieun.knowthyself.entity.Timeline;
import org.springframework.stereotype.Repository;

@Repository(value = "timelineDao")
public interface TimelineDao extends CommonDao<Timeline>{

}
