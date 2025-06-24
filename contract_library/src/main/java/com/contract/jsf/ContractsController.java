package com.contract.jsf;

import com.contract.entity.*;
import com.contract.enums.*;
import com.contract.jsf.util.JsfUtil;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


@Named("contractsController")
@ViewScoped
public class ContractsController implements Serializable {

    @Inject
    private ContractsTableController contractsTableController;

    @Inject
    private ContractTypesTableController contractTypesTableController;

    @Inject
    private ContractStatusesTableController contractStatusesTableController;

    @Inject
    private ContractItemTableController contractItemTableController;

    @Inject
    private ContractPartyRolesTableController contractPartyRolesTableController;

    @Inject
    private ContractPartiesTableController contractPartiesTableController;

    @Inject
    private PartyTypesTableController partyTypesTableController;

    @Inject
    private PartyRoleTypesTableController partyRoleTypesTableController;

    @Inject
    private ContractKeyDatesMilestonesTableController contractKeyDatesMilestonesTableController;

    @Inject
    private MilestoneStatusesTableController milestoneStatusesTableController;

    @Inject
    private IssuesDefectsTableController issuesDefectsTableController;

    @Inject
    private WarrantiesTableController warrantiesTableController;

    @Inject
    private PenaltiesController penaltiesController;

    @Inject
    private RebatesDiscountsTableController rebatesDiscountsTableController;

    @Inject
    private PaymentFrequenciesTableController paymentFrequenciesTableController;

    @Inject
    private RetentionsTableController retentionsTableController;

    private static final long serialVersionUID = 1L;

    // Basic contract fields
    private boolean createNewType = false;
    private boolean createNewStatus = false;
    private boolean createNewPartyType = false;
    private boolean createNewParty = false;
    private boolean createNewPartyRoleType = false;
    private boolean createNewMilestoneStatus = false;
    private boolean createNewWarrantyId = false;

    private String contractTitle;
    private String referenceNumber;
    private ContractTypesTable contractTypeId;
    private String typeName;
    private String typeDescription;
    private String contractSide;
    private ContractStatusesTable contractStatusId;

    private String statusName;
    private String statusDescription;
    private String descriptionScope;

    private Date effectiveDate;
    private Date initialExpiryDate;
    private Date currentExpiryDate;

    private Integer noticePeriodDaysForTermination;
    private Boolean autoRenewalEnabled = false;
    private Integer renewalTermMonths;

    private Double contractValue;
    private String currencyCode;
    private String governingLawJurisdiction;
    private Integer parentContractId;
    private String createdByUserId;
    private Date createdAt;
    private String updatedByUserId;
    private Date updatedAt;
    private String contractIdentitySide;

    // Dates and Fees
    private Date initalStartDate;
    private Date endStartDate;
    private Double cancellationFee;
    private Double retinageAmount;
    private Integer taxRate;
    private Integer contractCurrency;

    // Address fields
    private Integer addressNumberCustomer1;
    private Integer addressNumberCustomer2;
    private Integer addressNumberCustomer3;
    private Integer addressNumberRep1;
    private Integer addressNumberRep2;
    private Integer addressNumberRep3;
    private Integer addressNumberRep4;
    private Integer addressNumberPrepared;
    private Integer addressNumberAproved;
    private Integer addressNumberChecked;

    // Contract Item
    private Double amountQuantity;
    private Integer itemsTable;
    private Integer unitOfMeasure;
    private Double unitAmount;
    private Double extendedAmount;
    private String usersId;
    private Date dateEnered;
    private Date dateUpdated;


    // Party form flags
    private boolean partyFormDisabled = false;
    private boolean typeFormDisabled = false;

    // Contract Parties Type
    private String legalName;
    private String shortName;
    private PartyTypesTable partyTypeId;
    private String registrationNumber;
    private Integer addressNumberAb;
    private Boolean isPrimaryCounterParty;
    private ContractPartiesTable partyId;
    private PartyRoleTypesTable roleInContractId;
    private String roleName;

    //milestone
    private String milestoneName;
    private String milestoneDescription;
    private Date dueDate;
    private Integer assignedContactId;
    private Integer reminderLeadDays;
    private Date completedDate;
    private String milestoneStatusName;
    private MilestoneStatusesTable statusId;
    private ContractPartyRolesTable responsiblePartyId;

    //issue
    private String issueCategory;
    private String issueDescription;
    private Date reportedDate;
    private String impact;
    private Severity severity;
    private Status status;
    private Integer assignedToUserId;
    private String resolutionDetails;
    private Date resolutionDate;
    private Date closureDate;
    private Integer clientAcceptanceDocId;
    private WarrantiesTable warrantyId;

    //warranty
    private String warrantedItemDescription;
    private String manufacturerModel;
    private String serialNumber;
    private WarrantyType warrantyType;
    private Integer warrantyProviderPartyId;
    private Date startDate;
    private Date endDate;
    private String coverageScopeText;
    private String exclusionsText;
    private Boolean isActive;
    private Boolean isVoided;
    private String voidReason;
    private ContractPartiesTable warrantyProviderParty;

    //penalities
    private String breachType;
    private String penalitiesDescription;
    private Date incurredDate;
    private BigDecimal penaltyAmount;
    private String currency;
    private String relatedClauseRef;
    private Integer gracePeriodDaysApplied;
    private PenaltiesStatus penalitiesStatus;
    private Date penalitiesResolutionDate;
    private Integer resolvedByUserId;
    private String notes;
    private Integer proofDocumentId;

//rebate
    private RebateType rebateType;
    private CalculationBasis calculationBasis;
    private BigDecimal valueParam;
    private String rebateDescription;
    private String conditionText;
    private TrackingMetricType trackingMetricType;
    private BigDecimal targetValueMetric;
    private BigDecimal currentValueMetric;
    private Date rebateStartDate;
    private Date rebateEndDate;
    private BigDecimal estimatedRebateAmount;
    private BigDecimal actualRebateAmount;
    private RebateStatus rebateStatus;
    private Integer appliedToPaymentId;

//payment frequency
    private String frequencyName;

//    Retention
    private String retentionReason;
    private BigDecimal retainedPercentageApplied;
    private BigDecimal initialRetainedAmount;
    private String retentionCurrency;
    private BigDecimal amountReleasedSoFar;
    private RetentionStatus retentionStatus;
    private String releaseConditionDescription;
    private Date scheduledReleaseDate;
    private Date actualFirstReleaseDate;
    private String retentionNotes;

    public boolean isCreateNewType() {
        return createNewType;
    }

    public void setCreateNewType(boolean createNewType) {
        this.createNewType = createNewType;
    }
    public boolean isCreateNewStatus() {
        return createNewStatus;
    }

    public void setCreateNewStatus(boolean createNewStatus) {
        this.createNewStatus = createNewStatus;
    }

    public boolean isCreateNewPartyType() {
        return createNewPartyType;
    }

    public void setCreateNewPartyType(boolean createNewPartyType) {
        this.createNewPartyType = createNewPartyType;
    }

    public boolean isCreateNewParty() {
        return createNewParty;
    }

    public void setCreateNewParty(boolean createNewParty) {
        this.createNewParty = createNewParty;
    }

    public boolean isCreateNewPartyRoleType() {
        return createNewPartyRoleType;
    }

    public void setCreateNewPartyRoleType(boolean createNewPartyRoleType) {
        this.createNewPartyRoleType = createNewPartyRoleType;
    }
    public boolean isCreateNewMilestoneStatus() {
        return createNewMilestoneStatus;
    }

    public void setCreateNewMilestoneStatus(boolean createNewMilestoneStatus) {
        this.createNewMilestoneStatus = createNewMilestoneStatus;
    }

    public boolean isCreateNewWarrantyId() {
        return createNewWarrantyId;
    }

    public void setCreateNewWarrantyId(boolean createNewWarrantyId) {
        this.createNewWarrantyId = createNewWarrantyId;
    }

    private boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    public boolean assignFormToContractsTableSelected() {
        ContractsTable selectedContract = contractsTableController.getSelected();
        if (selectedContract == null) {
            selectedContract = new ContractsTable();
            contractsTableController.setSelected(selectedContract);
        }

        // === Contract Type (Create New or Use Existing)
        if (createNewType) {
            // Skip creating/assigning if all fields are empty
            if (isEmpty(typeName) && isEmpty(typeDescription) && isEmpty(contractSide)) {
                // Do nothing: don't create or assign
            } else {
                ContractTypesTable selectedContractType = contractTypesTableController.getSelected();
                if (selectedContractType == null) {
                    selectedContractType = new ContractTypesTable();
                    contractTypesTableController.setSelected(selectedContractType);
                }

                selectedContractType.setTypeName(typeName);
                selectedContractType.setDescription(typeDescription);
                selectedContractType.setContractSide(contractSide);

                if(isEmpty(typeName)){
                    JsfUtil.addErrorMessage("Type name is Required.");
                    return false;
                }
                contractTypesTableController.save(); // Save new type
                selectedContract.setContractTypeId(selectedContractType); // Assign new type
            }
        } else {
            selectedContract.setContractTypeId(contractTypeId); // Assign existing selected type
        }

        // === Contract Status (Create New or Use Existing)
        if (createNewStatus) {
            if (isEmpty(statusName) && isEmpty(statusDescription)) {
                // Do nothing: don't create or assign
            } else {
                ContractStatusesTable selectedContractStatus = contractStatusesTableController.getSelected();
                if (selectedContractStatus == null) {
                    selectedContractStatus = new ContractStatusesTable();
                    contractStatusesTableController.setSelected(selectedContractStatus);
                }

                selectedContractStatus.setStatusName(statusName);
                selectedContractStatus.setDescription(statusDescription);

                if(isEmpty(statusName)){
                    JsfUtil.addErrorMessage("Status name is Required.");
                    return false;
                }
            }
        } else {
            selectedContract.setContractStatusId(contractStatusId); // Assign existing selected status
        }

    // === Other fields
        selectedContract.setContractTitle(contractTitle);
        selectedContract.setReferenceNumber(referenceNumber);
        selectedContract.setDescriptionScope(descriptionScope);
        selectedContract.setEffectiveDate(effectiveDate);
        selectedContract.setInitialExpiryDate(initialExpiryDate);
        selectedContract.setCurrentExpiryDate(currentExpiryDate);
        selectedContract.setNoticePeriodDaysForTermination(noticePeriodDaysForTermination);
        selectedContract.setAutoRenewalEnabled(autoRenewalEnabled);
        selectedContract.setRenewalTermMonths(renewalTermMonths);
        selectedContract.setContractValue(contractValue);
        selectedContract.setCurrencyCode(currencyCode);
        selectedContract.setGoverningLawJurisdiction(governingLawJurisdiction);
        selectedContract.setParentContractId(parentContractId);
        selectedContract.setCreatedByUserId(createdByUserId);
        selectedContract.setCreatedAt(createdAt);
        selectedContract.setUpdatedByUserId(updatedByUserId);
        selectedContract.setUpdatedAt(updatedAt);
        selectedContract.setContractIdentitySide(contractIdentitySide);
        selectedContract.setInitalStartDate(initalStartDate);
        selectedContract.setEndStartDate(endStartDate);
        selectedContract.setCancellationFee(cancellationFee);
        selectedContract.setRetinageAmount(retinageAmount);
        selectedContract.setTaxRate(taxRate);
        selectedContract.setContractCurrency(contractCurrency);

        // === Address fields
        selectedContract.setAddressNumberCustomer1(addressNumberCustomer1);
        selectedContract.setAddressNumberCustomer2(addressNumberCustomer2);
        selectedContract.setAddressNumberCustomer3(addressNumberCustomer3);
        selectedContract.setAddressNumberRep1(addressNumberRep1);
        selectedContract.setAddressNumberRep2(addressNumberRep2);
        selectedContract.setAddressNumberRep3(addressNumberRep3);
        selectedContract.setAddressNumberRep4(addressNumberRep4);
        selectedContract.setAddressNumberPrepared(addressNumberPrepared);
        selectedContract.setAddressNumberAproved(addressNumberAproved);
        selectedContract.setAddressNumberChecked(addressNumberChecked);

        return true;
    }


    // Save action
    public String save() {
        if (!assignFormToContractsTableSelected()) {
            return null; // ⛔ Don't proceed with saving if validation failed
        }
        if(!(amountQuantity == 0 && itemsTable == 0 && unitOfMeasure == 0 && unitAmount == 0 && extendedAmount == 0 && dateEnered == null && dateUpdated == null)){
            if(assignFormToContractsItemTableSelected()){
                if (contractItemTableController.getSelected() == null || contractItemTableController.getSelected().getContractId() == null) {
                    JsfUtil.addErrorMessage("Validation Error: Contract is required.");
                    return null;
                }

            }else{
                return null;
            }
        }
        if(!(!isPrimaryCounterParty && partyId == null && isEmpty(legalName) && partyTypeId == null && isEmpty(typeName) && roleInContractId == null && isEmpty(roleName))){
            if(assignFormToContractsPartyRolesTableSelected()){
                if (contractPartyRolesTableController.getSelected().getContractId() == null) {
                    JsfUtil.addErrorMessage("Contract ID is required.");
                    return null;
                }
                if (contractPartyRolesTableController.getSelected().getPartyId() == null) {
                    JsfUtil.addErrorMessage("Party ID is required.");
                    return null;
                }
                if (contractPartyRolesTableController.getSelected().getRoleInContractId() == null) {
                    JsfUtil.addErrorMessage("Role In Contract ID is required.");
                    return null;
                }

            }else{
                return null;
            }

        }

        // Save Milestones
        if (!(isEmpty(milestoneName) && isEmpty(milestoneDescription) && dueDate == null &&
                assignedContactId == 0 && reminderLeadDays == 0 && completedDate == null &&
                isEmpty(usersId) && statusId == null && responsiblePartyId == null &&
                isEmpty(statusName))) {
            if (assignFormToMilestonesTableSelected()) {
                if (contractKeyDatesMilestonesTableController.getSelected() == null || contractKeyDatesMilestonesTableController.getSelected().getContractId() == null ) {
                    JsfUtil.addErrorMessage("Validation Error: Contract Should be Filled");
                    return null;
                }

            }else {
                return null;
            }
        }

        // Save Penalties
        if (!(isEmpty(breachType) && isEmpty(penalitiesDescription) && incurredDate == null &&
                penaltyAmount == null && isEmpty(currency) && isEmpty(relatedClauseRef) &&
                gracePeriodDaysApplied == 0 && penalitiesStatus == null &&
                penalitiesResolutionDate == null && resolvedByUserId == 0 &&
                isEmpty(notes) && proofDocumentId == 0)) {
            if (assignFormToPenaltiesTableSelected()) {
                penaltiesController.save();
            }
        }

        // Save Rebates/Discounts
        if (!(rebateType == null && calculationBasis == null && valueParam == null &&
                isEmpty(rebateDescription) && isEmpty(conditionText) &&
                trackingMetricType == null && targetValueMetric == null &&
                currentValueMetric == null && rebateStartDate == null && rebateEndDate == null &&
                estimatedRebateAmount == null && actualRebateAmount == null &&
                rebateStatus == null && appliedToPaymentId == 0)) {
            if (assignFormToRebatesDiscountsTableSelected()) {
                    BigDecimal max = new BigDecimal("9999999999999999.99");
                    BigDecimal min = BigDecimal.ZERO;

                    // Validate valueParam
                    if (valueParam == null || valueParam.compareTo(min) < 0 || valueParam.compareTo(max) > 0) {
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                        "Invalid value: Value Param must be between 0.00 and 9999999999999999.99", null));
                        return null;
                    }

                    // Validate actualRebateAmount
                    if (actualRebateAmount != null &&
                            (actualRebateAmount.compareTo(min) < 0 || actualRebateAmount.compareTo(max) > 0)) {
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                        "Invalid value: Actual Rebate Amount must be between 0.00 and 9999999999999999.99", null));
                        return null;
                    }

                    // Validate currentValueMetric
                    if (currentValueMetric != null &&
                            (currentValueMetric.compareTo(min) < 0 || currentValueMetric.compareTo(max) > 0)) {
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                        "Invalid value: Current Value Metric must be between 0.00 and 9999999999999999.99", null));
                        return null;
                    }

                    // Validate estimatedRebateAmount
                    if (estimatedRebateAmount != null &&
                            (estimatedRebateAmount.compareTo(min) < 0 || estimatedRebateAmount.compareTo(max) > 0)) {
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                        "Invalid value: Estimated Rebate Amount must be between 0.00 and 9999999999999999.99", null));
                        return null;
                    }

                    // Validate targetValueMetric
                    if (targetValueMetric != null &&
                            (targetValueMetric.compareTo(min) < 0 || targetValueMetric.compareTo(max) > 0)) {
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                        "Invalid value: Target Value Metric must be between 0.00 and 9999999999999999.99", null));
                        return null;
                    }

                    // Validate required fields
                    if (status == null) {
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                        "Status is required.", null));
                        return null;
                    }

                    if (rebateType == null) {
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                        "Rebate Type is required.", null));
                        return null;
                    }

                    if (calculationBasis == null) {
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                        "Calculation Basis is required.", null));
                        return null;
                    }

                    if (conditionText == null || conditionText.trim().isEmpty()) {
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                        "Condition Text is required.", null));
                        return null;
                    }

            }else {
                return null;
            }
        }

        // Save Retentions
        if (!(isEmpty(retentionReason) && retainedPercentageApplied == null && initialRetainedAmount == null &&
                isEmpty(retentionCurrency) && amountReleasedSoFar == null &&
                retentionStatus == null && isEmpty(releaseConditionDescription) &&
                scheduledReleaseDate == null && actualFirstReleaseDate == null &&
                isEmpty(retentionNotes))) {
            if (assignFormToRetentionsTableSelected()) {
                BigDecimal percentage = retentionsTableController.getSelected().getRetainedPercentageApplied();
                if (percentage != null && (percentage.compareTo(new BigDecimal("999.99")) > 0 || percentage.compareTo(BigDecimal.ZERO) < 0)) {
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    "Retained Percentage must be between 0.00 and 999.99", null));
                    return null;
                }

            }else {
                return null;
            }
        }

        // Save Issues
        if (!(isEmpty(issueCategory) && isEmpty(issueDescription) && reportedDate == null &&
                severity == null && status == null && isEmpty(impact) &&
                assignedToUserId == 0 && isEmpty(resolutionDetails) &&
                resolutionDate == null && closureDate == null && clientAcceptanceDocId == 0)) {
            if (assignFormToIssuesTableSelected()) {
                // issueDescription - required, min 1 char, max 65535 (handled by DB size but check null/empty)
                if (issueDescription == null || issueDescription.trim().isEmpty()) {
                    JsfUtil.addErrorMessage("Issue description cannot be empty.");
                    return null;
                }

                // reportedDate - must not be null and not future date
                if (reportedDate == null) {
                    JsfUtil.addErrorMessage("Reported date is required.");
                    return null;
                } else if (reportedDate.after(new Date())) {
                    JsfUtil.addErrorMessage("Reported date cannot be in the future.");
                    return null;
                }

                // Optionally validate optional fields length (impact, resolutionDetails) if present
                if (impact != null && impact.length() > 65535) {
                    JsfUtil.addErrorMessage("Impact text is too long.");
                    return null;
                }

                if (resolutionDetails != null && resolutionDetails.length() > 65535) {
                    JsfUtil.addErrorMessage("Resolution details text is too long.");
                    return null;
                }

            }else {
                return null;
            }
        }

//Save Payment Frequency
        if (!isEmpty(frequencyName)) {
            if (!assignFormToPaymentFrequenciesTableSelected()) {
                return null;
            }
        }

        if(assignFormToPaymentFrequenciesTableSelected() && assignFormToPenaltiesTableSelected() && assignFormToIssuesTableSelected() && assignFormToMilestonesTableSelected() && assignFormToContractsItemTableSelected() && assignFormToContractsPartyRolesTableSelected() && assignFormToRetentionsTableSelected() && assignFormToPaymentFrequenciesTableSelected() && assignFormToRebatesDiscountsTableSelected() && assignFormToPenaltiesTableSelected() && assignFormToIssuesTableSelected() && assignFormToMilestonesTableSelected()) {

            ContractsTable contract = contractsTableController.save();
            issuesDefectsTableController.getSelected().setContractId(contract);
            warrantiesTableController.save(); // Save new warranty
            issuesDefectsTableController.getSelected().setWarrantyId(warrantiesTableController.getSelectedWarranty());
            contractStatusesTableController.save(); // Save new status
            contractItemTableController.getSelected().setContractId(contract);
            contractItemTableController.save();
            partyTypesTableController.save(); // Save new status
            contractPartiesTableController.getSelected().setPartyTypeId(partyTypesTableController.getSelected());
            contractPartyRolesTableController.save();
            contractPartiesTableController.save(); // Save new status
            contractPartyRolesTableController.getSelected().setPartyId(contractPartiesTableController.getSelected());
            contractPartyRolesTableController.getSelected().setContractId(contract);
            partyRoleTypesTableController.save(); // Save new status
            contractPartyRolesTableController.getSelected().setRoleInContractId(partyRoleTypesTableController.getSelected());
            contractKeyDatesMilestonesTableController.getSelected().setContractId(contract);
            milestoneStatusesTableController.save(); // Save new milestone type
            contractKeyDatesMilestonesTableController.getSelected().setStatusId(milestoneStatusesTableController.getSelected()); // Assign new type
            contractKeyDatesMilestonesTableController.save();
            rebatesDiscountsTableController.save();
            retentionsTableController.save();
            issuesDefectsTableController.save();
            paymentFrequenciesTableController.save();
        }
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Contract saved", null));
        return null;
    }

    public boolean assignFormToContractsItemTableSelected() {
        ContractItemTable selectedContractItem = contractItemTableController.getSelected();
        if (selectedContractItem == null) {
            selectedContractItem = new ContractItemTable();
            contractItemTableController.setSelected(selectedContractItem);
        }

        selectedContractItem.setAmountQuantity(amountQuantity);
        selectedContractItem.setItemsTable(itemsTable);
        selectedContractItem.setUnitOfMeasure(unitOfMeasure);
        selectedContractItem.setUnitAmount(unitAmount);
        selectedContractItem.setExtendedAmount(extendedAmount);
        selectedContractItem.setDateEnered(dateEnered);
        selectedContractItem.setDateUpdated(dateUpdated);

        return true;
    }

    public boolean assignFormToContractsPartyRolesTableSelected() {
        ContractPartyRolesTable selectedContractPartyRoles = contractPartyRolesTableController.getSelected();
        if (selectedContractPartyRoles == null) {
            selectedContractPartyRoles = new ContractPartyRolesTable();
            contractPartyRolesTableController.setSelected(selectedContractPartyRoles);
        }

        // === Handle Contract Party (Create or Select)
        if (createNewParty) {
            if (isEmpty(legalName) && isEmpty(shortName) && isEmpty(registrationNumber) && addressNumberAb == 0 && partyTypeId == null && isEmpty(typeName) ) {
                // Do nothing: don't create or assign
            } else {
                ContractPartiesTable selectedContractParty = contractPartiesTableController.getSelected();
                if (selectedContractParty == null) {
                    selectedContractParty = new ContractPartiesTable();
                    contractPartiesTableController.setSelected(selectedContractParty);
                }


                // === Handle Contract Party (Create or Select)
                if (createNewPartyType) {
                    if (isEmpty(typeName) ) {
                        // Do nothing: don't create or assign
                    } else {
                        PartyTypesTable selectedPartyType = partyTypesTableController.getSelected();
                        if (selectedPartyType == null) {
                            selectedPartyType = new PartyTypesTable();
                            partyTypesTableController.setSelected(selectedPartyType);
                        }

                        selectedPartyType.setTypeName(typeName);

                    }
                } else {
                    selectedContractParty.setPartyTypeId(partyTypeId); // Assign existing selected status
                }

                selectedContractParty.setLegalName(legalName);
                selectedContractParty.setShortName(shortName);
                selectedContractParty.setRegistrationNumber(registrationNumber);
                selectedContractParty.setAddressNumberAb(addressNumberAb);


                if(isEmpty(legalName)){
                    JsfUtil.addErrorMessage("Legal name is Required.");
                    return false;
                }
            }
        } else {
            selectedContractPartyRoles.setPartyId(partyId); // Assign existing selected status
        }

        if(createNewPartyRoleType){
            if (isEmpty(roleName) ) {
                // Do nothing: don't create or assign
            } else {
                PartyRoleTypesTable selectedPartyRoleType = partyRoleTypesTableController.getSelected();
                if (selectedPartyRoleType == null) {
                    selectedPartyRoleType = new PartyRoleTypesTable();
                    partyRoleTypesTableController.setSelected(selectedPartyRoleType);
                }

                selectedPartyRoleType.setRoleName(roleName);

            }
        } else {
            selectedContractPartyRoles.setRoleInContractId(roleInContractId);
        }

        selectedContractPartyRoles.setIsPrimaryCounterparty(isPrimaryCounterParty);

        return true;
    }

    public boolean assignFormToMilestonesTableSelected() {
        ContractKeyDatesMilestonesTable selectedMilestone = contractKeyDatesMilestonesTableController.getSelected();
        if (selectedMilestone == null) {
            selectedMilestone = new ContractKeyDatesMilestonesTable();
            contractKeyDatesMilestonesTableController.setSelected(selectedMilestone);
        }

        // === Milestone Type (Create New or Use Existing)
        if (createNewMilestoneStatus) {
            if (isEmpty(milestoneName) ){
                // Do nothing: don't create or assign
            } else {
                MilestoneStatusesTable selectedMilestoneType = milestoneStatusesTableController.getSelected();
                if (selectedMilestoneType == null) {
                    selectedMilestoneType = new MilestoneStatusesTable();
                    milestoneStatusesTableController.setSelected(selectedMilestoneType);
                }

                selectedMilestoneType.setStatusName(statusName);
            }
        } else {
            selectedMilestone.setStatusId(statusId); // Assign existing selected type
        }

        // === Set other milestone details
        selectedMilestone.setMilestoneName(milestoneName);
        selectedMilestone.setDescription(milestoneDescription);
        selectedMilestone.setDueDate(dueDate);
        selectedMilestone.setAssignedContactId(assignedContactId);
        selectedMilestone.setReminderLeadDays(reminderLeadDays);
        selectedMilestone.setCompletedDate(completedDate);

        return true;
    }

    public boolean assignFormToIssuesTableSelected() {
        IssuesDefectsTable selectedIssue = issuesDefectsTableController.getSelected();
        if (selectedIssue == null) {
            selectedIssue = new IssuesDefectsTable();
            issuesDefectsTableController.setSelected(selectedIssue);
        }

        // === Warranty Handling (Create New or Use Existing)
        if (createNewWarrantyId) {
            if (isEmpty(warrantedItemDescription) && isEmpty(manufacturerModel) && isEmpty(serialNumber) && warrantyType == null &&
                    warrantyProviderPartyId == 0 && startDate == null && endDate == null && isEmpty(coverageScopeText) && isEmpty(exclusionsText) &&
                    !isActive && !isVoided && isEmpty(voidReason)) {
                // Skip creation
            } else {
                WarrantiesTable selectedWarranty = warrantiesTableController.getSelected();
                if (selectedWarranty == null) {
                    selectedWarranty = new WarrantiesTable();
                    warrantiesTableController.setSelected(selectedWarranty);
                }

                selectedWarranty.setWarrantedItemDescription(warrantedItemDescription);
                selectedWarranty.setManufacturerModel(manufacturerModel);
                selectedWarranty.setSerialNumber(serialNumber);
                selectedWarranty.setWarrantyType(warrantyType);
                selectedWarranty.setWarrantyProviderPartyId(warrantyProviderPartyId);
                selectedWarranty.setStartDate(startDate);
                selectedWarranty.setEndDate(endDate);
                selectedWarranty.setCoverageScopeText(coverageScopeText);
                selectedWarranty.setExclusionsText(exclusionsText);
                selectedWarranty.setIsActive(isActive);
                selectedWarranty.setIsVoided(isVoided);
                selectedWarranty.setVoidReason(voidReason);

                if(endDate == null && startDate == null){
                    JsfUtil.addErrorMessage("Warranties Start and End Date is Required.");
                    return false;
                }
            }
        } else {
            selectedIssue.setWarrantyId(warrantyId); // Assign existing
        }

        if(isEmpty(issueCategory)){
            JsfUtil.addErrorMessage("Issue Category is Required.");
            return false;
        }

        // === Set issue details
        selectedIssue.setIssueCategory(issueCategory);
        selectedIssue.setIssueDescription(issueDescription);
        selectedIssue.setReportedDate(reportedDate);
        selectedIssue.setSeverity(severity);
        selectedIssue.setStatus(status);
        selectedIssue.setImpact(impact);
        selectedIssue.setAssignedToUserId(assignedToUserId);
        selectedIssue.setResolutionDetails(resolutionDetails);
        selectedIssue.setResolutionDate(resolutionDate);
        selectedIssue.setClosureDate(closureDate);
        selectedIssue.setClientAcceptanceDocId(clientAcceptanceDocId);

        return true;
    }

    public boolean assignFormToPenaltiesTableSelected() {
        Penalties selectedPenalty = penaltiesController.getSelected();
        if (selectedPenalty == null) {
            selectedPenalty = new Penalties();
            penaltiesController.setSelected(selectedPenalty);
        }

        // === Set penalty fields
        selectedPenalty.setBreachType(breachType);
        selectedPenalty.setDescription(penalitiesDescription);
        selectedPenalty.setIncurredDate(incurredDate);
        selectedPenalty.setPenaltyAmount(penaltyAmount);
        selectedPenalty.setCurrency(currency);
        selectedPenalty.setRelatedClauseRef(relatedClauseRef);
        selectedPenalty.setGracePeriodDaysApplied(gracePeriodDaysApplied);
        selectedPenalty.setStatus(penalitiesStatus);
        selectedPenalty.setResolutionDate(penalitiesResolutionDate);
        selectedPenalty.setResolvedByUserId(resolvedByUserId);
        selectedPenalty.setNotes(notes);
        selectedPenalty.setProofDocumentId(proofDocumentId);

        return true;
    }

    public boolean assignFormToRebatesDiscountsTableSelected() {
        RebatesDiscountsTable selectedRebate = rebatesDiscountsTableController.getSelected();
        if (selectedRebate == null) {
            selectedRebate = new RebatesDiscountsTable();
            rebatesDiscountsTableController.setSelected(selectedRebate);
        }

        selectedRebate.setRebateType(rebateType);
        selectedRebate.setCalculationBasis(calculationBasis);
        selectedRebate.setValueParam(valueParam);
        selectedRebate.setDescription(rebateDescription);
        selectedRebate.setConditionText(conditionText);
        selectedRebate.setTrackingMetricType(trackingMetricType);
        selectedRebate.setTargetValueMetric(targetValueMetric);
        selectedRebate.setCurrentValueMetric(currentValueMetric);
        selectedRebate.setStartDate(rebateStartDate);
        selectedRebate.setEndDate(rebateEndDate);
        selectedRebate.setEstimatedRebateAmount(estimatedRebateAmount);
        selectedRebate.setActualRebateAmount(actualRebateAmount);
        selectedRebate.setStatus(rebateStatus);
        selectedRebate.setAppliedToPaymentId(appliedToPaymentId);

        return true;
    }

    public boolean assignFormToPaymentFrequenciesTableSelected() {
        PaymentFrequenciesTable selectedFrequency = paymentFrequenciesTableController.getSelected();
        if (selectedFrequency == null) {
            selectedFrequency = new PaymentFrequenciesTable();
            paymentFrequenciesTableController.setSelected(selectedFrequency);
        }

        selectedFrequency.setFrequencyName(selectedFrequency.getFrequencyName());

        return true;
    }

    public boolean assignFormToRetentionsTableSelected() {
        RetentionsTable selectedRetention = retentionsTableController.getSelected();
        if (selectedRetention == null) {
            selectedRetention = new RetentionsTable();
            retentionsTableController.setSelected(selectedRetention);
        }

        selectedRetention.setRetentionReason(retentionReason);
        selectedRetention.setRetainedPercentageApplied(retainedPercentageApplied);
        selectedRetention.setInitialRetainedAmount(initialRetainedAmount);
        selectedRetention.setCurrency(retentionCurrency);
        selectedRetention.setAmountReleasedSoFar(amountReleasedSoFar);
        selectedRetention.setStatus(retentionStatus);
        selectedRetention.setReleaseConditionDescription(releaseConditionDescription);
        selectedRetention.setScheduledReleaseDate(scheduledReleaseDate);
        selectedRetention.setActualFirstReleaseDate(actualFirstReleaseDate);
        selectedRetention.setNotes(retentionNotes);

        return true;
    }

    // Cancel create
    public void cancelCreate() {
        resetForms();
    }

    // Discard
    public void discard() {
        resetForms();
    }

    // Reset forms
    public void resetForms() {
        contractTitle = null;
        referenceNumber = null;
        contractTypeId = null;
        contractStatusId = null;
        descriptionScope = null;
        effectiveDate = null;
        initialExpiryDate = null;
        currentExpiryDate = null;
        noticePeriodDaysForTermination = null;
        autoRenewalEnabled = false;
        renewalTermMonths = null;
        contractValue = null;
        currencyCode = null;
        parentContractId = null;
        createdByUserId = null;
        createdAt = null;
        updatedByUserId = null;
        updatedAt = null;
        governingLawJurisdiction = null;
        contractIdentitySide = null;
        initalStartDate = null;
        endStartDate = null;
        cancellationFee = null;
        retinageAmount = null;
        taxRate = null;
        contractCurrency = null;

        addressNumberCustomer1 = null;
        addressNumberCustomer2 = null;
        addressNumberCustomer3 = null;
        addressNumberRep1 = null;
        addressNumberRep2 = null;
        addressNumberRep3 = null;
        addressNumberRep4 = null;
        addressNumberPrepared = null;
        addressNumberAproved = null;
        addressNumberChecked = null;

        legalName = null;
        shortName = null;
        partyTypeId = null;
        registrationNumber = null;
        addressNumberAb = null;

        partyFormDisabled = false;
        typeFormDisabled = false;
    }

    //getters and setters

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }
    public String getTypeDescription() {
        return typeDescription;
    }

    public void setTypeDescription(String typeDescription) {
        this.typeDescription = typeDescription;
    }

    public ContractStatusesTable getContractStatusId() {
        return contractStatusId;
    }

    public void setContractStatusId(ContractStatusesTable contractStatusId) {
        this.contractStatusId = contractStatusId;
    }

    public String getDescriptionScope() {
        return descriptionScope;
    }

    public void setDescriptionScope(String descriptionScope) {
        this.descriptionScope = descriptionScope;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Date getInitialExpiryDate() {
        return initialExpiryDate;
    }

    public void setInitialExpiryDate(Date initialExpiryDate) {
        this.initialExpiryDate = initialExpiryDate;
    }

    public Date getCurrentExpiryDate() {
        return currentExpiryDate;
    }

    public void setCurrentExpiryDate(Date currentExpiryDate) {
        this.currentExpiryDate = currentExpiryDate;
    }

    public Integer getNoticePeriodDaysForTermination() {
        return noticePeriodDaysForTermination;
    }

    public void setNoticePeriodDaysForTermination(Integer noticePeriodDaysForTermination) {
        this.noticePeriodDaysForTermination = noticePeriodDaysForTermination;
    }

    public Boolean getAutoRenewalEnabled() {
        return autoRenewalEnabled;
    }

    public void setAutoRenewalEnabled(Boolean autoRenewalEnabled) {
        this.autoRenewalEnabled = autoRenewalEnabled;
    }

    public Integer getRenewalTermMonths() {
        return renewalTermMonths;
    }

    public void setRenewalTermMonths(Integer renewalTermMonths) {
        this.renewalTermMonths = renewalTermMonths;
    }

    public Double getContractValue() {
        return contractValue;
    }

    public void setContractValue(Double contractValue) {
        this.contractValue = contractValue;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Integer getParentContractId() {
        return parentContractId;
    }

    public void setParentContractId(Integer parentContractId) {
        this.parentContractId = parentContractId;
    }

    public String getCreatedByUserId() {
        return createdByUserId;
    }

    public void setCreatedByUserId(String createdByUserId) {
        this.createdByUserId = createdByUserId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedByUserId() {
        return updatedByUserId;
    }

    public void setUpdatedByUserId(String updatedByUserId) {
        this.updatedByUserId = updatedByUserId;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getGoverningLawJurisdiction() {
        return governingLawJurisdiction;
    }

    public void setGoverningLawJurisdiction(String governingLawJurisdiction) {
        this.governingLawJurisdiction = governingLawJurisdiction;
    }

    public String getContractIdentitySide() {
        return contractIdentitySide;
    }

    public void setContractIdentitySide(String contractIdentitySide) {
        this.contractIdentitySide = contractIdentitySide;
    }

    public Date getInitalStartDate() {
        return initalStartDate;
    }

    public void setInitalStartDate(Date initalStartDate) {
        this.initalStartDate = initalStartDate;
    }

    public Date getEndStartDate() {
        return endStartDate;
    }

    public void setEndStartDate(Date endStartDate) {
        this.endStartDate = endStartDate;
    }

    public Double getCancellationFee() {
        return cancellationFee;
    }

    public void setCancellationFee(Double cancellationFee) {
        this.cancellationFee = cancellationFee;
    }

    public Double getRetinageAmount() {
        return retinageAmount;
    }

    public void setRetinageAmount(Double retinageAmount) {
        this.retinageAmount = retinageAmount;
    }

    public Integer getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(Integer taxRate) {
        this.taxRate = taxRate;
    }

    public Integer getContractCurrency() {
        return contractCurrency;
    }

    public void setContractCurrency(Integer contractCurrency) {
        this.contractCurrency = contractCurrency;
    }

    public Integer getAddressNumberCustomer1() {
        return addressNumberCustomer1;
    }

    public void setAddressNumberCustomer1(Integer addressNumberCustomer1) {
        this.addressNumberCustomer1 = addressNumberCustomer1;
    }

    public Integer getAddressNumberCustomer2() {
        return addressNumberCustomer2;
    }

    public void setAddressNumberCustomer2(Integer addressNumberCustomer2) {
        this.addressNumberCustomer2 = addressNumberCustomer2;
    }

    public Integer getAddressNumberCustomer3() {
        return addressNumberCustomer3;
    }

    public void setAddressNumberCustomer3(Integer addressNumberCustomer3) {
        this.addressNumberCustomer3 = addressNumberCustomer3;
    }

    public Integer getAddressNumberRep1() {
        return addressNumberRep1;
    }

    public void setAddressNumberRep1(Integer addressNumberRep1) {
        this.addressNumberRep1 = addressNumberRep1;
    }

    public Integer getAddressNumberRep2() {
        return addressNumberRep2;
    }

    public void setAddressNumberRep2(Integer addressNumberRep2) {
        this.addressNumberRep2 = addressNumberRep2;
    }

    public Integer getAddressNumberRep3() {
        return addressNumberRep3;
    }

    public void setAddressNumberRep3(Integer addressNumberRep3) {
        this.addressNumberRep3 = addressNumberRep3;
    }

    public Integer getAddressNumberRep4() {
        return addressNumberRep4;
    }

    public void setAddressNumberRep4(Integer addressNumberRep4) {
        this.addressNumberRep4 = addressNumberRep4;
    }

    public Integer getAddressNumberPrepared() {
        return addressNumberPrepared;
    }

    public void setAddressNumberPrepared(Integer addressNumberPrepared) {
        this.addressNumberPrepared = addressNumberPrepared;
    }

    public Integer getAddressNumberAproved() {
        return addressNumberAproved;
    }

    public void setAddressNumberAproved(Integer addressNumberAproved) {
        this.addressNumberAproved = addressNumberAproved;
    }

    public Integer getAddressNumberChecked() {
        return addressNumberChecked;
    }

    public void setAddressNumberChecked(Integer addressNumberChecked) {
        this.addressNumberChecked = addressNumberChecked;
    }


    public Double getAmountQuantity() {
        return amountQuantity;
    }

    public void setAmountQuantity(Double amountQuantity) {
        this.amountQuantity = amountQuantity;
    }

    public Integer getItemsTable() {
        return itemsTable;
    }

    public void setItemsTable(Integer itemsTable) {
        this.itemsTable = itemsTable;
    }

    public Integer getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(Integer unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public Double getUnitAmount() {
        return unitAmount;
    }

    public void setUnitAmount(Double unitAmount) {
        this.unitAmount = unitAmount;
    }

    public Double getExtendedAmount() {
        return extendedAmount;
    }

    public void setExtendedAmount(Double extendedAmount) {
        this.extendedAmount = extendedAmount;
    }

    public String getUsersId() {
        return usersId;
    }

    public void setUsersId(String usersId) {
        this.usersId = usersId;
    }

    public Date getDateEnered() {
        return dateEnered;
    }

    public void setDateEnered(Date dateEnered) {
        this.dateEnered = dateEnered;
    }

    public Date getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }



    public String getLegalName() {
        return legalName;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public PartyTypesTable getPartyTypeId() {
        return partyTypeId;
    }

    public void setPartyTypeId(PartyTypesTable partyTypeId) {
        this.partyTypeId = partyTypeId;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public Integer getAddressNumberAb() {
        return addressNumberAb;
    }

    public void setAddressNumberAb(Integer addressNumberAb) {
        this.addressNumberAb = addressNumberAb;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getDescription() {
        return typeDescription;
    }

    public void setDescription(String description) {
        this.typeDescription = description;
    }

    public String getContractSide() {
        return contractSide;
    }

    public void setContractSide(String contractSide) {
        this.contractSide = contractSide;
    }

    public String getContractTitle() {
        return contractTitle;
    }

    public void setContractTitle(String contractTitle) {
        this.contractTitle = contractTitle;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public ContractTypesTable getContractTypeId() {
        return contractTypeId;
    }

    public void setContractTypeId(ContractTypesTable contractTypeId) {
        this.contractTypeId = contractTypeId;
    }

    public Boolean getPrimaryCounterParty() {
        return isPrimaryCounterParty;
    }

    public void setPrimaryCounterParty(Boolean primaryCounterParty) {
        isPrimaryCounterParty = primaryCounterParty;
    }

    public ContractPartiesTable getPartyId() {
        return partyId;
    }

    public void setPartyId(ContractPartiesTable partyId) {
        this.partyId = partyId;
    }

    public PartyRoleTypesTable getRoleInContractId() {
        return roleInContractId;
    }

    public void setRoleInContractId(PartyRoleTypesTable roleInContractId) {
        this.roleInContractId = roleInContractId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getMilestoneName() {
        return milestoneName;
    }

    public void setMilestoneName(String milestoneName) {
        this.milestoneName = milestoneName;
    }

    public String getMilestoneDescription() {
        return milestoneDescription;
    }

    public void setMilestoneDescription(String milestoneDescription) {
        this.milestoneDescription = milestoneDescription;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Integer getAssignedContactId() {
        return assignedContactId;
    }

    public void setAssignedContactId(Integer assignedContactId) {
        this.assignedContactId = assignedContactId;
    }

    public Integer getReminderLeadDays() {
        return reminderLeadDays;
    }

    public void setReminderLeadDays(Integer reminderLeadDays) {
        this.reminderLeadDays = reminderLeadDays;
    }

    public Date getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(Date completedDate) {
        this.completedDate = completedDate;
    }

    public String getMilestoneStatusName() {
        return milestoneStatusName;
    }

    public void setMilestoneStatusName(String milestoneStatusName) {
        this.milestoneStatusName = milestoneStatusName;
    }

    public MilestoneStatusesTable getStatusId() {
        return statusId;
    }

    public void setStatusId(MilestoneStatusesTable statusId) {
        this.statusId = statusId;
    }

    public ContractPartyRolesTable getResponsiblePartyId() {
        return responsiblePartyId;
    }

    public void setResponsiblePartyId(ContractPartyRolesTable responsiblePartyId) {
        this.responsiblePartyId = responsiblePartyId;
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

    public Severity getSeverity() {
        return severity;
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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

    public WarrantiesTable getWarrantyId() {
        return warrantyId;
    }

    public void setWarrantyId(WarrantiesTable warrantyId) {
        this.warrantyId = warrantyId;
    }

    public String getWarrantedItemDescription() {
        return warrantedItemDescription;
    }

    public void setWarrantedItemDescription(String warrantedItemDescription) {
        this.warrantedItemDescription = warrantedItemDescription;
    }

    public String getManufacturerModel() {
        return manufacturerModel;
    }

    public void setManufacturerModel(String manufacturerModel) {
        this.manufacturerModel = manufacturerModel;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public WarrantyType getWarrantyType() {
        return warrantyType;
    }

    public void setWarrantyType(WarrantyType warrantyType) {
        this.warrantyType = warrantyType;
    }

    public Integer getWarrantyProviderPartyId() {
        return warrantyProviderPartyId;
    }

    public void setWarrantyProviderPartyId(Integer warrantyProviderPartyId) {
        this.warrantyProviderPartyId = warrantyProviderPartyId;
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

    public String getCoverageScopeText() {
        return coverageScopeText;
    }

    public void setCoverageScopeText(String coverageScopeText) {
        this.coverageScopeText = coverageScopeText;
    }

    public String getExclusionsText() {
        return exclusionsText;
    }

    public void setExclusionsText(String exclusionsText) {
        this.exclusionsText = exclusionsText;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Boolean getVoided() {
        return isVoided;
    }

    public void setVoided(Boolean voided) {
        isVoided = voided;
    }

    public String getVoidReason() {
        return voidReason;
    }

    public void setVoidReason(String voidReason) {
        this.voidReason = voidReason;
    }

    public String getBreachType() {
        return breachType;
    }

    public void setBreachType(String breachType) {
        this.breachType = breachType;
    }

    public Date getIncurredDate() {
        return incurredDate;
    }

    public void setIncurredDate(Date incurredDate) {
        this.incurredDate = incurredDate;
    }

    public BigDecimal getPenaltyAmount() {
        return penaltyAmount;
    }

    public void setPenaltyAmount(BigDecimal penaltyAmount) {
        this.penaltyAmount = penaltyAmount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getRelatedClauseRef() {
        return relatedClauseRef;
    }

    public void setRelatedClauseRef(String relatedClauseRef) {
        this.relatedClauseRef = relatedClauseRef;
    }

    public Integer getGracePeriodDaysApplied() {
        return gracePeriodDaysApplied;
    }

    public void setGracePeriodDaysApplied(Integer gracePeriodDaysApplied) {
        this.gracePeriodDaysApplied = gracePeriodDaysApplied;
    }

    public PenaltiesStatus getPenalitiesStatus() {
        return penalitiesStatus;
    }

    public String getPenalitiesDescription() {
        return penalitiesDescription;
    }

    public void setPenalitiesDescription(String penalitiesDescription) {
        this.penalitiesDescription = penalitiesDescription;
    }

    public void setPenalitiesStatus(PenaltiesStatus penalitiesStatus) {
        this.penalitiesStatus = penalitiesStatus;
    }

    public Date getPenalitiesResolutionDate() {
        return penalitiesResolutionDate;
    }

    public void setPenalitiesResolutionDate(Date penalitiesResolutionDate) {
        this.penalitiesResolutionDate = penalitiesResolutionDate;
    }

    public Integer getResolvedByUserId() {
        return resolvedByUserId;
    }

    public void setResolvedByUserId(Integer resolvedByUserId) {
        this.resolvedByUserId = resolvedByUserId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getProofDocumentId() {
        return proofDocumentId;
    }

    public void setProofDocumentId(Integer proofDocumentId) {
        this.proofDocumentId = proofDocumentId;
    }

    public ContractPartiesTable getWarrantyProviderParty() {
        return warrantyProviderParty;
    }

    public void setWarrantyProviderParty(ContractPartiesTable warrantyProviderParty) {
        this.warrantyProviderParty = warrantyProviderParty;
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

    public Date getRebateStartDate() {
        return rebateStartDate;
    }

    public void setRebateStartDate(Date rebateStartDate) {
        this.rebateStartDate = rebateStartDate;
    }

    public Date getRebateEndDate() {
        return rebateEndDate;
    }

    public void setRebateEndDate(Date rebateEndDate) {
        this.rebateEndDate = rebateEndDate;
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

    public RebateStatus getRebateStatus() {
        return rebateStatus;
    }

    public void setRebateStatus(RebateStatus rebateStatus) {
        this.rebateStatus = rebateStatus;
    }
    public String getRebateDescription() {
        return rebateDescription;
    }

    public void setRebateDescription(String rebateDescription) {
        rebateDescription = rebateDescription;
    }

    public Integer getAppliedToPaymentId() {
        return appliedToPaymentId;
    }

    public void setAppliedToPaymentId(Integer appliedToPaymentId) {
        this.appliedToPaymentId = appliedToPaymentId;
    }

    public String getFrequencyName() {
        return frequencyName;
    }

    public void setFrequencyName(String frequencyName) {
        this.frequencyName = frequencyName;
    }

    public String getRetentionReason() {
        return retentionReason;
    }

    public void setRetentionReason(String retentionReason) {
        this.retentionReason = retentionReason;
    }

    public BigDecimal getRetainedPercentageApplied() {
        return retainedPercentageApplied;
    }

    public void setRetainedPercentageApplied(BigDecimal retainedPercentageApplied) {
        this.retainedPercentageApplied = retainedPercentageApplied;
    }

    public BigDecimal getInitialRetainedAmount() {
        return initialRetainedAmount;
    }

    public void setInitialRetainedAmount(BigDecimal initialRetainedAmount) {
        this.initialRetainedAmount = initialRetainedAmount;
    }

    public String getRetentionCurrency() {
        return retentionCurrency;
    }

    public void setRetentionCurrency(String retentionCurrency) {
        this.retentionCurrency = retentionCurrency;
    }

    public BigDecimal getAmountReleasedSoFar() {
        return amountReleasedSoFar;
    }

    public void setAmountReleasedSoFar(BigDecimal amountReleasedSoFar) {
        this.amountReleasedSoFar = amountReleasedSoFar;
    }

    public RetentionStatus getRetentionStatus() {
        return retentionStatus;
    }

    public void setRetentionStatus(RetentionStatus retentionStatus) {
        this.retentionStatus = retentionStatus;
    }

    public String getReleaseConditionDescription() {
        return releaseConditionDescription;
    }

    public void setReleaseConditionDescription(String releaseConditionDescription) {
        this.releaseConditionDescription = releaseConditionDescription;
    }

    public Date getScheduledReleaseDate() {
        return scheduledReleaseDate;
    }

    public void setScheduledReleaseDate(Date scheduledReleaseDate) {
        this.scheduledReleaseDate = scheduledReleaseDate;
    }

    public Date getActualFirstReleaseDate() {
        return actualFirstReleaseDate;
    }

    public void setActualFirstReleaseDate(Date actualFirstReleaseDate) {
        this.actualFirstReleaseDate = actualFirstReleaseDate;
    }

    public String getRetentionNotes() {
        return retentionNotes;
    }

    public void setRetentionNotes(String retentionNotes) {
        this.retentionNotes = retentionNotes;
    }

    public boolean isPartyFormDisabled() {
        return partyFormDisabled;
    }

    public void setPartyFormDisabled(boolean partyFormDisabled) {
        this.partyFormDisabled = partyFormDisabled;
    }

    public boolean isTypeFormDisabled() {
        return typeFormDisabled;
    }

    public void setTypeFormDisabled(boolean typeFormDisabled) {
        this.typeFormDisabled = typeFormDisabled;
    }
}
