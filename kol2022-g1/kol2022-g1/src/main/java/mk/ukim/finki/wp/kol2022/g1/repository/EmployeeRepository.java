package mk.ukim.finki.wp.kol2022.g1.repository;

import mk.ukim.finki.wp.kol2022.g1.model.Employee;
import mk.ukim.finki.wp.kol2022.g1.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findAllBySkills(Skill skill);


    List<Employee> findAllBySkillsAndYearsOfServiceGreaterThan(Skill skill, int years);

    List<Employee> findAllByYearsOfServiceGreaterThan(Integer yearsOfService);

//    @Override
//    List<Employee> findAllBySkill();
}
