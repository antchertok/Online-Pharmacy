package by.chertok.pharmacy.entity;

/**
 * Class representing Drug entity
 */
public class Drug extends BaseEntity {

    private String name;
    private int dose;
    private int prescription;
    private double price;

    public Drug(long id) {
        super(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDose() {
        return dose;
    }

    public void setDose(int dose) {
        this.dose = dose;
    }

    public int getPrescription() {
        return prescription;
    }

    public void setPrescription(int prescription) {
        this.prescription = prescription;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) return false;
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Drug drug = (Drug) o;
        return prescription == drug.prescription &&
                dose == drug.dose &&
                name == null ? drug.name == null : name.equals(drug.name);
    }

    @Override
    public int hashCode() {

        return (int) (23 * getId() + 19 * dose + 37 * prescription + name.hashCode());
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "name='" + name + '\'' +
                ", dose=" + dose +
                ", prescription=" + prescription +
                '}';
    }
}
