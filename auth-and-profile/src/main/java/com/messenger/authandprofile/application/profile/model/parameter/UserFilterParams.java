package com.messenger.authandprofile.application.profile.model.parameter;

import com.google.common.collect.Range;
import com.messenger.authandprofile.application.profile.dto.DiscreteParamDto;
import com.messenger.authandprofile.application.profile.dto.IntervalParamDto;
import com.messenger.authandprofile.application.profile.model.parameter.param.DiscreteParam;
import com.messenger.authandprofile.application.profile.model.parameter.param.IntervalParam;
import lombok.*;
import org.jetbrains.annotations.Contract;

import java.time.LocalDate;

@EqualsAndHashCode
@ToString
@RequiredArgsConstructor
@Getter
public class UserFilterParams {
    private DiscreteParam<String> login;
    private DiscreteParam<String> fullName;
    private DiscreteParam<String> phoneNumber;
    private IntervalParam<LocalDate> registrationDate;
    private IntervalParam<LocalDate> birthDate;
    private DiscreteParam<String> city;

    public String getLoginValue() {
        return getLogin().getValue();
    }

    public String getFullNameValue() {
        return getFullName().getValue();
    }

    public String getPhoneNumberValue() {
        return getPhoneNumber().getValue();
    }

    public String getCityValue() {
        return getCity().getValue();
    }

    public Range<LocalDate> getRegistrationDateInterval() {
        return getRegistrationDate().getInterval();
    }

    public Range<LocalDate> getBirthDateInterval() {
        return getBirthDate().getInterval();
    }

    @Contract(" -> new")
    public static @NonNull UserFilterParamsBuilder builder() {
        return new UserFilterParamsBuilder();
    }

    public static class UserFilterParamsBuilder {
        private final UserFilterParams params;

        public UserFilterParamsBuilder() {
            params = new UserFilterParams();
        }

        public UserFilterParamsBuilder withLogin(DiscreteParamDto<String> param) {
            params.login = param == null ? DiscreteParam.createDefault() : new DiscreteParam<>(
                    param.getValue(),
                    param.getSortingOrder()
            );
            return this;
        }

        public UserFilterParamsBuilder withFullName(DiscreteParamDto<String> param) {
            params.fullName = param == null ? DiscreteParam.createDefault() : new DiscreteParam<>(
                    param.getValue(),
                    param.getSortingOrder()
            );
            return this;
        }

        public UserFilterParamsBuilder withPhoneNumber(DiscreteParamDto<String> param) {
            params.phoneNumber = param == null ? DiscreteParam.createDefault() : new DiscreteParam<>(
                    param.getValue(),
                    param.getSortingOrder()
            );
            return this;
        }

        public UserFilterParamsBuilder withRegistrationDate(IntervalParamDto<LocalDate> param) {
            params.registrationDate = param == null ? IntervalParam.createDefault() : new IntervalParam<>(
                    param.getFrom(),
                    param.getTo(),
                    param.getSortingOrder()
            );
            return this;
        }

        public UserFilterParamsBuilder withBirthDate(IntervalParamDto<LocalDate> param) {
            params.birthDate = param == null ? IntervalParam.createDefault() : new IntervalParam<>(
                    param.getFrom(),
                    param.getTo(),
                    param.getSortingOrder()
            );
            return this;
        }

        public UserFilterParamsBuilder withCity(DiscreteParamDto<String> param) {
            params.city = param == null ? DiscreteParam.createDefault() : new DiscreteParam<>(
                    param.getValue(),
                    param.getSortingOrder()
            );
            return this;
        }

        public UserFilterParams build() {
            return params;
        }
    }
}
