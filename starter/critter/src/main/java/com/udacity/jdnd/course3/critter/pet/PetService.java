package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PetService {
    @Autowired
    PetRepository petRepository;

    @Autowired
    CustomerService customerService;

    public Pet save(Pet pet) {
        Pet savedPet = petRepository.save(pet);
        customerService.addPetToCustomer(savedPet, savedPet.getCustomer());
        return savedPet;
    }

    public Pet getPetById(Long id) {return petRepository.getOne(id);}

    public List<Pet>  findAllPets() {return petRepository.findAll();}

    public List<Pet> findByCustomerId(Long id) {return petRepository.findByCustomer_id(id);}

    public List<Pet> findByCustomer(Customer customer) {return petRepository.findByCustomer(customer);}
}
