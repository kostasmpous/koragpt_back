package com.koragpt.koragpt.services;

import com.koragpt.koragpt.models.Contact;
import com.koragpt.koragpt.models.User;
import com.koragpt.koragpt.models.dtos.user.UserDTO;
import com.koragpt.koragpt.repositories.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    public Contact updateContact(User u, UserDTO body) {
        List<Contact> list = contactRepository.findAllByUserAndType(u, "BillTo");

        Contact c = list.isEmpty() ? null : list.get(0);
        if (c == null) {
            c = new Contact();
            c.setUser(u);
            c.setType("BillTo");
        }
        if (body.getAddress() != null)     c.setAddress(body.getAddress());
        if (body.getCity() != null)        c.setCity(body.getCity());
        if (body.getCountry() != null)     c.setCountry(body.getCountry());
        if (body.getPostalCode() != null)  c.setPostalcode(body.getPostalCode());
        if (body.getFirstName() != null)   c.setFirstName(body.getFirstName());
        if (body.getLastName() != null)   c.setLastName(body.getLastName());
        contactRepository.save(c);

        return c;
    }
}
