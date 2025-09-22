package com.koragpt.koragpt.models.dtos.user;

import com.koragpt.koragpt.models.Contact;
import com.koragpt.koragpt.models.User;
import com.koragpt.koragpt.repositories.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String country;
    private String postalCode;

    public static UserDTO from(User user, Contact contact) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirst_name());
        dto.setLastName(user.getLast_name());

        if (contact != null) {
            dto.setAddress(checkContent(contact.getAddress()));
            dto.setCity(checkContent(contact.getCity()));
            dto.setCountry(checkContent(contact.getCountry()));
            dto.setPostalCode(checkContent(contact.getPostalcode()));
        } else {
            dto.setAddress("");
            dto.setCity("");
            dto.setCountry("");
            dto.setPostalCode("");
        }
        return dto;
    }

    private static String checkContent(String s) { return s == null ? "" : s; }


}
