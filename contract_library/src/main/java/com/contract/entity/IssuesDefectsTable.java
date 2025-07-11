/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.contract.entity;

import com.contract.enums.Severity;
import com.contract.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 *
 * @author Lucy
 */
@Entity
@Table(name = "issues_defects_table")
@NamedQueries({
    @NamedQuery(name = "IssuesDefectsTable.findAll", query = "SELECT i FROM IssuesDefectsTable i"),
    @NamedQuery(name = "IssuesDefectsTable.findById", query = "SELECT i FROM IssuesDefectsTable i WHERE i.id = :id"),
    @NamedQuery(name = "IssuesDefectsTable.findByContractId", query = "SELECT i FROM IssuesDefectsTable i WHERE i.contractId = :contractId"),
    @NamedQuery(name = "IssuesDefectsTable.findByIssueCategory", query = "SELECT i FROM IssuesDefectsTable i WHERE i.issueCategory = :issueCategory"),
    @NamedQuery(name = "IssuesDefectsTable.findByReportedByPartyId", query = "SELECT i FROM IssuesDefectsTable i WHERE i.reportedByPartyId = :reportedByPartyId"),
    @NamedQuery(name = "IssuesDefectsTable.findByReportedDate", query = "SELECT i FROM IssuesDefectsTable i WHERE i.reportedDate = :reportedDate"),
    @NamedQuery(name = "IssuesDefectsTable.findBySeverity", query = "SELECT i FROM IssuesDefectsTable i WHERE i.severity = :severity"),
    @NamedQuery(name = "IssuesDefectsTable.findByStatus", query = "SELECT i FROM IssuesDefectsTable i WHERE i.status = :status"),
    @NamedQuery(name = "IssuesDefectsTable.findByAssignedToUserId", query = "SELECT i FROM IssuesDefectsTable i WHERE i.assignedToUserId = :assignedToUserId"),
    @NamedQuery(name = "IssuesDefectsTable.findByResolutionDate", query = "SELECT i FROM IssuesDefectsTable i WHERE i.resolutionDate = :resolutionDate"),
    @NamedQuery(name = "IssuesDefectsTable.findByClosureDate", query = "SELECT i FROM IssuesDefectsTable i WHERE i.closureDate = :closureDate"),
    @NamedQuery(name = "IssuesDefectsTable.findByClientAcceptanceDocId", query = "SELECT i FROM IssuesDefectsTable i WHERE i.clientAcceptanceDocId = :clientAcceptanceDocId"),
    @NamedQuery(name = "IssuesDefectsTable.findByCreatedAt", query = "SELECT i FROM IssuesDefectsTable i WHERE i.createdAt = :createdAt"),
    @NamedQuery(name = "IssuesDefectsTable.findByUpdatedAt", query = "SELECT i FROM IssuesDefectsTable i WHERE i.updatedAt = :updatedAt")})
public class IssuesDefectsTable implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 100)
    @Column(name = "issue_category")
    private String issueCategory;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "issue_description")
    private String issueDescription;
    @Basic(optional = false)
    @NotNull
    @Column(name = "reported_date")
    @Temporal(TemporalType.DATE)
    private Date reportedDate;
    @Lob
    @Size(max = 65535)
    @Column(name = "impact")
    private String impact;
    @Basic(optional = false)
    @Enumerated(EnumType.STRING)
    @Column(name = "severity")
    private Severity severity;

    @Basic(optional = false)
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "assigned_to_user_id")
    private Integer assignedToUserId;
    @Lob
    @Size(max = 65535)
    @Column(name = "resolution_details")
    private String resolutionDetails;
    @Column(name = "resolution_date")
    @Temporal(TemporalType.DATE)
    private Date resolutionDate;
    @Column(name = "closure_date")
    @Temporal(TemporalType.DATE)
    private Date closureDate;
    @Column(name = "client_acceptance_doc_id")
    private Integer clientAcceptanceDocId;
    @Column(name = "created_at", insertable = false, updatable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    private Timestamp updatedAt;

    @ManyToOne
    @JoinColumn(name = "contract_id", referencedColumnName = "id")
    private ContractsTable contractId;

    @ManyToOne
    @JoinColumn(name = "reported_by_party_id", referencedColumnName = "id")
    private ContractPartiesTable reportedByPartyId;

    @JoinColumn(name = "warranty_id", referencedColumnName = "id")
    @ManyToOne
    private WarrantiesTable warrantyId;
    
    @jakarta.persistence.Transient
    private Integer tempId;
    @jakarta.persistence.Transient
    private Boolean validCell = false;

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

    public IssuesDefectsTable() {
    }

    public IssuesDefectsTable(Integer id) {
        this.id = id;
    }

    public IssuesDefectsTable(Integer id, ContractsTable contractId, String issueDescription, ContractPartiesTable reportedByPartyId, Date reportedDate, Severity severity, Status status, Date createdAt, Date updatedAt) {
        this.id = id;
        this.contractId = contractId;
        this.issueDescription = issueDescription;
        this.reportedByPartyId = reportedByPartyId;
        this.reportedDate = reportedDate;
        this.severity = severity;
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

    public String getIssueCategory() {
        return issueCategory;
    }

    public void setIssueCategory(String issueCategory) {
        this.issueCategory = issueCategory;
    }

    public String getIssueDescription() {
        return issueDescription;
    }

    public void setIssueDescription(String issueDescription) {
        this.issueDescription = issueDescription;
    }

    public ContractPartiesTable getReportedByPartyId() {
        return reportedByPartyId;
    }

    public void setReportedByPartyId(ContractPartiesTable reportedByPartyId) {
        this.reportedByPartyId = reportedByPartyId;
    }

    public Date getReportedDate() {
        return reportedDate;
    }

    public void setReportedDate(Date reportedDate) {
        this.reportedDate = reportedDate;
    }

    public String getImpact() {
        return impact;
    }

    public void setImpact(String impact) {
        this.impact = impact;
    }

    public Integer getAssignedToUserId() {
        return assignedToUserId;
    }

    public void setAssignedToUserId(Integer assignedToUserId) {
        this.assignedToUserId = assignedToUserId;
    }

    public String getResolutionDetails() {
        return resolutionDetails;
    }

    public void setResolutionDetails(String resolutionDetails) {
        this.resolutionDetails = resolutionDetails;
    }

    public Date getResolutionDate() {
        return resolutionDate;
    }

    public void setResolutionDate(Date resolutionDate) {
        this.resolutionDate = resolutionDate;
    }

    public Date getClosureDate() {
        return closureDate;
    }

    public void setClosureDate(Date closureDate) {
        this.closureDate = closureDate;
    }

    public Integer getClientAcceptanceDocId() {
        return clientAcceptanceDocId;
    }

    public void setClientAcceptanceDocId(Integer clientAcceptanceDocId) {
        this.clientAcceptanceDocId = clientAcceptanceDocId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Severity getSeverity() {
        return severity;
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public WarrantiesTable getWarrantyId() {
        return warrantyId;
    }

    public void setWarrantyId(WarrantiesTable warrantyId) {
        this.warrantyId = warrantyId;
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
        if (!(object instanceof IssuesDefectsTable)) {
            return false;
        }
        IssuesDefectsTable other = (IssuesDefectsTable) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.contract.entity.IssuesDefectsTable[ id=" + id + " ]";
    }
    
}
