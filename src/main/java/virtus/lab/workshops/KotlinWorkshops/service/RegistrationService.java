package virtus.lab.workshops.KotlinWorkshops.service;

import virtus.lab.workshops.KotlinWorkshops.model.UserDto;

public interface RegistrationService {
    void createAccount(UserDto userDto);
}
