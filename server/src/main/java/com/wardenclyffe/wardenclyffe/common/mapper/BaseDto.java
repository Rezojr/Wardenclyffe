package com.wardenclyffe.wardenclyffe.common.mapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseDto implements Serializable {
    private Long id;

    public static BaseDto from(BaseDto other) {
        return from(other.id);
    }

    public static BaseDto from(Long id) {
        return new BaseDto(id);
    }

    public BaseDto toBaseDto() {
        return new BaseDto(id);
    }
}
