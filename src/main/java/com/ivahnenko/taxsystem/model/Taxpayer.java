package com.ivahnenko.taxsystem.model;

import java.io.Serializable;

public class Taxpayer implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;
    private String registrationNumber;
    private String email;
    private String postcode;
    private OwnershipType ownershipType;
    private User employee;

    public enum OwnershipType {

        SOLE_PROPRIETORSHIP, LIMITED, JOINT_VENTURE, JOINT_STOCK;
    	
    	@Override
		public String toString() {
			return name().toUpperCase();	
		}
    }
    
    public static class Builder {
        private Taxpayer taxpayer;

        public Builder() {
            taxpayer = new Taxpayer();
        }

        public Builder setId(Integer id) {
            taxpayer.setId(id);
            return this;
        }

        public Builder setName(String name) {
            taxpayer.setName(name);
            return this;
        }

        public Builder setRegistrationNumber(String registrationNumber) {
            taxpayer.setRegistrationNumber(registrationNumber);
            return this;
        }

        public Builder setEmail(String email) {
            taxpayer.setEmail(email);
            return this;
        }

        public Builder setPostcode(String postcode) {
            taxpayer.setPostcode(postcode);
            return this;
        }

        public Builder setOwnershipType(OwnershipType ownershipType) {
            taxpayer.setOwnershipType(ownershipType);
            return this;
        }

        public Builder setEmployee(User employee) {
            taxpayer.setEmployee(employee);
            return this;
        }

        public Taxpayer build() {
            return taxpayer;
        }
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public OwnershipType getOwnershipType() {
        return ownershipType;
    }

    public void setOwnershipType(OwnershipType ownershipType) {
        this.ownershipType = ownershipType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public User getEmployee() {
        return employee;
    }

    public void setEmployee(User employee) {
        this.employee = employee;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((employee == null) ? 0 : employee.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((ownershipType == null) ? 0 : ownershipType.hashCode());
        result = prime * result + ((postcode == null) ? 0 : postcode.hashCode());
        result = prime * result + ((registrationNumber == null) ? 0 : registrationNumber.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Taxpayer other = (Taxpayer) obj;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        if (employee == null) {
            if (other.employee != null)
                return false;
        } else if (!employee.equals(other.employee))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (ownershipType != other.ownershipType)
            return false;
        if (postcode == null) {
            if (other.postcode != null)
                return false;
        } else if (!postcode.equals(other.postcode))
            return false;
        if (registrationNumber == null) {
            if (other.registrationNumber != null)
                return false;
        } else if (!registrationNumber.equals(other.registrationNumber))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Taxpayer [id=" + id + ", name=" + name + ", registrationNumber=" + registrationNumber + ", email="
                + email + ", postcode=" + postcode + ", ownership=" + ownershipType + ", employee=" + employee + "]";
    }
}
