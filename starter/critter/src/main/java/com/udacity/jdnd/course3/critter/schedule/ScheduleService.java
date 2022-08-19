package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleService {
    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    PetRepository petRepository;

    public Schedule save(Schedule schedule) {return scheduleRepository.save(schedule);}
    public List<Schedule> findAllSchedules() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> findByPetId(Long id) {
        return scheduleRepository.findByPets_Id(id);
    }

    public List<Schedule> findByEmployeeId(Long id) {
        return scheduleRepository.findByEmployees_Id(id);
    }

    public List<Schedule> getScheduleForPet(Long id) {
        return scheduleRepository.findByPets_Id(id);
    }

    public List<Schedule> getScheduleForCustomer(Long customerId) {
        List<Pet> pets = petRepository.findByCustomer_id(customerId);
        List<Schedule> schedules= new ArrayList<>();
        for (Pet pet: pets) {
            List<Schedule> petsSchedules = getScheduleForPet(pet.getId());
            schedules.addAll(petsSchedules);
        }
        return schedules;
    }
}
