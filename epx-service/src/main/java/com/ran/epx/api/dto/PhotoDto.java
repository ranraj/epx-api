package com.ran.epx.api.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PhotoDto {
    String tripId;
    String photoBase64Encoded;
}
