package com.digitalqd.production.entity.primary;

import java.sql.Timestamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

// 介质
@Data
@Entity
@Table(name = "energy_metrical_category")
public class EnergyMetricalCategoryDO {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "BIGINT UNSIGNED")
    Long id;

    @Column(name = "full_name", nullable = false, columnDefinition = "varchar(255) comment '全称(英文)'")
    String fullName; // 全程(英文)
    @Column(name = "short_name", columnDefinition = "varchar(255) comment '英文缩写'")
    String shortName; // 缩写
    @Column(name = "ch_name", columnDefinition = "varchar(255) comment '中文名称'")
    String chName; // 中文名称
    @Column(name = "description", columnDefinition = "varchar(255) comment '描述'")
    String description;

    @Column(name = "create_time")
    Timestamp createTime;
    @Column(name = "update_time")
    Timestamp updateTime;
    @Column(name = "is_deleted", columnDefinition = "BIT(1)")
    Boolean deleted = false;
}
