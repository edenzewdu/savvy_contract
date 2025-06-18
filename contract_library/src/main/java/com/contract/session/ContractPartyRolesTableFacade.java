/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.contract.session;

import com.contract.entity.ContractPartiesTable;
import com.contract.entity.ContractPartyRolesTable;
import com.contract.entity.ContractsTable;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

/**
 *
 * @author Lucy
 */
@Stateless
public class ContractPartyRolesTableFacade extends AbstractFacadeSavvy<ContractPartyRolesTable> {

    @PersistenceContext(unitName = "com.contract_library_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ContractPartyRolesTableFacade() {
        super(ContractPartyRolesTable.class);
    }

    public List<ContractPartyRolesTable> findByContractAndParty(ContractsTable contract, ContractPartiesTable party) {
        return em.createQuery(
                        "SELECT c FROM ContractPartyRolesTable c WHERE c.contractId = :contract AND c.partyId = :party", ContractPartyRolesTable.class)
                .setParameter("contract", contract)
                .setParameter("party", party)
                .getResultList();
    }


}
