package com.contract.jsf;

import com.contract.entity.ContractsTable;
import com.contract.session.ContractsTableFacade;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named("contractsController")
@SessionScoped
public class ContractsController implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private ContractsTableFacade contractsFacade;

    private ContractsTable selected;
    private List<ContractsTable> items;

    private boolean partyFormDisabled = true;

    public ContractsController() {}

    @PostConstruct
    public void init() {
        selected = new ContractsTable();
        items = contractsFacade.findAll();
    }

    public ContractsTable getSelected() {
        if (selected == null) {
            selected = new ContractsTable();
        }
        return selected;
    }

    public void setSelected(ContractsTable selected) {
        this.selected = selected;
    }

    public List<ContractsTable> getItems() {
        if (items == null) {
            items = contractsFacade.findAll();
        }
        return items;
    }

    public void setItems(List<ContractsTable> items) {
        this.items = items;
    }

    public boolean isPartyFormDisabled() {
        return partyFormDisabled;
    }

    public void setPartyFormDisabled(boolean partyFormDisabled) {
        this.partyFormDisabled = partyFormDisabled;
    }

    // Save Contract
    public String save() {
        try {
            if (selected != null) {
                contractsFacade.edit(selected); // edit() handles both persist & merge
                selected = new ContractsTable();
                items = contractsFacade.findAll(); // refresh list
                partyFormDisabled = false;
            }
            return "List.xhtml?faces-redirect=true";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Cancel Creation
    public void cancelCreate() {
        selected = new ContractsTable();
        partyFormDisabled = true;
    }

    // Discard Changes
    public void discard() {
        selected = null;
        partyFormDisabled = true;
    }

    // Reset Contract & Party Forms
    public void resetForms() {
        selected = new ContractsTable();
        partyFormDisabled = true;
    }
}
