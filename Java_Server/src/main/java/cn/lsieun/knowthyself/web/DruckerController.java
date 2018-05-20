package cn.lsieun.knowthyself.web;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.lsieun.knowthyself.dto.DruckerDTO;
import cn.lsieun.knowthyself.dto.ResultDTO;
import cn.lsieun.knowthyself.entity.Drucker;
import cn.lsieun.knowthyself.service.DruckerService;

@RestController
@RequestMapping("/drucker")
public class DruckerController {
    @Autowired
    private DruckerService service;

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public ResultDTO add(@RequestBody Drucker drucker){
        DruckerDTO dto = service.addDrucker(drucker);
        ResultDTO result = new ResultDTO();
        if(dto == null || StringUtils.isBlank(dto.getUid())){
            result.setSuccess(false);
        }
        result.setData(dto);
        return result;
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResultDTO list(@RequestBody Drucker drucker){
        String firstResult = drucker.getFirstResult();
        String maxResult = drucker.getMaxResult();
        String order = drucker.getOrder();

        List<DruckerDTO> druckerList = service.getDruckerList(firstResult, maxResult, order);

        ResultDTO result = new ResultDTO();
        result.setDataList(druckerList);
        return result;
    }
}
