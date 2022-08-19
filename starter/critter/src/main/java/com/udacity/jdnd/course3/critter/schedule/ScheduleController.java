package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetService;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeService;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    ScheduleService scheduleService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    PetService petService;
    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        return convertScheduleToScheduleDTO(scheduleService.save(convertScheduleDTOToSchedule(scheduleDTO)));
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
         List<Schedule> schedules= scheduleService.findAllSchedules();
         List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
         for (Schedule schedule: schedules) {
             scheduleDTOS.add(convertScheduleToScheduleDTO(schedule));
         }
         return scheduleDTOS;
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<Schedule> schedules = scheduleService.findByPetId(petId);
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        for (Schedule schedule: schedules) {
            scheduleDTOS.add(convertScheduleToScheduleDTO(schedule));
        }
        return scheduleDTOS;
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<Schedule> schedules = scheduleService.findByEmployeeId(employeeId);
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        for (Schedule schedule: schedules) {
            scheduleDTOS.add(convertScheduleToScheduleDTO(schedule));
        }
        return scheduleDTOS;
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<Schedule> schedules = scheduleService.getScheduleForCustomer(customerId);
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        for (Schedule schedule: schedules) {
            scheduleDTOS.add(convertScheduleToScheduleDTO(schedule));
        }
        return scheduleDTOS;
    }

    /**
     * convert DTO and entities
     */
    private ScheduleDTO convertScheduleToScheduleDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        List<Long> employIds = new ArrayList<>();
        List<Long> petIds = new ArrayList<>();

        BeanUtils.copyProperties(schedule, scheduleDTO);

        if (schedule.getEmployees() != null && schedule.getEmployees().size() > 0) {
            schedule.getEmployees().forEach(employee -> {
                employIds.add(employee.getId());
            });
        }
        scheduleDTO.setEmployeeIds(employIds);

        if(schedule.getPets() != null && schedule.getPets().size() > 0) {
            schedule.getPets().forEach(pet -> {
                petIds.add(pet.getId());
            });
        }
        scheduleDTO.setPetIds(petIds);

        if (schedule.getSkills() != null && schedule.getSkills().size() > 0 ) {
            Set<EmployeeSkill> skills = new HashSet<>(schedule.getSkills());
            scheduleDTO.setActivities(skills);
        }

        return scheduleDTO;
    }

    private Schedule convertScheduleDTOToSchedule(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        List<Employee> employees = new ArrayList<>();
        List<Pet> pets = new ArrayList<>();

        BeanUtils.copyProperties(scheduleDTO, schedule);

        if (scheduleDTO.getEmployeeIds() != null && scheduleDTO.getEmployeeIds().size() > 0) {
            scheduleDTO.getEmployeeIds().forEach(employeeId -> {
                Employee employee = employeeService.getEmployeeById(employeeId);
                employees.add(employee);
            });
        }
        schedule.setEmployees(employees);

        if (scheduleDTO.getPetIds() != null && scheduleDTO.getPetIds().size() > 0) {
            scheduleDTO.getPetIds().forEach(pet -> {
                Pet pet1 = petService.getPetById(pet);
                pets.add(pet1);
            });
        }
        schedule.setPets(pets);

        if (scheduleDTO.getActivities() != null && scheduleDTO.getActivities().size() > 0) {
            Set<EmployeeSkill> skills = new HashSet<>(scheduleDTO.getActivities());
            schedule.setSkills(skills);
        }
        return schedule;
    }

}
