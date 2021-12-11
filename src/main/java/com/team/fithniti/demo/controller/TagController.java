package com.team.fithniti.demo.controller;

import com.team.fithniti.demo.controller.api.TagAPI;
import com.team.fithniti.demo.model.Tag;
import com.team.fithniti.demo.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController implements TagAPI {

    @Autowired
    private TagService tagService ;

    @Override
    public List<Tag> getAllTags() {
        return tagService.getAllTags();
    }

    @Override
    public Tag addTag(Tag tag) {
        return tagService.addTag(tag);
    }
}
