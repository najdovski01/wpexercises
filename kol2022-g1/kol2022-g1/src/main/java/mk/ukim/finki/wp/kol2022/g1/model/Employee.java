package mk.ukim.finki.wp.kol2022.g1.model;


import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
public class Employee {

    public Employee() {
    }

    public Employee(String name, String email, String password, EmployeeType type, List<Skill> skills, LocalDate employmentDate) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.type = type;
        this.skills = skills;
        this.employmentDate = employmentDate;
        this.setEmploymentYears(employmentDate.getYear());
    }

    @Id
    @GeneratedValue
    private Long id;

    @DateTimeFormat(fallbackPatterns = "dd-MM-yyyy")
    private LocalDate employmentDate;

    private String name;

    private String email;

    private String password;

    private EmployeeType type;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Skill> skills;

    private int yearsOfService;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public EmployeeType getType() {
        return type;
    }

    public void setType(EmployeeType type) {
        this.type = type;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public LocalDate getEmploymentDate() {
        return employmentDate;
    }

    public void setEmploymentDate(LocalDate employmentDate) {
        this.employmentDate = employmentDate;
    }

    public Integer getEmploymentYears(int years){
        Date d=new Date();
        int year=d.getYear();
        int currentYear=year+1900;
        return currentYear-years;
    }

    public void setEmploymentYears(int years){
        this.yearsOfService = getEmploymentYears(years);
    }
}
