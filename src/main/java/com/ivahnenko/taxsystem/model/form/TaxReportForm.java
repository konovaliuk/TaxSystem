package com.ivahnenko.taxsystem.model.form;

import java.time.LocalDate;

import com.ivahnenko.taxsystem.model.Taxpayer;
import com.ivahnenko.taxsystem.model.User;

public class TaxReportForm extends Form {
    private static final long serialVersionUID = 1L;
    private byte quarter;
    private short year;
    private double mainActivityIncome;
    private double investmentIncome;
    private double propertyIncome;
    private double mainActivityExpenses;
    private double investmentExpenses;
    private double propertyExpenses;

    public static class Builder {
        TaxReportForm taxReportForm;

        public Builder() {
            taxReportForm = new TaxReportForm();
        }

        public Builder setId(Integer id) {
            taxReportForm.setId(id);
            return this;
        }

        public Builder setQuarter(byte quarter) {
            taxReportForm.setQuarter(quarter);
            return this;
        }

        public Builder setYear(short year) {
            taxReportForm.setYear(year);
            return this;
        }

        public Builder setTaxpayer(Taxpayer taxpayer) {
            taxReportForm.setTaxpayer(taxpayer);
            return this;
        }

        public Builder setInitiator(User initiator) {
            taxReportForm.setInitiator(initiator);
            return this;
        }

        public Builder setReviewer(User reviewer) {
            taxReportForm.setReviewer(reviewer);
            return this;
        }

        public Builder setReviewerComment(String reviewerComment) {
            taxReportForm.setReviewerComment(reviewerComment);
            return this;
        }

        public Builder setCreationDate(LocalDate creationDate) {
            taxReportForm.setCreationDate(creationDate);
            return this;
        }

        public Builder setLastModifiedDate(LocalDate lastModifiedDate) {
            taxReportForm.setLastModifiedDate(lastModifiedDate);
            return this;
        }

        public Builder setStatus(Status status) {
            taxReportForm.setStatus(status);
            return this;
        }

        public Builder setMainActivityIncome(double amount) {
            taxReportForm.setMainActivityIncome(amount);
            return this;
        }

        public Builder setInvestmentIncome(double amount) {
            taxReportForm.setInvestmentIncome(amount);
            return this;
        }

        public Builder setPropertyIncome(double amount) {
            taxReportForm.setPropertyIncome(amount);
            return this;
        }

        public Builder setMainActivityExpenses(double amount) {
            taxReportForm.setMainActivityExpenses(amount);
            return this;
        }

        public Builder setInvestmentExpenses(double amount) {
            taxReportForm.setInvestmentExpenses(amount);
            return this;
        }

        public Builder setPropertyExpenses(double amount) {
            taxReportForm.setPropertyExpenses(amount);
            return this;
        }

        public TaxReportForm build() {
            return taxReportForm;
        }
    }
    
    public byte getQuarter() {
        return quarter;
    }

    public void setQuarter(byte quarter) {
        this.quarter = quarter;
    }

    public short getYear() {
        return year;
    }

    public void setYear(short year) {
        this.year = year;
    }

    public double getMainActivityIncome() {
        return mainActivityIncome;
    }

    public void setMainActivityIncome(double amount) {
        this.mainActivityIncome = amount;
    }

    public double getInvestmentIncome() {
        return investmentIncome;
    }

    public void setInvestmentIncome(double amount) {
        this.investmentIncome = amount;
    }

    public double getPropertyIncome() {
        return propertyIncome;
    }

    public void setPropertyIncome(double amount) {
        this.propertyIncome = amount;
    }

    public double getMainActivityExpenses() {
        return mainActivityExpenses;
    }

    public void setMainActivityExpenses(double amount) {
        this.mainActivityExpenses = amount;
    }

    public double getInvestmentExpenses() {
        return investmentExpenses;
    }

    public void setInvestmentExpenses(double amount) {
        this.investmentExpenses = amount;
    }

    public double getPropertyExpenses() {
        return propertyExpenses;
    }

    public void setPropertyExpenses(double amount) {
        this.propertyExpenses = amount;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        long temp;
        temp = Double.doubleToLongBits(investmentExpenses);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(investmentIncome);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(mainActivityExpenses);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(mainActivityIncome);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(propertyExpenses);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(propertyIncome);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + quarter;
        result = prime * result + year;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        TaxReportForm other = (TaxReportForm) obj;
        if (Double.doubleToLongBits(investmentExpenses) != Double.doubleToLongBits(other.investmentExpenses))
            return false;
        if (Double.doubleToLongBits(investmentIncome) != Double.doubleToLongBits(other.investmentIncome))
            return false;
        if (Double.doubleToLongBits(mainActivityExpenses) != Double.doubleToLongBits(other.mainActivityExpenses))
            return false;
        if (Double.doubleToLongBits(mainActivityIncome) != Double.doubleToLongBits(other.mainActivityIncome))
            return false;
        if (Double.doubleToLongBits(propertyExpenses) != Double.doubleToLongBits(other.propertyExpenses))
            return false;
        if (Double.doubleToLongBits(propertyIncome) != Double.doubleToLongBits(other.propertyIncome))
            return false;
        if (quarter != other.quarter)
            return false;
        if (year != other.year)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "TaxReportForm [quarter=" + quarter + ", year=" + year
                + ", mainActivityIncome=" + mainActivityIncome + ", investmentIncome=" + investmentIncome
                + ", propertyIncome=" + propertyIncome + ", mainActivityExpenses=" + mainActivityExpenses
                + ", investmentExpenses=" + investmentExpenses + ", propertyExpenses=" + propertyExpenses
                + super.toString() + "]";
    }
}
