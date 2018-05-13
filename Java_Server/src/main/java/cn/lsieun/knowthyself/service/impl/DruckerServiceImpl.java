package cn.lsieun.knowthyself.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.lsieun.knowthyself.dao.DruckerDao;
import cn.lsieun.knowthyself.dto.DruckerDTO;
import cn.lsieun.knowthyself.dto.ValidationDTO;
import cn.lsieun.knowthyself.entity.Drucker;
import cn.lsieun.knowthyself.exception.DruckerException;
import cn.lsieun.knowthyself.service.DruckerService;
import cn.lsieun.knowthyself.util.page.PageUtil;
import cn.lsieun.knowthyself.util.snowflake.SnowflakeIdWorker;


@Service("druckerService")
public class DruckerServiceImpl implements DruckerService{

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    @Autowired
    private DruckerDao dao;

    @Override
    public DruckerDTO addDrucker(Drucker drucker) {
        /*
        处理思路：
        （1）进行数据有效性检验
        （2）将数据添加到数据库中
        （3）将结果进行返回
        * */
        // region （1）进行数据有效性检验
        ValidationDTO validationDTO = validate(drucker, true);
        if(validationDTO.isSuccess() == false) throw new DruckerException(validationDTO.getErrMsg());
        // endregion

        // region （2）将数据添加到数据库中
        long sid = snowflakeIdWorker.nextId();
        String uid = String.valueOf(sid);
        drucker.setUid(uid);

        int effectedNum = dao.insert(drucker);
        if(effectedNum != 1) throw new DruckerException("操作失败，数据库出错啦！");
        // endregion

        // region （3）将结果进行返回
        DruckerDTO dto = entity2DTO(drucker);
        return dto;
        // endregion
    }

    @Override
    public boolean modifyDrucker(Drucker drucker) {
        /*
        处理思路：
        （1）进行数据有效性检验
        （2）将数据更新到数据库中
        （3）将结果进行返回
        * */

        // region （1）进行数据有效性检验
        ValidationDTO validationDTO = validate(drucker, false);
        if(validationDTO.isSuccess() == false) throw new DruckerException(validationDTO.getErrMsg());
        // endregion

        // region （2）将数据更新到数据库中
        drucker.setLastEditTime(new Date());
        int effectedNum = dao.update(drucker);
        if(effectedNum != 1) throw new DruckerException("操作失败，数据库出错啦！");
        // endregion

        // region （3）将结果进行返回
        return true;
        // endregion
    }

    @Override
    public DruckerDTO getDrucker(String uid) {
        Drucker drucker = dao.getById(uid);
        DruckerDTO dto = entity2DTO(drucker);
        return dto;
    }

    @Override
    public boolean delete(String uid) {
        Drucker drucker = new Drucker();
        drucker.setUid(uid);
        drucker.setIsValid(0);
        int effectedNum = dao.update(drucker);
        if(effectedNum != 1) return false;
        return true;
    }

    @Override
    public List<DruckerDTO> getList(Map<String,String> queryMap) {
        List<Drucker> druckerList = dao.getList(queryMap);
        List<DruckerDTO> dtoList = entityList2DTOList(druckerList);
        return dtoList;
    }

    @Override
    public List<DruckerDTO> getDruckerList(String firstResult, String maxResult, String order) {
        Map<String,String> queryMap = new HashMap<String,String>();
        queryMap.put("firstResult", PageUtil.formatFirstResult(firstResult));
        queryMap.put("maxResult",PageUtil.formatMaxResult(maxResult));
        if("desc".equalsIgnoreCase(order)){
            queryMap.put("sortType","1");
        }
        else if("asc".equalsIgnoreCase(order)){
            queryMap.put("sortType","2");
        }
        else{
            queryMap.put("sortType","1");
        }
        queryMap.put("isValid","1");
        List<Drucker> druckerList = dao.getList(queryMap);
        List<DruckerDTO> dtoList = entityList2DTOList(druckerList);
        return dtoList;
    }

    @Override
    public int getCount(Map<String,String> queryMap) {
        return dao.getCount(queryMap);
    }

    @Override
    public DruckerDTO entity2DTO(Drucker entity) {
        DruckerDTO dto = new DruckerDTO();
        try {
            PropertyUtils.copyProperties(dto,entity);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return dto;
    }

    @Override
    public List<DruckerDTO> entityList2DTOList(List<Drucker> entityList) {
        if(entityList == null) return null;
        List<DruckerDTO> dtoList = new ArrayList<DruckerDTO>();
        for(Drucker entity : entityList){
            DruckerDTO dto = entity2DTO(entity);
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Override
    public ValidationDTO validate(Drucker drucker, boolean isCreate) {
        String operation = (isCreate)?("添加"):("更新");
        String errMsg = "";
        if(drucker == null)
        {
            errMsg = operation + "操作失败，对象不能为空！";
            return new ValidationDTO(false,errMsg);
        }
        if(isCreate && StringUtils.isNotBlank(drucker.getUid())){
            errMsg = "创建操作失败，主键ID不为空！";
            return new ValidationDTO(false,errMsg);
        }
        if(!isCreate && StringUtils.isBlank(drucker.getUid())){
            errMsg = "更新操作失败，主键ID不能为空！";
            return new ValidationDTO(false,errMsg);
        }
        if(StringUtils.isBlank(drucker.getUserid())) {
            errMsg = operation + "操作失败，用户ID不能为空！";
            return new ValidationDTO(false,errMsg);
        }
        if(StringUtils.isBlank(drucker.getTitle())) {
            errMsg = operation + "操作失败，标题名称不能为空！";
            return new ValidationDTO(false,errMsg);
        }
        if(StringUtils.isBlank(drucker.getUrl())) {
            errMsg = operation + "操作失败，URL不能为空！";
            return new ValidationDTO(false,errMsg);
        }
        if(drucker.getPubTime() == null){
            errMsg = operation + "操作失败，发布时间不能为空！";
            return new ValidationDTO(false,errMsg);
        }

        return new ValidationDTO(true,errMsg);
    }

}
