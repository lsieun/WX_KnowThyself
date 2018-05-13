package cn.lsieun.knowthyself.service;

import java.util.List;
import java.util.Map;

import cn.lsieun.knowthyself.dto.DruckerDTO;
import cn.lsieun.knowthyself.dto.ValidationDTO;
import cn.lsieun.knowthyself.entity.Drucker;



public interface DruckerService {

    DruckerDTO addDrucker(Drucker drucker);

    boolean modifyDrucker(Drucker drucker);

    boolean delete(String uid);

    DruckerDTO getDrucker(String uid);

    List<DruckerDTO> getList(Map<String,String> queryMap);

    List<DruckerDTO> getDruckerList(String firstResult, String maxResult, String order);

    int getCount(Map<String,String> queryMap);

    DruckerDTO entity2DTO(Drucker entity);

    List<DruckerDTO> entityList2DTOList(List<Drucker> entityList);

    ValidationDTO validate(Drucker drucker, boolean isCreate);

}
