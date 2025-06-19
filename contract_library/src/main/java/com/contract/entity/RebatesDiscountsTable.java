/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.contract.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import com.contract.enums.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 *
 * @author Lucy
 */
@Entity
@Table(name = "rebates_discounts_table")
@NamedQueries({
    @NamedQuery(name = "RebatesDiscountsTable.findAll", query = "SELECT r FROM RebatesDiscountsTable r"),
    @NamedQuery(name = "RebatesDiscountsTable.findById", query = "SELECT r FROM RebatesDiscountsTable r WHERE r.id = :id"),
    @NamedQuery(name = "RebatesDiscountsTable.findByContractId", query = "SELECT r FROM RebatesDiscountsTable r WHERE r.contractId = :contractId"),
    @NamedQuery(name = "RebatesDiscountsTable.findByRebateType", query = "SELECT r FROM RebatesDiscountsTable r WHERE r.rebateType = :rebateType"),
    @NamedQuery(name = "RebatesDiscountsTable.findByCalculationBasis", query = "SELECT r FROM RebatesDiscountsTable r WHERE r.calculationBasis = :calculationBasis"),
    @NamedQuery(name = "RebatesDiscountsTable.findByValueParam", query = "SELECT r FROM RebatesDiscountsTable r WHERE r.valueParam = :valueParam"),
    @NamedQuery(name = "RebatesDiscountsTable.findByDescription", query = "SELECT r FROM RebatesDiscountsTable r WHERE r.description = :description"),
    @NamedQuery(name = "RebatesDiscountsTable.findByTrackingMetricType", query = "SELECT r FROM RebatesDiscountsTable r WHERE r.trackingMetricType = :trackingMetricType"),
    @NamedQuery(name = "RebatesDiscountsTable.findByTargetValueMetric", query = "SELECT r FROM RebatesDiscountsTable r WHERE r.targetValueMetric = :targetValueMetric"),
    @NamedQuery(name = "RebatesDiscountsTable.findByCurrentValueMetric", query = "SELECT r FROM RebatesDiscountsTable r WHERE r.currentValueMetric = :currentValueMetric"),
    @NamedQuery(name = "RebatesDiscountsTable.findByStartDate", query = "SELECT r FROM RebatesDiscountsTable r WHERE r.startDate = :startDate"),
    @NamedQuery(name = "RebatesDiscountsTable.findByEndDate", query = "SELECT r FROM RebatesDiscountsTable r WHERE r.endDate = :endDate"),
    @NamedQuery(name = "RebatesDiscountsTable.findByEstimatedRebateAmount", query = "SELECT r FROM RebatesDiscountsTable r WHERE r.estimatedRebateAmount = :estimatedRebateAmount"),
    @NamedQuery(name = "RebatesDiscountsTable.findByActualRebateAmount", query = "SELECT r FROM RebatesDiscountsTable r WHERE r.actualRebateAmount = :actualRebateAmount"),
    @NamedQuery(name = "RebatesDiscountsTable.findByStatus", query = "SELECT r FROM RebatesDiscountsTable r WHERE r.status = :status"),
    @NamedQuery(name = "RebatesDiscountsTable.findByAppliedToPaymentId", query = "SELECT r FROM RebatesDiscountsTable r WHERE r.appliedToPaymentId = :appliedToPaymentId"),
    @NamedQuery(name = "RebatesDiscountsTable.findByCreatedAt", query = "SELECT r FROM RebatesDiscountsTable r WHERE r.createdAt = :createdAt"),
    @NamedQuery(name = "RebatesDiscountsTable.findByUpdatedAt", query = "SELECT r FROM RebatesDiscountsTable r WHERE r.updatedAt = :updatedAt")})
public class RebatesDiscountsTable implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "rebate_type")
    private RebateType rebateType;
    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "calculation_basis")
    private CalculationBasis calculationBasis;

    @Basic(optional = false)
    @NotNull
    @Column(name = "value_param")
    private BigDecimal valueParam;
    @Size(max = 255)
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "condition_text")
    private String conditionText;
    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "tracking_metric_type")
    private TrackingMetricType trackingMetricType;

    @Column(name = "target_value_metric")
    private BigDecimal targetValueMetric;
    @Column(name = "current_value_metric")
    private BigDecimal currentValueMetric;
    @Column(name = "start_date")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Column(name = "end_date")
    @Temporal(TemporalType.DATE)
    private Date endDate;
    @Column(name = "estimated_rebate_amount")
    private BigDecimal estimatedRebateAmount;
    @Column(name = "actual_rebate_amount")
    private BigDecimal actualRebateAmount;
    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "status")
    private RebateStatus status;
    @Column(name = "applied_to_payment_id")
    private Integer appliedToPaymentId;
    
    @jakarta.persistence.Transient
    private Integer tempId;
    @jakarta.persistence.Transient
    private Boolean validCell = false;
    @Column(name = "created_at", insertable = false, updatable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    private Timestamp updatedAt;

    @ManyToOne(optional = false)
    @NotNull
    @JoinColumn(name = "contract_id", referencedColumnName = "id")
    private ContractsTable contractId;
    public Integer getTempId() {
        return tempId;
    }

    public void setTempId(Integer tempId) {
        this.tempId = tempId;
    }

    public Boolean getValidCell() {
        return validCell;
    }

    public void setValidCell(Boolean validCell) {
        this.validCell = validCell;
    }

    public RebatesDiscountsTable() {
    }

    public RebatesDiscountsTable(Integer id) {
        this.id = id;
    }

    public RebatesDiscountsTable(Integer id, ContractsTable contractId, RebateType rebateType, CalculationBasis calculationBasis, BigDecimal valueParam, String conditionText, RebateStatus status) {
        this.id = id;
        this.contractId = contractId;
        this.rebateType = rebateType;
        this.calculationBasis = calculationBasis;
        this.valueParam = valueParam;
        this.conditionText = conditionText;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ContractsTable getContractId() {
        return contractId;
    }

    public void setContractId(ContractsTable contractId) {
        this.contractId = contractId;
    }

    public RebateType getRebateType() {
        return rebateType;
    }

    public void setRebateType(RebateType rebateType) {
        this.rebateType = rebateType;
    }

    public CalculationBasis getCalculationBasis() {
        return calculationBasis;
    }

    public void setCalculationBasis(CalculationBasis calculationBasis) {
        this.calculationBasis = calculationBasis;
    }

    public BigDecimal getValueParam() {
        return valueParam;
    }

    public void setValueParam(BigDecimal valueParam) {
        this.valueParam = valueParam;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getConditionText() {
        return conditionText;
    }

    public void setConditionText(String conditionText) {
        this.conditionText = conditionText;
    }

    public TrackingMetricType getTrackingMetricType() {
        return trackingMetricType;
    }

    public void setTrackingMetricType(TrackingMetricType trackingMetricType) {
        this.trackingMetricType = trackingMetricType;
    }

    public BigDecimal getTargetValueMetric() {
        return targetValueMetric;
    }

    public void setTargetValueMetric(BigDecimal targetValueMetric) {
        this.targetValueMetric = targetValueMetric;
    }

    public BigDecimal getCurrentValueMetric() {
        return currentValueMetric;
    }

    public void setCurrentValueMetric(BigDecimal currentValueMetric) {
        this.currentValueMetric = currentValueMetric;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getEstimatedRebateAmount() {
        return estimatedRebateAmount;
    }

    public void setEstimatedRebateAmount(BigDecimal estimatedRebateAmount) {
        this.estimatedRebateAmount = estimatedRebateAmount;
    }

    public BigDecimal getActualRebateAmount() {
        return actualRebateAmount;
    }

    public void setActualRebateAmount(BigDecimal actualRebateAmount) {
        this.actualRebateAmount = actualRebateAmount;
    }

    public RebateStatus getStatus() {
        return status;
    }

    public void setStatus(RebateStatus status) {
        this.status = status;
    }

    public Integer getAppliedToPaymentId() {
        return appliedToPaymentId;
    }

    public void setAppliedToPaymentId(Integer appliedToPaymentId) {
        this.appliedToPaymentId = appliedToPaymentId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RebatesDiscountsTable)) {
            return false;
        }
        RebatesDiscountsTable other = (RebatesDiscountsTable) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.contract.entity.RebatesDiscountsTable[ id=" + id + " ]";
    }
    
}
