package com.ivahnenko.taxsystem.model.form;

import java.time.LocalDate;

import com.ivahnenko.taxsystem.model.Taxpayer;
import com.ivahnenko.taxsystem.model.User;

public class FeedbackForm extends Form {
    private String description;
    
    public static class Builder {
        FeedbackForm feedbackForm;

        public Builder() {
            feedbackForm = new FeedbackForm();
        }

        public Builder setId(Integer id) {
            feedbackForm.setId(id);
            return this;
        }

        public Builder setTaxpayer(Taxpayer taxpayer) {
            feedbackForm.setTaxpayer(taxpayer);
            return this;
        }

        public Builder setInitiator(User initiator) {
            feedbackForm.setInitiator(initiator);
            return this;
        }

        public Builder setReviewer(User reviewer) {
            feedbackForm.setReviewer(reviewer);
            return this;
        }

        public Builder setDescription(String description) {
            feedbackForm.setDescription(description);
            return this;
        }

        public Builder setReviewerComment(String reviewerComment) {
            feedbackForm.setReviewerComment(reviewerComment);
            return this;
        }

        public Builder setCreationDate(LocalDate creationDate) {
            feedbackForm.setCreationDate(creationDate);
            return this;
        }

        public Builder setLastModifiedDate(LocalDate lastModifiedDate) {
            feedbackForm.setLastModifiedDate(lastModifiedDate);
            return this;
        }

        public Builder setStatus(Status status) {
            feedbackForm.setStatus(status);
            return this;
        }

        public FeedbackForm build() {
            return feedbackForm;
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((description == null) ? 0 : description.hashCode());
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
        FeedbackForm other = (FeedbackForm) obj;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "FeedbackForm [ " + super.toString() + "]";
    }
}
