package com.digitalqd.production.dao.primary;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.digitalqd.production.entity.primary.EnergyMetricalDataDO;

import jakarta.transaction.Transactional;

public interface EnergyMetricalDataDAO extends CrudRepository<EnergyMetricalDataDO,Long>{
    // 加载默认数据

    // @Transactional
    // @Modifying
    // @Query(value = "truncate table energy_metrical_data",nativeQuery = true)
    // void truncateTable();
}
