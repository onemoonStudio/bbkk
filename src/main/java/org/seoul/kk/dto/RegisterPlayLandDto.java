package org.seoul.kk.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterPlayLandDto {

    @NotNull(message = "travelerId is null")
    private Long travelerId;
    @NotNull(message = "title is null")
    private String title;
    @NotNull(message = "content is null")
    private String content;
    @NotNull(message = "position is null")
    private String position;
    @NotNull(message = "images are null")
    private String images;

    public static RegisterPlayLandDto newInstance(Long travelerId, String title, String content, String position, String images) {
        return new RegisterPlayLandDto(travelerId, title, content, position, images);
    }

}
