package com.ivahnenko.taxsystem.model.form;

import java.time.LocalDate;

import com.ivahnenko.taxsystem.model.Taxpayer;
import com.ivahnenko.taxsystem.model.User;

public abstract class Form {
    private Integer id;
    private Taxpayer taxpayer;
    private User initiator;
    private User reviewer;
    private String reviewerComment;
    private LocalDate creationDate;
    private LocalDate lastModifiedDate;
    private Status status;

    public enum Status {
        NEW, TODO, IN_PROGRESS, DONE, RETURNED;
    	
    	@Override
		public String toString() {
			return name().toUpperCase();	
		}
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Taxpayer getTaxpayer() {
        return taxpayer;
    }

    public void setTaxpayer(Taxpayer taxpayer) {
        this.taxpayer = taxpayer;
    }

    public User getInitiator() {
        return initiator;
    }

    public void setInitiator(User initiator) {
        this.initiator = initiator;
    }

    public User getReviewer() {
        return reviewer;
    }

    public void setReviewer(User reviewer) {
        this.reviewer = reviewer;
    }

    public String getReviewerComment() {
        return reviewerComment;
    }

    public void setReviewerComment(String reviewerComment) {
        this.reviewerComment = reviewerComment;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDate getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(LocalDate lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((creationDate == null) ? 0 : creationDate.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((initiator == null) ? 0 : initiator.hashCode());
        result = prime * result + ((lastModifiedDate == null) ? 0 : lastModifiedDate.hashCode());
        result = prime * result + ((reviewer == null) ? 0 : reviewer.hashCode());
        result = prime * result + ((reviewerComment == null) ? 0 : reviewerComment.hashCode());
        result = prime * result + ((status == null) ? 0 : status.hashCode());
        result = prime * result + ((taxpayer == null) ? 0 : taxpayer.hashCode());
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
        Form other = (Form) obj;
        if (creationDate == null) {
            if (other.creationDate != null)
                return false;
        } else if (!creationDate.equals(other.creationDate))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (initiator == null) {
            if (other.initiator != null)
                return false;
        } else if (!initiator.equals(other.initiator))
            return false;
        if (lastModifiedDate == null) {
            if (other.lastModifiedDate != null)
                return false;
        } else if (!lastModifiedDate.equals(other.lastModifiedDate))
            return false;
        if (reviewer == null) {
            if (other.reviewer != null)
                return false;
        } else if (!reviewer.equals(other.reviewer))
            return false;
        if (reviewerComment == null) {
            if (other.reviewerComment != null)
                return false;
        } else if (!reviewerComment.equals(other.reviewerComment))
            return false;
        if (status != other.status)
            return false;
        if (taxpayer == null) {
            if (other.taxpayer != null)
                return false;
        } else if (!taxpayer.equals(other.taxpayer))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Form [id=" + id + ", taxpayer=" + taxpayer.getName() + ", initiator=" + initiator.getEmail() 
        		+ ", reviewer=" + reviewer.getEmail() + ", creationDate=" + creationDate + ", " 
        		+ "lastModifiedDate=" + lastModifiedDate + ", status=" + status + "]";
    }
}
