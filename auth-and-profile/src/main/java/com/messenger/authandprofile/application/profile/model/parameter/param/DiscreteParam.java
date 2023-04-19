package com.messenger.authandprofile.application.profile.model.parameter.param;

import com.messenger.authandprofile.application.profile.model.parameter.field.Field;
import com.messenger.authandprofile.application.profile.model.parameter.order.SortingOrder;
import lombok.Setter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

// INFO: all Param classes may be extended from Param interface with createDefault() (and maybe some more) method.
@Setter
public class DiscreteParam<T extends Serializable> extends Field<T> {
    public DiscreteParam(T value, SortingOrder filterType) {
        super(value, filterType);
    }

    protected DiscreteParam() {
        super();
    }

    @Contract(" -> new")
    public static <T extends Serializable> @NotNull DiscreteParam<T> createDefault() {
        return new DiscreteParam<>();
    }
}
