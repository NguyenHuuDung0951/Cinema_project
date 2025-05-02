package entity;

import java.time.LocalDate;
import java.util.Objects;

public class Employee {
    private String employeeID;
    private String fullName;
    private boolean gender;
    private LocalDate dateOfBirth;
    private LocalDate dateJoin;

    public LocalDate getDateJoin() {
        return dateJoin;
    }

    public void setDateJoin(LocalDate dateJoin) {
        this.dateJoin = dateJoin;
    }
    private String phoneNumber;
    private String email;
    private Account account;    

    public Employee(String employeeID) {
        this.employeeID = employeeID;
    }
 
    public Employee(String employeeID, String fullName, boolean gender,
                    LocalDate dateOfBirth, LocalDate dateJoin, String phoneNumber,
                    String email, Account account) {
        this.employeeID = employeeID;
        this.fullName = fullName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.dateJoin = dateJoin;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.account = account;
    }

    public Employee(String eid, String name) {
        this.employeeID = eid;
        this.fullName = name;
    }

    public String getEmployeeID() { return employeeID; }
    public void setEmployeeID(String employeeID) { this.employeeID = employeeID; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public boolean isGender() { return gender; }
    public void setGender(boolean gender) { this.gender = gender; }

    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Account getAccount() { return account; }
    public void setAccount(Account account) { this.account = account; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;
        Employee other = (Employee) o;
        return Objects.equals(employeeID, other.employeeID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeID);
    }
}
