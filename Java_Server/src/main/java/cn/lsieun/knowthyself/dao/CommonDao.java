package cn.lsieun.knowthyself.dao;

import java.util.List;
import java.util.Map;

public interface CommonDao<T> {
    List<T> getList(Map<String,String> queryMap);
    int getCount(Map<String,String> queryMap);
    T getById(String pkId);
    int insert(T entity);
    int update(T entity);
    int delete(String pkId);
}
