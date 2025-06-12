package com.contract.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "employees_table")
@NamedQueries({
        @NamedQuery(name = "EmployeesTable.findAll", query = "SELECT e FROM EmployeesTable e"),
        @NamedQuery(name = "EmployeesTable.findById", query = "SELECT e FROM EmployeesTable e WHERE e.id = :id")
})
public class EmployeesTable implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "employee_name", length = 100)
    private String employeeName;

    @Column(name = "position", length = 100)
    private String position;

    @Column(name = "email", length = 100, unique = true)
    private String email;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "supervisor_id")
    private EmployeesTable supervisor;

    @OneToMany(mappedBy = "supervisor")
    private List<EmployeesTable> subordinates;

    @OneToMany(mappedBy = "createdByUserId")
    private List<ContractsTable> contractsCreated;

    public EmployeesTable() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public EmployeesTable getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(EmployeesTable supervisor) {
        this.supervisor = supervisor;
    }

    public List<EmployeesTable> getSubordinates() {
        return subordinates;
    }

    public void setSubordinates(List<EmployeesTable> subordinates) {
        this.subordinates = subordinates;
    }

    public List<ContractsTable> getContractsCreated() {
        return contractsCreated;
    }

    public void setContractsCreated(List<ContractsTable> contractsCreated) {
        this.contractsCreated = contractsCreated;
    }
}
