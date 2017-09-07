package com.tony.demo.microservice.mud.common.utils;

import com.github.pagehelper.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Convert
 * <p>
 * Created by Tony on 13/02/2017.
 */
public final class ConvertUtils {

    /**
     * page number offset
     */
    private final static int PAGE_NUMBER_OFFSET = 1;

    /**
     * Convert page object from spring to github.
     *
     * @param springPage spring page object
     * @param <T>        page type
     * @return github page object
     */
    public static <T> Page<T> convert(org.springframework.data.domain.Page<T> springPage) {
        Page<T> gitHubPage = new Page<>();
        gitHubPage.addAll(springPage.getContent());
        gitHubPage.setPageNum(springPage.getNumber() + PAGE_NUMBER_OFFSET);
        gitHubPage.setPageSize(springPage.getSize());
        gitHubPage.setPages(springPage.getTotalPages());
        gitHubPage.setTotal(springPage.getTotalElements());
        return gitHubPage;
    }

    public static <S, T> T convert(S sources, final Class<T> targetClazz) throws IllegalAccessException, InstantiationException {
        T t = targetClazz.newInstance();
        if (sources != null)
            BeanUtils.copyProperties(sources, t);
        return t;
    }

    /**
     * Convert list object from entity to dto.
     *
     * @param sources
     * @param targetClazz
     * @param <S>
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static <S, T> List<T> convert(List<S> sources, final Class<T> targetClazz) throws IllegalAccessException, InstantiationException {
        if (sources == null || sources.size() == 0)
            return new ArrayList<>();
        return sources.stream().map(source -> {
            try {
                T target = targetClazz.newInstance();
                BeanUtils.copyProperties(source, target);
                return target;
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
    }

    public static <S, T> List<T> convert(List<S> sources, final Converter<S, T> converter) throws IllegalAccessException, InstantiationException {
        if (sources == null || sources.size() == 0)
            return new ArrayList<>();
        return sources.stream().map(converter::convert).collect(Collectors.toList());
    }

}
