package com.moses33.habitof6.web.controller;

import org.springframework.data.domain.PageRequest;

public class BaseController {

    public static final String API_VERSION = "1";
    public static final String API_BASE_PATH = "/api/v"+API_VERSION;
    private static final Integer DEFAULT_PAGE_SIZE = 20;
    protected PageRequest getPageRequest(Integer pageNumber, Integer pageSize)
    {
        return PageRequest.of(pageNumber == null ? 0 : pageNumber, Math.min(pageSize == null ? DEFAULT_PAGE_SIZE : pageSize, DEFAULT_PAGE_SIZE));
    }
}
