package by.chertok.pharmacy.entity;

/**
 * Root class setting basic properties for entities
 */
public class BaseEntity {

    private long id;

    public BaseEntity(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseEntity that = (BaseEntity) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return 31 * (int)id;
    }

    @Override
    public String toString() {
        return  "id=" + id + ' ';
    }
}
