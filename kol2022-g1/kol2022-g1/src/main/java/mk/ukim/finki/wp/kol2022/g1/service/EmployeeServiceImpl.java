package mk.ukim.finki.wp.kol2022.g1.service;

import mk.ukim.finki.wp.kol2022.g1.model.Employee;
import mk.ukim.finki.wp.kol2022.g1.model.EmployeeType;
import mk.ukim.finki.wp.kol2022.g1.model.Skill;
import mk.ukim.finki.wp.kol2022.g1.model.exceptions.InvalidEmployeeIdException;
import mk.ukim.finki.wp.kol2022.g1.repository.EmployeeRepository;
import mk.ukim.finki.wp.kol2022.g1.repository.SkillRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final SkillRepository skillRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder, SkillRepository skillRepository) {
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
        this.skillRepository = skillRepository;
    }


    @Override
    public List<Employee> listAll() {
        return this.employeeRepository.findAll();
    }

    @Override
    public Employee findById(Long id) {
        return this.employeeRepository.findById(id)
                .orElseThrow(InvalidEmployeeIdException::new);
    }

    @Override
    public Employee create(String name, String email, String password, EmployeeType type, List<Long> skillId, LocalDate employmentDate) {
        String pass = this.passwordEncoder.encode(password);
        List<Skill> skills = this.skillRepository.findAllById(skillId);

        Employee employee = new Employee(name,email,pass,type,skills,employmentDate);
        return this.employeeRepository.save(employee);
    }

    @Override
    public Employee update(Long id, String name, String email, String password, EmployeeType type, List<Long> skillId, LocalDate employmentDate) {
        Employee employee = this.findById(id);
        String pass = this.passwordEncoder.encode(password);
        List<Skill> skills = this.skillRepository.findAllById(skillId);
        employee.setName(name);
        employee.setEmail(email);
        employee.setPassword(pass);
        employee.setType(type);
        employee.setSkills(skills);
        employee.setEmploymentDate(employmentDate);
        return this.employeeRepository.save(employee);
    }

    @Override
    public Employee delete(Long id) {
        Employee employee = this.findById(id);
        this.employeeRepository.delete(employee);
        return employee;
    }

    @Override
    public List<Employee> filter(Long skillId, Integer yearsOfService) {


        if(skillId !=null && yearsOfService !=null){
            Skill skill = this.skillRepository.findById(skillId).orElse(null);
            return this.employeeRepository.findAllBySkillsAndYearsOfServiceGreaterThan(skill, yearsOfService);
        }else if(skillId!=null){
            Skill skill = this.skillRepository.findById(skillId).orElse(null);
            return this.employeeRepository.findAllBySkills(skill);
        }else if(yearsOfService!=null){
            return this.employeeRepository.findAllByYearsOfServiceGreaterThan(yearsOfService);
        }else return this.employeeRepository.findAll();
    }

    public List<EmployeeType> getAllTypes(){
        return Stream.of(EmployeeType.values()).collect(Collectors.toList());
    }
}
