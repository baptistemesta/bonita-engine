package org.bonitasoft.engine.bdm.server;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Version;
import org.hibernate.annotations.Index;


/**
 * 
 */
@javax.persistence.Entity(name = "Employee")
@org.hibernate.annotations.Table(appliesTo = "EMPLOYEE", indexes = {
    @Index(name = "IDX_1", columnNames = {
        "FIRSTNAME, LASTNAME"
    })
})
@javax.persistence.Table(name = "EMPLOYEE")
@NamedQueries({
    @NamedQuery(name = "Employee.findByFirstName", query = "SELECT e\nFROM Employee e\nWHERE e.firstName= :firstName\nORDER BY e.persistenceId"),
    @NamedQuery(name = "Employee.find", query = "SELECT e\nFROM Employee e\nORDER BY e.persistenceId")
})
public class Employee
    implements org.bonitasoft.engine.bdm.Entity
{

    @Id
    @GeneratedValue
    private Long persistenceId;
    @Version
    private Long persistenceVersion;
    @Column(name = "FIRSTNAME", nullable = true)
    private String firstName;

    public Employee() {
    }

    public void setPersistenceId(Long persistenceId) {
        this.persistenceId = persistenceId;
    }

    public Long getPersistenceId() {
        return persistenceId;
    }

    public void setPersistenceVersion(Long persistenceVersion) {
        this.persistenceVersion = persistenceVersion;
    }

    public Long getPersistenceVersion() {
        return persistenceVersion;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass()!= obj.getClass()) {
            return false;
        }
        Employee other = ((Employee) obj);
        if (persistenceId == null) {
            if (other.persistenceId!= null) {
                return false;
            }
        } else {
            if (!persistenceId.equals(other.persistenceId)) {
                return false;
            }
        }
        if (persistenceVersion == null) {
            if (other.persistenceVersion!= null) {
                return false;
            }
        } else {
            if (!persistenceVersion.equals(other.persistenceVersion)) {
                return false;
            }
        }
        if (firstName == null) {
            if (other.firstName!= null) {
                return false;
            }
        } else {
            if (!firstName.equals(other.firstName)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        int persistenceIdCode = 0;
        if (persistenceId!= null) {
            persistenceIdCode = persistenceId.hashCode();
        }
        result = ((prime*result)+ persistenceIdCode);
        int persistenceVersionCode = 0;
        if (persistenceVersion!= null) {
            persistenceVersionCode = persistenceVersion.hashCode();
        }
        result = ((prime*result)+ persistenceVersionCode);
        int firstNameCode = 0;
        if (firstName!= null) {
            firstNameCode = firstName.hashCode();
        }
        result = ((prime*result)+ firstNameCode);
        return result;
    }

}