package by.chertok.pharmacy.entity;

import java.time.LocalDateTime;

public class Prescription extends BaseEntity {

    private LocalDateTime validUntil;
    private long doctorId;
    private String drugName;
    private long drugId;
    private long customerId;
    private String customerFirstName;
    private String customerLastName;
    private boolean approved;

    public long getDrugId() {
        return drugId;
    }

    public void setDrugId(long drugId) {
        this.drugId = drugId;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public Prescription(long id) {
        super(id);
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public LocalDateTime getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(LocalDateTime validUntil) {
        this.validUntil = validUntil;
    }

    public long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(long doctorId) {
        this.doctorId = doctorId;
    }


    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getCustomerFirstName() {
        return customerFirstName;
    }

    public void setCustomerFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) return false;
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Prescription that = (Prescription) o;
        return doctorId == that.doctorId &&
                approved == that.approved &&
                drugName == null ? that.drugName == null : drugName.equals(that.drugName) &&
                customerFirstName == null ? that.customerFirstName == null : customerFirstName.equals(that.customerFirstName) &&
                customerLastName == null ? that.customerLastName == null : customerLastName.equals(that.customerLastName) &&
                validUntil == null ? that.validUntil == null : validUntil.equals(that.validUntil);
    }

    @Override
    public int hashCode() {

        return (int)(5 * getId() + 11 * doctorId + 7 * drugName.hashCode()
                + 13 * customerFirstName.hashCode() + 3 * customerLastName.hashCode()
                + 23 * validUntil.hashCode() + (approved ? 0 : 1));
//                Objects.hash(super.hashCode(), validUntil, doctorId, drugId, customerId, approved);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                super.toString() +
                ", validUntil=" + validUntil +
                ", doctorId=" + doctorId +
                ", drug=" + drugName +
                ", customer=" + customerFirstName +
                " " + customerLastName +
                ", approved=" + approved +
                '}';
    }
}
