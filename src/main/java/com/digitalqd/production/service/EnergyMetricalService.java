package com.digitalqd.production.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalqd.production.utils.Python;
import com.fasterxml.jackson.core.type.TypeReference;
import com.digitalqd.production.dao.primary.EnergyMetricalDataDAO;
import com.digitalqd.production.entity.primary.EnergyMetricalDataDO;
import com.digitalqd.production.utils.JsonConvert;


@Service
public class EnergyMetricalService {
    @Autowired
    private Python python;
    @Autowired
    private JsonConvert jsonConvert;
    @Autowired
    private EnergyMetricalDataDAO resourceDataDAO;

    // 传入介质、车间、开始时间、结束时间 (时间戳)
    public void saveEnergyMetricalDataToDb(String energyMetrical, String workShop, String startTime, String endTime) {
        String str = python.run("energy_metrical/main.py",
                new String[] { energyMetrical, workShop, startTime, endTime });
        List<EnergyMetricalDataDO> resourceDatasDataDO_list = jsonConvert.jsonToType(str, new TypeReference<List<EnergyMetricalDataDO>>() {});
        for(var r:resourceDatasDataDO_list){
            r.setCreateTime(new Timestamp(System.currentTimeMillis()));
            r.setIdEnergyMetrical(1L);
            r.setIdWorkShop(1L);
            resourceDataDAO.save(r);
        }
    }
}