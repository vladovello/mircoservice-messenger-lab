package com.messenger.authandprofile.application.profile.query;

import an.awesome.pipelinr.Command;
import com.messenger.authandprofile.application.profile.dto.DiscreteParamDto;
import com.messenger.authandprofile.application.profile.dto.IntervalParamDto;
import com.messenger.authandprofile.application.profile.model.UserListDto;
import com.messenger.sharedlib.pagination.PageNumberPaginatedQuery;
import io.vavr.control.Either;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * @apiNote @see block shows exceptions may be thrown by this command
 * @see com.messenger.authandprofile.application.profile.exception.ConstraintViolationException
 * @see com.messenger.authandprofile.application.profile.exception.IntervalException
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class UserListQuery extends PageNumberPaginatedQuery implements Command<Either<Exception, UserListDto>> {
    private DiscreteParamDto<String> email;
    private DiscreteParamDto<String> login;
    private DiscreteParamDto<String> fullName;
    private DiscreteParamDto<String> phoneNumber;
    private IntervalParamDto<LocalDate> registrationDate;
    private IntervalParamDto<LocalDate> birthDate;
    private DiscreteParamDto<String> city;

    @Override
    public int getMaxPageSize() {
        return 50;
    }
}
