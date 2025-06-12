//package com.contract.jsf;
//
//import com.contract.entity.EmployeesTable;
//import com.contract.jsf.util.JsfUtil;
//import com.contract.jsf.util.JsfUtil.PersistAction;
//import com.contract.session.EmployeesFacade;
//
//import java.io.Serializable;
//import java.util.List;
//import java.util.ResourceBundle;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import jakarta.ejb.EJB;
//import jakarta.ejb.EJBException;
//import jakarta.inject.Named;
//import jakarta.enterprise.context.SessionScoped;
//import jakarta.faces.component.UIComponent;
//import java.util.ArrayList;
//import jakarta.faces.context.FacesContext;
//import jakarta.faces.convert.Converter;
//import jakarta.faces.convert.FacesConverter;
//
//@Named("employeesController")
//@SessionScoped
//public class EmployeesController implements Serializable {
//
//    @EJB
//    private com.contract.session.EmployeesFacade ejbFacade;
//    @EJB
//    private com.contract.session.AbstractFacadeQuerySavvy ejbFacade1;
//    private List<EmployeesTable> items = null;
//    private List<EmployeesTable> multiselectionItems = null;
//    private List<EmployeesTable> createItems = null;
//    private List<EmployeesTable> editItems = null;
//    private List<EmployeesTable> filteredValues = null;
//    private EmployeesTable selected;
//    private EmployeesTable selected1;
//    private EmployeesTable selected2 = new EmployeesTable();
//    private String dataName = "com.contract.entityEmployees";
//    protected int first;
//
//    public EmployeesController() {
//    }
//
//    public int getFirst() {
//        return first;
//    }
//
//    public void setFirst(int first) {
//        this.first = first;
//    }
//
//    public String getDataName() {
//        return this.dataName;
//    }
//
//    public void setDataName(final String dataName) {
//        this.dataName = dataName;
//    }
//
//    public EmployeesTable getSelected() {
//        return selected;
//    }
//
//    public void setSelected(EmployeesTable selected) {
//        this.selected = selected;
//    }
//
//    public EmployeesTable getSelected1() {
//        return selected1;
//    }
//
//    public void setSelected1(EmployeesTable selected1) {
//        this.selected1 = selected1;
//    }
//
//    public EmployeesTable getSelected2() {
//        return selected2;
//    }
//
//    public void setSelected2(EmployeesTable selected2) {
//        this.selected2 = selected2;
//    }
//
//    public void cancelUpdate() {
//        selected1 = null;
//
//        editItems = null;
//    }
//
//    public void discard() {
//        selected = null;
//        for (EmployeesTable item : getCreateItems()) {
//            if (item.getId() != null) {
//                getFacade().remove(item);
//            }
//
//        }
//        createItems = null;
//        items = null;
//        if (!JsfUtil.isValidationFailed()) {
//            // Invalidate list of items to trigger re-query.
//            JsfUtil.addSuccessMessage("All records are removed");
//        }
//    }
//
//    public void cancelCreate() {
//        selected = null;
//        createItems = null;
//        items = null;
//    }
//
//    public void refreshList() {
//        items = null;
//    }
//
//    protected void setEmbeddableKeys() {
//    }
//
//    protected void initializeEmbeddableKey() {
//    }
//
//    private EmployeesFacade getFacade() {
//        return ejbFacade;
//    }
//
//    public EmployeesTable prepareCreate() {
//
//        selected = new EmployeesTable();
//
//        return selected;
//    }
//
//    public EmployeesTable prepareCopy() {
//
//        if (createItems == null) {
//            createItems = new ArrayList<>();
//        }
//        selected = multiselectionItems.get(0);
//        selected.setId(null);
//        createItems.add(selected);
//        initializeEmbeddableKey();
//        return selected;
//    }
//
//    public EmployeesTable prepareCreateInCreate() {
//
//        selected1 = new EmployeesTable();
//        createItems = preparingTempId(selected1, createItems);
//        initializeEmbeddableKey();
//        return selected1;
//    }
//
//    public EmployeesTable prepareCreate1() {
//        selected = new EmployeesTable();
//        int tempId = 0;
//        for (EmployeesTable p : createItems) {
//            if (p.getTempId() > tempId) {
//                tempId = p.getTempId();
//            }
//        }
//        selected.setTempId(tempId + 1);
//        createItems.add(selected);
//        return selected;
//    }
//
//    public EmployeesTable prepareCreateInEdit() {
//
//        selected1 = new EmployeesTable();
//        editItems = preparingTempId(selected1, editItems);
//        initializeEmbeddableKey();
//        return selected1;
//    }
//
//    public void prepareEdit() {
//
//    }
//
//    public String saveAndClose(String linkName) {
//        cancelUpdate();
//        cancelCreate();
//        linkName += ".xhtml?faces-redirect=true";
//        return linkName;
//    }
//
//    public String saveAndAddNew(String linkName) {
//        createItems = new ArrayList<>();
//        createItems.add(new EmployeesTable());
//        linkName += ".xhtml?faces-redirect=true";
//        return linkName;
//    }
//
//    public String saveAndAddContinue(String linkName) {
//        createItems = new ArrayList<>();
//        createItems.add(selected);
//        linkName += ".xhtml?faces-redirect=true";
//        return linkName;
//    }
//
//    public void save() {
//
//        //   for (EmployeesTable item : getCreateItems()) {
//        if (selected.getId() == null) {
//
//            getFacade().create(selected);
//        } else {
//            getFacade().edit(selected);
//        }
//        //   }
//        if (!JsfUtil.isValidationFailed()) {
//            items = null;    // Invalidate list of items to trigger re-query.
//            JsfUtil.addSuccessMessage("Saved");
//        }
//    }
//
//    public void saveRow() {
//        for (EmployeesTable item : getEditItems()) {
//            if (item.getId() == null) {
//                getFacade().create(item);
//            } else {
//                getFacade().edit(item);
//            }
//
//        }
//        if (!JsfUtil.isValidationFailed()) {
//            // Invalidate list of items to trigger re-query.
//            JsfUtil.addSuccessMessage("Saved");
//        }
//    }
//
//    public void saveInEdit() {
//
//        for (EmployeesTable item : getEditItems()) {
//            if (item.getId() == null) {
//                getFacade().create(item);
//            } else {
//                getFacade().edit(item);
//            }
//        }
//        if (!JsfUtil.isValidationFailed()) {
//            items = null;    // Invalidate list of items to trigger re-query.
//            JsfUtil.addSuccessMessage("Saved");
//        }
//    }
//
//    public void createInEdit() {
//        getFacade().create(selected1);
//        if (!JsfUtil.isValidationFailed()) {
//            items = null;    // Invalidate list of items to trigger re-query.
//        }
//    }
//
//    public void update() {
//        persist(PersistAction.UPDATE);
//    }
//
//    public void removeInCreate(EmployeesTable item) {
//
//        if (item.getId() == null) {
//            createItems.removeIf(eachElement -> eachElement.getTempId().equals(item.getTempId()));
//        } else {
//            createItems.removeIf(eachElement -> eachElement.getId().equals(item.getId()));
//            getFacade().remove(item);
//        }
//
//    }
//
//    public void removeInEdit(EmployeesTable item) {
//
//        if (item.getId() == null) {
//            editItems.removeIf(eachElement -> eachElement.getTempId().equals(item.getTempId()));
//        } else {
//            editItems.removeIf(eachElement -> eachElement.getId().equals(item.getId()));
//            getFacade().remove(item);
//        }
//
//    }
//
//    public void removeRecord(EmployeesTable item) {
//
//        if (item.getId() != null) {
//            getFacade().remove(item);
//        }
//
//    }
//
//    public void removeList(List<EmployeesTable> aList) {
//
//        if (aList != null && !aList.isEmpty()) {
//            getFacade().removeCollection(aList);
//        }
//
//    }
//
//    public void destroy() {
//        persist(PersistAction.DELETE);
//        if (!JsfUtil.isValidationFailed()) {
//            selected = null; // Remove selection
//            items = null;    // Invalidate list of items to trigger re-query.
//            multiselectionItems = null;
//        }
//    }
//
//    public List<EmployeesTable> getItems() {
//        if (items == null) {
//            items = getFacade().findAll();
//        }
//        return items;
//    }
//
//    public void setItems(List<EmployeesTable> items) {
//        this.items = items;
//    }
//
//    public List<EmployeesTable> getMultiselectionItems() {
//        return multiselectionItems;
//    }
//
//    public void setMultiselectionItems(List<EmployeesTable> multiselectionItems) {
//        this.multiselectionItems = multiselectionItems;
//    }
//
//    public List<EmployeesTable> getFilteredValues() {
//        return filteredValues;
//    }
//
//    public void setFilteredValues(List<EmployeesTable> filteredValues) {
//        this.filteredValues = filteredValues;
//    }
//
//    public List<EmployeesTable> getCreateItems() {
//        if (createItems == null) {
//            createItems = getFacade().findAll();
//        }
//        return createItems;
//    }
//
//    public void setCreateItems(List<EmployeesTable> createItems) {
//        this.createItems = createItems;
//    }
//
//    public List<EmployeesTable> getEditItems() {
//        if (editItems == null) {
//            editItems = getFacade().findAll();
//        }
//        return editItems;
//    }
//
//    public void setEditItems(List<EmployeesTable> editItems) {
//        this.editItems = editItems;
//    }
//
//    private List<EmployeesTable> preparingTempId(EmployeesTable item, List<EmployeesTable> aList) {
//        int tempId = 0;
//        if (!aList.isEmpty()) {
//            for (EmployeesTable itm : aList) {
//                if (itm.getTempId() != null && itm.getTempId() > tempId) {
//                    tempId = itm.getTempId();
//                }
//            }
//        }
//        tempId += 1;
//        item.setTempId(tempId);
//        aList.add(item);
//        return aList;
//    }
//
//    private void persist(PersistAction persistAction) {
//        if (!multiselectionItems.isEmpty()) {
//            for (EmployeesTable item : multiselectionItems) {
//                setEmbeddableKeys();
//                try {
//                    if (persistAction != PersistAction.DELETE) {
//                        getFacade().edit(item);
//                    } else {
//                        getFacade().remove(item);
//                    }
//
//                } catch (EJBException ex) {
//                    String msg = "";
//                    Throwable cause = ex.getCause();
//                    if (cause != null) {
//                        msg = cause.getLocalizedMessage();
//                    }
//                    if (msg.length() > 0) {
//                        JsfUtil.addErrorMessage(msg);
//                    } else {
//                        JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
//                    }
//                } catch (Exception ex) {
//                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
//                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
//                }
//            }
//        }
//    }
//
//    public EmployeesTable getEmployeesTable(java.lang.Integer id) {
//        return getFacade().find(id);
//    }
//
//    public List<EmployeesTable> getItemsAvailableSelectMany() {
//        return getFacade().findAll();
//    }
//
//    public List<EmployeesTable> getItemsAvailableSelectOne() {
//        return getFacade().findAll();
//    }
//
//    @FacesConverter(forClass = EmployeesTable.class)
//    public static class EmployeesControllerConverter implements Converter {
//
//        @Override
//        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
//            if (value == null || value.length() == 0) {
//                return null;
//            }
//            EmployeesController controller = (EmployeesController) facesContext.getApplication().getELResolver().
//                    getValue(facesContext.getELContext(), null, "employeesController");
//            return controller.getEmployeesTable(getKey(value));
//        }
//
//        java.lang.Integer getKey(String value) {
//            java.lang.Integer key;
//            key = Integer.valueOf(value);
//            return key;
//        }
//
//        String getStringKey(java.lang.Integer value) {
//            StringBuilder sb = new StringBuilder();
//            sb.append(value);
//            return sb.toString();
//        }
//
//        @Override
//        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
//            if (object == null) {
//                return null;
//            }
//            if (object instanceof EmployeesTable) {
//                EmployeesTable o = (EmployeesTable) object;
//                return getStringKey(o.getId());
//            } else {
//                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), EmployeesTable.class.getName()});
//                return null;
//            }
//        }
//
//    }
//
//}
