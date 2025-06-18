/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.contract.session;

import com.contract.entity.ContractsTable;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 *
 * @author Lucy
 */
@Stateless
public class ContractsTableFacade extends AbstractFacadeSavvy<ContractsTable> {

    @PersistenceContext(unitName = "com.contract_library_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ContractsTableFacade() {
        super(ContractsTable.class);
    }
    public boolean existsByContractTitle(String contractTitle) {
        try {
            Long count = em.createQuery(
                            "SELECT COUNT(c) FROM ContractsTable c WHERE LOWER(c.contractTitle) = :title", Long.class)
                    .setParameter("title", contractTitle.toLowerCase())
                    .getSingleResult();

            return count > 0;
        } catch (Exception e) {
            // Optionally log the error
            return false;
        }
    }


}
