package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.repository.AddressRepository;

@Service
public class AddressService {
    
    @Autowired
    private AddressRepository addressRepo;
    
    /**
     * Deletes an address with the specified ID.
     * 
     * @param id The ID of the address to be deleted.
     */
    public void deleteAddress(int id) {
        // Delete the address by its ID
        addressRepo.deleteById(id);
    }
}
