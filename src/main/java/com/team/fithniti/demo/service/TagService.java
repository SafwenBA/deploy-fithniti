package com.team.fithniti.demo.service;

import com.team.fithniti.demo.model.Tag;

import java.util.List;

public interface TagService {
    List<Tag> getAllTags()  ;
    Tag addTag(Tag tag) ;
}
