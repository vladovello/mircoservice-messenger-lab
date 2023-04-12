package com.messenger.authandprofile.application.profile.model.parameter;

import com.messenger.authandprofile.application.profile.dto.DiscreteParamDto;
import com.messenger.authandprofile.application.profile.dto.IntervalParamDto;
import com.messenger.authandprofile.application.profile.model.parameter.param.DiscreteParam;
import com.messenger.authandprofile.application.profile.model.parameter.param.IntervalParam;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;

@Data
public class UserFilterParams {
    private DiscreteParam<String> email;
    private DiscreteParam<String> login;
    private DiscreteParam<String> fullName;
    private DiscreteParam<String> phoneNumber;
    private IntervalParam<LocalDate> registrationDate;
    private IntervalParam<LocalDate> birthDate;
    private DiscreteParam<String> city;

    public static UserFilterParamsBuilder builder() {
        return new UserFilterParamsBuilder();
    }

    public static class UserFilterParamsBuilder {
        private final UserFilterParams params;

        public UserFilterParamsBuilder() {
            params = new UserFilterParams();
        }

        public UserFilterParamsBuilder withEmail(@NonNull DiscreteParamDto<String> param) {
            params.email = new DiscreteParam<>(param.getValue(), param.getSortingOrder());
            return this;
        }

        public UserFilterParamsBuilder withLogin(@NonNull DiscreteParamDto<String> param) {
            params.email = new DiscreteParam<>(param.getValue(), param.getSortingOrder());
            return this;
        }

        public UserFilterParamsBuilder withFullName(@NonNull DiscreteParamDto<String> param) {
            params.email = new DiscreteParam<>(param.getValue(), param.getSortingOrder());
            return this;
        }

        public UserFilterParamsBuilder withPhoneNumber(@NonNull DiscreteParamDto<String> param) {
            params.email = new DiscreteParam<>(param.getValue(), param.getSortingOrder());
            return this;
        }

        public UserFilterParamsBuilder withRegistrationDate(@NonNull IntervalParamDto<LocalDate> param) {
            this.params.registrationDate = new IntervalParam<>(param.getFrom(), param.getTo(), param.getSortingOrder());
            return this;
        }

        public UserFilterParamsBuilder withBirthDate(@NonNull IntervalParamDto<LocalDate> param) {
            this.params.birthDate = new IntervalParam<>(param.getFrom(), param.getTo(), param.getSortingOrder());
            return this;
        }

        public UserFilterParamsBuilder withCity(@NonNull DiscreteParamDto<String> param) {
            params.email = new DiscreteParam<>(param.getValue(), param.getSortingOrder());
            return this;
        }

        public UserFilterParams build() {
            return params;
        }
    }
}