package com.messenger.authandprofile.application.profile.query;

import an.awesome.pipelinr.Command;
import com.messenger.authandprofile.application.profile.dto.DiscreteParamDto;
import com.messenger.authandprofile.application.profile.dto.IntervalParamDto;
import com.messenger.authandprofile.application.profile.dto.UserDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class UserListQuery implements Command<List<UserDto>> {
    private int pageSize;
    private int pageNumber;

    private DiscreteParamDto<String> email;
    private DiscreteParamDto<String> login;
    private DiscreteParamDto<String> fullName;
    private DiscreteParamDto<String> phoneNumber;
    private IntervalParamDto<LocalDate> registrationDate;
    private IntervalParamDto<LocalDate> birthDate;
    private DiscreteParamDto<String> city;
}
