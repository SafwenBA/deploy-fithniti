package com.team.fithniti.demo.dto.response;

import com.team.fithniti.demo.exception.InvalidResource;
import com.team.fithniti.demo.model.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class TagDto {
    private Long id;
    private String tagName;
    public static TagDto fromEntity(Tag tag){
        if (tag != null)
            throw new InvalidResource(null,"INTERNAL ERROR","Can't map null Entity");
        return new TagDto(tag.getId(), tag.getTagName());
    }
}
