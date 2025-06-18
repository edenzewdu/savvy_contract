/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.contract.entity;

import com.contract.enums.WarrantyType;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 *
 * @author Lucy
 */
@Entity
@Table(name = "warranties_table")
@NamedQueries({
    @NamedQuery(name = "WarrantiesTable.findAll", query = "SELECT w FROM WarrantiesTable w"),
    @NamedQuery(name = "WarrantiesTable.findById", query = "SELECT w FROM WarrantiesTable w WHERE w.id = :id"),
    @NamedQuery(name = "WarrantiesTable.findByContractId", query = "SELECT w FROM WarrantiesTable w WHERE w.contractId = :contractId"),
    @NamedQuery(name = "WarrantiesTable.findByWarrantedItemDescription", query = "SELECT w FROM WarrantiesTable w WHERE w.warrantedItemDescription = :warrantedItemDescription"),
    @NamedQuery(name = "WarrantiesTable.findByManufacturerModel", query = "SELECT w FROM WarrantiesTable w WHERE w.manufacturerModel = :manufacturerModel"),
    @NamedQuery(name = "WarrantiesTable.findBySerialNumber", query = "SELECT w FROM WarrantiesTable w WHERE w.serialNumber = :serialNumber"),
    @NamedQuery(name = "WarrantiesTable.findByWarrantyType", query = "SELECT w FROM WarrantiesTable w WHERE w.warrantyType = :warrantyType"),
    @NamedQuery(name = "WarrantiesTable.findByWarrantyProviderPartyId", query = "SELECT w FROM WarrantiesTable w WHERE w.warrantyProviderPartyId = :warrantyProviderPartyId"),
    @NamedQuery(name = "WarrantiesTable.findByStartDate", query = "SELECT w FROM WarrantiesTable w WHERE w.startDate = :startDate"),
    @NamedQuery(name = "WarrantiesTable.findByEndDate", query = "SELECT w FROM WarrantiesTable w WHERE w.endDate = :endDate"),
    @NamedQuery(name = "WarrantiesTable.findByIsActive", query = "SELECT w FROM WarrantiesTable w WHERE w.isActive = :isActive"),
    @NamedQuery(name = "WarrantiesTable.findByIsVoided", query = "SELECT w FROM WarrantiesTable w WHERE w.isVoided = :isVoided"),
    @NamedQuery(name = "WarrantiesTable.findByCreatedAt", query = "SELECT w FROM WarrantiesTable w WHERE w.createdAt = :createdAt"),
    @NamedQuery(name = "WarrantiesTable.findByUpdatedAt", query = "SELECT w FROM WarrantiesTable w WHERE w.updatedAt = :updatedAt")})

public class WarrantiesTable implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @NotNull
    @Column(name = "contract_id", insertable = false, updatable = false)
    private int contractId;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "warranted_item_description")
    private String warrantedItemDescription;

    @Size(max = 100)
    @Column(name = "manufacturer_model")
    private String manufacturerModel;

    @Size(max = 100)
    @Column(name = "serial_number")
    private String serialNumber;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "warranty_type")
    private WarrantyType warrantyType;

    @Column(name = "warranty_provider_party_id", insertable = false, updatable = false)
    private Integer warrantyProviderPartyId;

    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(name = "start_date")
    private Date startDate;

    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(name = "end_date")
    private Date endDate;

    @Lob
    @Column(name = "coverage_scope_text")
    private String coverageScopeText;

    @Lob
    @Column(name = "exclusions_text")
    private String exclusionsText;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "is_voided")
    private Boolean isVoided;

    @Lob
    @Column(name = "void_reason")
    private String voidReason;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;

    // Relationships
    @ManyToOne
    @JoinColumn(name = "contract_id", referencedColumnName = "id")
    private ContractsTable contract;

    @ManyToOne
    @JoinColumn(name = "warranty_provider_party_id", referencedColumnName = "id")
    private ContractPartiesTable warrantyProviderParty;

    @OneToMany(mappedBy = "warrantyId")
    private Collection<IssuesDefectsTable> issuesDefectsTableCollection;

    // Transient Fields
    @Transient
    private Integer tempId;

    @Transient
    private Boolean validCell = false;

    // Constructors
    public WarrantiesTable() {
        this.isActive = false;
        this.isVoided = false;
    }

    public WarrantiesTable(Integer id, int contractId, String warrantedItemDescription, WarrantyType warrantyType, Date startDate, Date endDate, Date createdAt, Date updatedAt) {
        this.id = id;
        this.contractId = contractId;
        this.warrantedItemDescription = warrantedItemDescription;
        this.warrantyType = warrantyType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isActive = false;
        this.isVoided = false;
    }

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public int getContractId() { return contractId; }
    public void setContractId(int contractId) { this.contractId = contractId; }

    public String getWarrantedItemDescription() { return warrantedItemDescription; }
    public void setWarrantedItemDescription(String desc) { this.warrantedItemDescription = desc; }

    public String getManufacturerModel() { return manufacturerModel; }
    public void setManufacturerModel(String model) { this.manufacturerModel = model; }

    public String getSerialNumber() { return serialNumber; }
    public void setSerialNumber(String serialNumber) { this.serialNumber = serialNumber; }

    public WarrantyType getWarrantyType() { return warrantyType; }
    public void setWarrantyType(WarrantyType warrantyType) { this.warrantyType = warrantyType; }

    public Integer getWarrantyProviderPartyId() { return warrantyProviderPartyId; }
    public void setWarrantyProviderPartyId(Integer id) { this.warrantyProviderPartyId = id; }

    public Date getStartDate() { return startDate; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }

    public Date getEndDate() { return endDate; }
    public void setEndDate(Date endDate) { this.endDate = endDate; }

    public String getCoverageScopeText() { return coverageScopeText; }
    public void setCoverageScopeText(String text) { this.coverageScopeText = text; }

    public String getExclusionsText() { return exclusionsText; }
    public void setExclusionsText(String text) { this.exclusionsText = text; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean active) { this.isActive = active; }

    public Boolean getIsVoided() { return isVoided; }
    public void setIsVoided(Boolean voided) { this.isVoided = voided; }

    public String getVoidReason() { return voidReason; }
    public void setVoidReason(String voidReason) { this.voidReason = voidReason; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }

    public Collection<IssuesDefectsTable> getIssuesDefectsTableCollection() { return issuesDefectsTableCollection; }
    public void setIssuesDefectsTableCollection(Collection<IssuesDefectsTable> col) { this.issuesDefectsTableCollection = col; }

    public Integer getTempId() { return tempId; }
    public void setTempId(Integer tempId) { this.tempId = tempId; }

    public Boolean getValidCell() { return validCell; }
    public void setValidCell(Boolean validCell) { this.validCell = validCell; }

    public ContractsTable getContract() { return contract; }
    public void setContract(ContractsTable contract) { this.contract = contract; }

    public ContractPartiesTable getWarrantyProviderParty() { return warrantyProviderParty; }
    public void setWarrantyProviderParty(ContractPartiesTable party) { this.warrantyProviderParty = party; }

    @Override
    public int hashCode() {
        return (id != null ? id.hashCode() : 0);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof WarrantiesTable)) return false;
        WarrantiesTable other = (WarrantiesTable) obj;
        return this.id != null && this.id.equals(other.id);
    }

    @Override
    public String toString() {
        return "WarrantiesTable[ id=" + id + " ]";
    }
}
