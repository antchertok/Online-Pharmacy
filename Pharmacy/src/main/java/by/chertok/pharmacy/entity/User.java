package by.chertok.pharmacy.entity;

public class User extends BaseEntity {

    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private String mail;
    private String role;
    private String speciality;
    private long doctorId;

    public User(long id) {
        super(id);
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(long doctorId) {
        this.doctorId = doctorId;
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) return false;
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return doctorId == user.doctorId
                && login == null ? user.login == null : login.equals(user.login)
                && password == null ? user.password == null : password.equals(user.password)
                && firstName == null ? user.firstName == null : firstName.equals(user.firstName)
                && lastName == null ? user.lastName == null : lastName.equals(user.lastName)
                && mail == null ? user.mail == null : mail.equals(user.mail)
                && role == null ? user.role == null : role.equals(user.role)
                && speciality == null ? user.speciality == null : speciality.equals(user.speciality);
    }

    @Override
    public int hashCode() {

        return (int)(31 * getId() + 3 * doctorId + login.hashCode()
                + password.hashCode() + firstName.hashCode() + lastName.hashCode()
                + mail.hashCode() + role.hashCode() + speciality.hashCode());
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" + super.toString()
                + "login='" + login + ", password='" + password
                + ", firstName='" + firstName + ", lastName='" + lastName
                + ", mail='" + mail + ", role='" + role + ", speciality='"
                + speciality + ", doctorId=" + doctorId + '}';
    }
}
