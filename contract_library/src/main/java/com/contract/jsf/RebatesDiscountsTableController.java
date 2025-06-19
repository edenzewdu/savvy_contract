package com.contract.jsf;

import com.contract.entity.ContractPartiesTable;
import com.contract.entity.RebatesDiscountsTable;
import com.contract.enums.*;
import com.contract.jsf.util.JsfUtil;
import com.contract.jsf.util.JsfUtil.PersistAction;
import com.contract.session.RebatesDiscountsTableFacade;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.faces.application.FacesMessage;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.component.UIComponent;
import java.util.ArrayList;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@Named("rebatesDiscountsTableController")
@SessionScoped
public class RebatesDiscountsTableController implements Serializable {

    @EJB
    private com.contract.session.RebatesDiscountsTableFacade ejbFacade;
    @EJB
    private com.contract.session.AbstractFacadeQuerySavvy ejbFacade1;
    private List<RebatesDiscountsTable> items = null;
    private List<RebatesDiscountsTable> multiselectionItems = null;
    private List<RebatesDiscountsTable> createItems = null;
    private List<RebatesDiscountsTable> editItems = null;
    private List<RebatesDiscountsTable> filteredValues = null;
    private RebatesDiscountsTable selected = new RebatesDiscountsTable();
    private RebatesDiscountsTable selected1;
    private RebatesDiscountsTable selected2 = new RebatesDiscountsTable();
    private String dataName = "RebatesDiscountsTable";
    protected int first;

    public RebatesDiscountsTableController() {
    }

    public int getFirst() {
        return first;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public String getDataName() {
        return this.dataName;
    }

    public void setDataName(final String dataName) {
        this.dataName = dataName;
    }

    public RebateStatus[] getRebateStatus() {
        return RebateStatus.values();
    }
    public RebateType[] getRebateType() {
        return RebateType.values();
    }
    public CalculationBasis[] getCalculationBasis() {
        return CalculationBasis.values();
    }
    public TrackingMetricType[] getTrackingMetricType() {
        return TrackingMetricType.values();
    }
    public RebatesDiscountsTable getSelected() {

        if (selected == null) {
            selected = new RebatesDiscountsTable();
        }
        return selected;
    }

    public void setSelected(RebatesDiscountsTable selected) {
        this.selected = selected;
    }

    public RebatesDiscountsTable getSelected1() {
        return selected1;
    }

    public void setSelected1(RebatesDiscountsTable selected1) {
        this.selected1 = selected1;
    }

    public RebatesDiscountsTable getSelected2() {
        return selected2;
    }

    public void setSelected2(RebatesDiscountsTable selected2) {
        this.selected2 = selected2;
    }

    public void cancelUpdate() {
        selected1 = null;

        editItems = null;
    }

    public void discard() {
        selected = null;
        for (RebatesDiscountsTable item : getCreateItems()) {
            if (item.getId() != null) {
                getFacade().remove(item);
            }

        }
        createItems = null;
        items = null;
        if (!JsfUtil.isValidationFailed()) {
            // Invalidate list of items to trigger re-query.
            JsfUtil.addSuccessMessage("All records are removed");
        }
    }

    public void cancelCreate() {
        selected = null;
        createItems = null;
        items = null;
    }

    public void refreshList() {
        items = null;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private RebatesDiscountsTableFacade getFacade() {
        return ejbFacade;
    }

    public RebatesDiscountsTable prepareCreate() {
        createItems = new ArrayList<>();

        int tempid = 1;

        selected = new RebatesDiscountsTable();
        selected.setTempId(tempid);

        createItems.add(selected);
        initializeEmbeddableKey();
        return selected;
    }

    public RebatesDiscountsTable prepareCopy() {

        if (createItems == null) {
            createItems = new ArrayList<>();
        }
        selected = multiselectionItems.get(0);
        selected.setId(null);
        createItems.add(selected);
        initializeEmbeddableKey();
        return selected;
    }

    public RebatesDiscountsTable prepareCreateInCreate() {

        selected1 = new RebatesDiscountsTable();
        createItems = preparingTempId(selected1, createItems);
        initializeEmbeddableKey();
        return selected1;
    }

    public RebatesDiscountsTable prepareCreate1() {
        selected = new RebatesDiscountsTable();
        int tempId = 0;
        for (RebatesDiscountsTable p : createItems) {
            if (p.getTempId() > tempId) {
                tempId = p.getTempId();
            }
        }
        selected.setTempId(tempId + 1);
        createItems.add(selected);
        return selected;
    }

    public RebatesDiscountsTable prepareCreateInEdit() {

        selected1 = new RebatesDiscountsTable();
        editItems = preparingTempId(selected1, editItems);
        initializeEmbeddableKey();
        return selected1;
    }

    public void prepareEdit() {
        editItems = new ArrayList<>();
        selected = multiselectionItems.get(0);
        editItems.add(selected);
    }

    public String saveAndClose(String linkName) {
        cancelUpdate();
        cancelCreate();
        linkName += ".xhtml?faces-redirect=true";
        return linkName;
    }

    public String saveAndAddNew(String linkName) {
        createItems = new ArrayList<>();
        createItems.add(new RebatesDiscountsTable());
        linkName += ".xhtml?faces-redirect=true";
        return linkName;
    }

    public String saveAndAddContinue(String linkName) {
        createItems = new ArrayList<>();
        createItems.add(selected);
        linkName += ".xhtml?faces-redirect=true";
        return linkName;
    }

    public void save() {
        try {
            BigDecimal max = new BigDecimal("9999999999999999.99");
            BigDecimal min = BigDecimal.ZERO;

            // Validate valueParam
            if (selected.getValueParam() == null || selected.getValueParam().compareTo(min) < 0 || selected.getValueParam().compareTo(max) > 0) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Invalid value: Value Param must be between 0.00 and 9999999999999999.99", null));
                return;
            }

            // Validate actualRebateAmount
            if (selected.getActualRebateAmount() != null &&
                    (selected.getActualRebateAmount().compareTo(min) < 0 || selected.getActualRebateAmount().compareTo(max) > 0)) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Invalid value: Actual Rebate Amount must be between 0.00 and 9999999999999999.99", null));
                return;
            }

            // Validate currentValueMetric
            if (selected.getCurrentValueMetric() != null &&
                    (selected.getCurrentValueMetric().compareTo(min) < 0 || selected.getCurrentValueMetric().compareTo(max) > 0)) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Invalid value: Current Value Metric must be between 0.00 and 9999999999999999.99", null));
                return;
            }

            // Validate estimatedRebateAmount
            if (selected.getEstimatedRebateAmount() != null &&
                    (selected.getEstimatedRebateAmount().compareTo(min) < 0 || selected.getEstimatedRebateAmount().compareTo(max) > 0)) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Invalid value: Estimated Rebate Amount must be between 0.00 and 9999999999999999.99", null));
                return;
            }

            // Validate targetValueMetric
            if (selected.getTargetValueMetric() != null &&
                    (selected.getTargetValueMetric().compareTo(min) < 0 || selected.getTargetValueMetric().compareTo(max) > 0)) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Invalid value: Target Value Metric must be between 0.00 and 9999999999999999.99", null));
                return;
            }

            // Validate required fields
            if (selected.getStatus() == null) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Status is required.", null));
                return;
            }

            if (selected.getContractId() == null || selected.getContractId().getId() == null) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Contract must be selected.", null));
                return;
            }

            if (selected.getRebateType() == null) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Rebate Type is required.", null));
                return;
            }

            if (selected.getCalculationBasis() == null) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Calculation Basis is required.", null));
                return;
            }

            if (selected.getConditionText() == null || selected.getConditionText().trim().isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Condition Text is required.", null));
                return;
            }

            // Save or update
            if (selected.getId() == null) {
                getFacade().create(selected);
            } else {
                getFacade().edit(selected);
            }

            if (!JsfUtil.isValidationFailed()) {
                items = null; // refresh list
                JsfUtil.addSuccessMessage("Saved successfully.");
            }

        } catch (ConstraintViolationException e) {
            for (ConstraintViolation<?> v : e.getConstraintViolations()) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                v.getPropertyPath() + ": " + v.getMessage(), null));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Error: " + e.getMessage(), null));
        }
    }



    public void saveRow() {
        for (RebatesDiscountsTable item : getEditItems()) {
            if (item.getId() == null) {
                getFacade().create(item);
            } else {
                getFacade().edit(item);
            }

        }
        if (!JsfUtil.isValidationFailed()) {
            // Invalidate list of items to trigger re-query.
            JsfUtil.addSuccessMessage("Saved");
        }
    }

    public void saveInEdit() {

        for (RebatesDiscountsTable item : getEditItems()) {
            if (item.getId() == null) {
                getFacade().create(item);
            } else {
                getFacade().edit(item);
            }
        }
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
            JsfUtil.addSuccessMessage("Saved");
        }
    }

    public void createInEdit() {
        getFacade().create(selected1);
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE);
    }

    public void removeInCreate(RebatesDiscountsTable item) {

        if (item.getId() == null) {
            createItems.removeIf(eachElement -> eachElement.getTempId().equals(item.getTempId()));
        } else {
            createItems.removeIf(eachElement -> eachElement.getId().equals(item.getId()));
            getFacade().remove(item);
        }

    }

    public void removeInEdit(RebatesDiscountsTable item) {

        if (item.getId() == null) {
            editItems.removeIf(eachElement -> eachElement.getTempId().equals(item.getTempId()));
        } else {
            editItems.removeIf(eachElement -> eachElement.getId().equals(item.getId()));
            getFacade().remove(item);
        }

    }

    public void removeRecord(RebatesDiscountsTable item) {

        if (item.getId() != null) {
            getFacade().remove(item);
        }

    }

    public void removeList(List<RebatesDiscountsTable> aList) {

        if (aList != null && !aList.isEmpty()) {
            getFacade().removeCollection(aList);
        }

    }

    public void destroy() {
        persist(PersistAction.DELETE);
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
            multiselectionItems = null;
        }
    }

    public List<RebatesDiscountsTable> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    public void setItems(List<RebatesDiscountsTable> items) {
        this.items = items;
    }

    public List<RebatesDiscountsTable> getMultiselectionItems() {
        return multiselectionItems;
    }

    public void setMultiselectionItems(List<RebatesDiscountsTable> multiselectionItems) {
        this.multiselectionItems = multiselectionItems;
    }

    public List<RebatesDiscountsTable> getFilteredValues() {
        return filteredValues;
    }

    public void setFilteredValues(List<RebatesDiscountsTable> filteredValues) {
        this.filteredValues = filteredValues;
    }

    public List<RebatesDiscountsTable> getCreateItems() {
        if (createItems == null) {
            createItems = getFacade().findAll();
        }
        return createItems;
    }

    public void setCreateItems(List<RebatesDiscountsTable> createItems) {
        this.createItems = createItems;
    }

    public List<RebatesDiscountsTable> getEditItems() {
        if (editItems == null) {
            editItems = getFacade().findAll();
        }
        return editItems;
    }

    public void setEditItems(List<RebatesDiscountsTable> editItems) {
        this.editItems = editItems;
    }

    private List<RebatesDiscountsTable> preparingTempId(RebatesDiscountsTable item, List<RebatesDiscountsTable> aList) {
        int tempId = 0;
        if (!aList.isEmpty()) {
            for (RebatesDiscountsTable itm : aList) {
                if (itm.getTempId() != null && itm.getTempId() > tempId) {
                    tempId = itm.getTempId();
                }
            }
        }
        tempId += 1;
        item.setTempId(tempId);
        aList.add(item);
        return aList;
    }

    private void persist(PersistAction persistAction) {
        if (!multiselectionItems.isEmpty()) {
            for (RebatesDiscountsTable item : multiselectionItems) {
                setEmbeddableKeys();
                try {
                    if (persistAction != PersistAction.DELETE) {
                        getFacade().edit(item);
                    } else {
                        getFacade().remove(item);
                    }

                } catch (EJBException ex) {
                    String msg = "";
                    Throwable cause = ex.getCause();
                    if (cause != null) {
                        msg = cause.getLocalizedMessage();
                    }
                    if (msg.length() > 0) {
                        JsfUtil.addErrorMessage(msg);
                    } else {
                        JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                    }
                } catch (Exception ex) {
                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            }
        }
    }

    public RebatesDiscountsTable getRebatesDiscountsTable(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<RebatesDiscountsTable> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<RebatesDiscountsTable> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = RebatesDiscountsTable.class)
    public static class RebatesDiscountsTableControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RebatesDiscountsTableController controller = (RebatesDiscountsTableController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "rebatesDiscountsTableController");
            return controller.getRebatesDiscountsTable(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof RebatesDiscountsTable) {
                RebatesDiscountsTable o = (RebatesDiscountsTable) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), RebatesDiscountsTable.class.getName()});
                return null;
            }
        }

    }

}
