package com.team.fithniti.demo.service.impl;

import com.team.fithniti.demo.model.Tag;
import com.team.fithniti.demo.repository.TagRepo;
import com.team.fithniti.demo.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private  TagRepo tagRepo ;

    @Override
    public List<Tag> getAllTags() {
        return tagRepo.findAll();
    }

    @Override
    public Tag addTag(Tag tag) {
        return tagRepo.save(tag);
    }
}
