package com.digitalqd.production.entity.primary;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonAlias;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

// 介质数据

@Data
@Entity
@Table(name="energy_metrical_data")
public class EnergyMetricalDataDO {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition="BIGINT UNSIGNED")
    Long id;
    
    @Column(name="id_work_shop", nullable=false, columnDefinition="BIGINT UNSIGNED comment '车间ID'")
    Long idWorkShop;       // 车间ID
    @Column(name="id_energy_metrical", nullable=false, columnDefinition="BIGINT UNSIGNED comment '介质ID'")
    Long idEnergyMetrical;    // 介质


    @JsonAlias({ "RecordTime" })
    @Column(name="record_time", columnDefinition="timestamp comment '记录时间'")
    Timestamp recordTime;   // 记录时间

    @JsonAlias({ "DataValue" })
    @Column(name="data_value", columnDefinition="DECIMAL(10,3) UNSIGNED comment '用量'")
    BigDecimal dataValue;          // 用量

    @JsonAlias({ "MaxAccuValue" })
    @Column(name="max_accu_value", columnDefinition="DECIMAL(10,3) UNSIGNED comment '最大累计'")
    BigDecimal maxAccuValue;    // 最大累计

    @JsonAlias({ "MinAccuValue" })
    @Column(name="min_accu_value", columnDefinition="DECIMAL(10,3) UNSIGNED comment '最小累计'")
    BigDecimal minAccuValue;    // 最小累计

    @JsonAlias({ "AvgAccuValue" })
    @Column(name="avg_accu_value", columnDefinition="DECIMAL(10,3) UNSIGNED comment '平均累计'")
    BigDecimal avgAccuValue;    // 平均累计


    @Column(name="create_time")
    Timestamp createTime;
    @Column(name="update_time")
    Timestamp updateTime;
    @Column(name="is_deleted",columnDefinition="BIT(1)")
    Boolean deleted = false;
}
