package com.moses33.habitof6.web.controller;

import org.springframework.data.domain.PageRequest;

public class BaseController {
    private static final Integer DEFAULT_PAGE_SIZE = 20;
    protected PageRequest getPageRequest(Integer pageNumber, Integer pageSize)
    {
        return PageRequest.of(pageNumber == null ? 0 : pageNumber, Math.min(pageSize == null ? DEFAULT_PAGE_SIZE : pageSize, DEFAULT_PAGE_SIZE));
    }
}
