/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.contract.session;

import com.contract.entity.PartyRoleTypesTable;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 *
 * @author Lucy
 */
@Stateless
public class PartyRoleTypesTableFacade extends AbstractFacadeSavvy<PartyRoleTypesTable> {

    @PersistenceContext(unitName = "com.contract_library_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PartyRoleTypesTableFacade() {
        super(PartyRoleTypesTable.class);
    }
    
}
