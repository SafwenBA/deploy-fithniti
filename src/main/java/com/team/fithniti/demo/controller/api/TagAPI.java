package com.team.fithniti.demo.controller.api;

import com.team.fithniti.demo.model.Tag;

import java.util.List;

public interface TagAPI {
    List<Tag> getAllTags()  ;
    Tag addTag(Tag tag) ;
}
