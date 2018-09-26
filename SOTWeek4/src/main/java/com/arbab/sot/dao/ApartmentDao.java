package com.arbab.sot.dao;

import com.arbab.sot.models.Apartment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ApartmentDao {
    private List<Apartment> apartmentList;

    public ApartmentDao() {
        apartmentList = new ArrayList<Apartment>() {{
            add(new Apartment(1, 150, "Eindhoven"));
            add(new Apartment(2, 200, "Amsterdam"));
            add(new Apartment(3, 100, "Amsterdam"));
            add(new Apartment(4, 120, "Eindhoven"));
            add(new Apartment(5, 400, "Eindhoven"));
            add(new Apartment(6, 136, "Rotterdam"));
        }};
    }

    public List<Apartment> getApartmentList() {
        return apartmentList;
    }

    public void createApartment(Apartment apartment) {
        apartmentList.add(apartment);
    }

    public Apartment getApartmentById(int id) {
        for (Apartment apartment : apartmentList) {
            if (apartment.getId() == id) {
                return apartment;
            }
        }
        return null;
    }

    public void updateApartmentAvailability(Apartment apartment, boolean isAvailable) {
        apartment.setAvailable(isAvailable);
    }
}
