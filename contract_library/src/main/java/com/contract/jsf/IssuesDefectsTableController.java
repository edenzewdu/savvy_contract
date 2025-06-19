package com.contract.jsf;


import com.contract.entity.IssuesDefectsTable;
import com.contract.entity.WarrantiesTable;
import com.contract.enums.Severity;
import com.contract.enums.Status;
import com.contract.jsf.util.JsfUtil;
import com.contract.jsf.util.JsfUtil.PersistAction;
import com.contract.session.IssuesDefectsTableFacade;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.component.UIComponent;
import java.util.ArrayList;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@Named("issuesDefectsTableController")
@SessionScoped
public class IssuesDefectsTableController implements Serializable {

    @EJB
    private com.contract.session.IssuesDefectsTableFacade ejbFacade;
    @EJB
    private com.contract.session.AbstractFacadeQuerySavvy ejbFacade1;
    private IssuesDefectsTable selectedIssues = new IssuesDefectsTable();
    private List<IssuesDefectsTable> items = null;
    private List<IssuesDefectsTable> multiselectionItems = null;
    private List<IssuesDefectsTable> createItems = null;
    private List<IssuesDefectsTable> editItems = null;
    private List<IssuesDefectsTable> filteredValues = null;
    private IssuesDefectsTable selected = new IssuesDefectsTable();
    private IssuesDefectsTable selected1;
    private IssuesDefectsTable selected2 = new IssuesDefectsTable();
    private String dataName = "IssuesDefectsTable";
    protected int first;

    public IssuesDefectsTableController() {
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

    public Severity[] getSeverityItems() {
        return Severity.values();
    }

    public Status[] getStatusItems() {
        return Status.values();
    }

    public IssuesDefectsTable getSelectedIssues() {
        return selectedIssues;
    }

    public void setSelectedIssues(IssuesDefectsTable selectedIssues) {
        this.selectedIssues = selectedIssues;
    }

    public IssuesDefectsTable getSelected() {

        if (selected == null) {
            selected = new IssuesDefectsTable();
        }
        return selected;
    }

    public void setSelected(IssuesDefectsTable selected) {
        this.selected = selected;
    }

    public IssuesDefectsTable getSelected1() {
        return selected1;
    }

    public void setSelected1(IssuesDefectsTable selected1) {
        this.selected1 = selected1;
    }

    public IssuesDefectsTable getSelected2() {
        return selected2;
    }

    public void setSelected2(IssuesDefectsTable selected2) {
        this.selected2 = selected2;
    }

    public void cancelUpdate() {
        selected1 = null;

        editItems = null;
    }

    public void discard() {
        selected = null;
        for (IssuesDefectsTable item : getCreateItems()) {
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

    private IssuesDefectsTableFacade getFacade() {
        return ejbFacade;
    }

    public IssuesDefectsTable prepareCreate() {
        createItems = new ArrayList<>();

        int tempid = 1;

        selected = new IssuesDefectsTable();
        selected.setTempId(tempid);

        createItems.add(selected);
        initializeEmbeddableKey();
        return selected;
    }

    public IssuesDefectsTable prepareCopy() {

        if (createItems == null) {
            createItems = new ArrayList<>();
        }
        selected = multiselectionItems.get(0);
        selected.setId(null);
        createItems.add(selected);
        initializeEmbeddableKey();
        return selected;
    }

    public IssuesDefectsTable prepareCreateInCreate() {

        selected1 = new IssuesDefectsTable();
        createItems = preparingTempId(selected1, createItems);
        initializeEmbeddableKey();
        return selected1;
    }

    public IssuesDefectsTable prepareCreate1() {
        selected = new IssuesDefectsTable();
        int tempId = 0;
        for (IssuesDefectsTable p : createItems) {
            if (p.getTempId() > tempId) {
                tempId = p.getTempId();
            }
        }
        selected.setTempId(tempId + 1);
        createItems.add(selected);
        return selected;
    }

    public IssuesDefectsTable prepareCreateInEdit() {

        selected1 = new IssuesDefectsTable();
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
        createItems.add(new IssuesDefectsTable());
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
            if (selected == null || selected.getContractId() == null) {
                JsfUtil.addErrorMessage("Validation Error: Contract is required.");
                return;
            }
            if (!validateEntity(selected)) {
                JsfUtil.addErrorMessage("Validation failed. Please check your input.");
                return;  // stop saving if validation fails
            }

            if (selected.getId() == null) {
                getFacade().create(selected);
            } else {
                getFacade().edit(selected);
            }

            if (!JsfUtil.isValidationFailed()) {
                items = null;    // Invalidate list of items to trigger re-query.
                selected = null;
                JsfUtil.addSuccessMessage("Saved");
            }
        }  catch (EJBException ejbEx) {
            Throwable cause = ejbEx.getCause();
            while (cause != null) {
                if (cause instanceof ConstraintViolationException) {
                    ConstraintViolationException cve = (ConstraintViolationException) cause;
                    for (ConstraintViolation<?> violation : cve.getConstraintViolations()) {
                        System.out.println("Validation error in " + violation.getPropertyPath() + ": " + violation.getMessage());
                    }
                    break;
                }
                cause = cause.getCause();
            }
            // Optional: rethrow or show error message
            JsfUtil.addErrorMessage("Validation failed: check server log for details.");
        } catch (Exception ex) {
            ex.printStackTrace();
            JsfUtil.addErrorMessage("Save failed: " + ex.getMessage());
        }
    }
    private boolean validateEntity(IssuesDefectsTable entity) {
        boolean valid = true;

        // issueDescription - required, min 1 char, max 65535 (handled by DB size but check null/empty)
        if (entity.getIssueDescription() == null || entity.getIssueDescription().trim().isEmpty()) {
            JsfUtil.addErrorMessage("Issue description cannot be empty.");
            valid = false;
        }

        // reportedDate - must not be null and not future date
        if (entity.getReportedDate() == null) {
            JsfUtil.addErrorMessage("Reported date is required.");
            valid = false;
        } else if (entity.getReportedDate().after(new Date())) {
            JsfUtil.addErrorMessage("Reported date cannot be in the future.");
            valid = false;
        }

        // Optionally validate optional fields length (impact, resolutionDetails) if present
        if (entity.getImpact() != null && entity.getImpact().length() > 65535) {
            JsfUtil.addErrorMessage("Impact text is too long.");
            valid = false;
        }

        if (entity.getResolutionDetails() != null && entity.getResolutionDetails().length() > 65535) {
            JsfUtil.addErrorMessage("Resolution details text is too long.");
            valid = false;
        }

        // Add any other business validations you need...

        return valid;
    }
    public void saveRow() {
        for (IssuesDefectsTable item : getEditItems()) {
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

        for (IssuesDefectsTable item : getEditItems()) {
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

    public void removeInCreate(IssuesDefectsTable item) {

        if (item.getId() == null) {
            createItems.removeIf(eachElement -> eachElement.getTempId().equals(item.getTempId()));
        } else {
            createItems.removeIf(eachElement -> eachElement.getId().equals(item.getId()));
            getFacade().remove(item);
        }

    }

    public void removeInEdit(IssuesDefectsTable item) {

        if (item.getId() == null) {
            editItems.removeIf(eachElement -> eachElement.getTempId().equals(item.getTempId()));
        } else {
            editItems.removeIf(eachElement -> eachElement.getId().equals(item.getId()));
            getFacade().remove(item);
        }

    }

    public void removeRecord(IssuesDefectsTable item) {

        if (item.getId() != null) {
            getFacade().remove(item);
        }

    }

    public void removeList(List<IssuesDefectsTable> aList) {

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

    public List<IssuesDefectsTable> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    public void setItems(List<IssuesDefectsTable> items) {
        this.items = items;
    }

    public List<IssuesDefectsTable> getMultiselectionItems() {
        return multiselectionItems;
    }

    public void setMultiselectionItems(List<IssuesDefectsTable> multiselectionItems) {
        this.multiselectionItems = multiselectionItems;
    }

    public List<IssuesDefectsTable> getFilteredValues() {
        return filteredValues;
    }

    public void setFilteredValues(List<IssuesDefectsTable> filteredValues) {
        this.filteredValues = filteredValues;
    }

    public List<IssuesDefectsTable> getCreateItems() {
        if (createItems == null) {
            createItems = getFacade().findAll();
        }
        return createItems;
    }

    public void setCreateItems(List<IssuesDefectsTable> createItems) {
        this.createItems = createItems;
    }

    public List<IssuesDefectsTable> getEditItems() {
        if (editItems == null) {
            editItems = getFacade().findAll();
        }
        return editItems;
    }

    public void setEditItems(List<IssuesDefectsTable> editItems) {
        this.editItems = editItems;
    }

    private List<IssuesDefectsTable> preparingTempId(IssuesDefectsTable item, List<IssuesDefectsTable> aList) {
        int tempId = 0;
        if (!aList.isEmpty()) {
            for (IssuesDefectsTable itm : aList) {
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
            for (IssuesDefectsTable item : multiselectionItems) {
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

    public IssuesDefectsTable getIssuesDefectsTable(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<IssuesDefectsTable> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<IssuesDefectsTable> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = IssuesDefectsTable.class)
    public static class IssuesDefectsTableControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            IssuesDefectsTableController controller = (IssuesDefectsTableController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "issuesDefectsTableController");
            return controller.getIssuesDefectsTable(getKey(value));
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
            if (object instanceof IssuesDefectsTable) {
                IssuesDefectsTable o = (IssuesDefectsTable) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), IssuesDefectsTable.class.getName()});
                return null;
            }
        }

    }

}
