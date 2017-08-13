package com.tony.demo.microservice.mud.services.user.common.utils;

import com.github.pagehelper.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;

import java.util.ArrayList;
import java.util.List;

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
    public static <S, T> List<T> convert(List<S> sources, Class<T> targetClazz) throws IllegalAccessException, InstantiationException {
        List<T> targets = new ArrayList<>();
        for (S source : sources) {
            T target = targetClazz.newInstance();
            BeanUtils.copyProperties(source, target);
            targets.add(target);
        }
        return targets;
    }

    public static <S, T> List<T> convert(List<S> sources, Converter<S, T> converter) throws IllegalAccessException, InstantiationException {
        List<T> targets = new ArrayList<>();
        for (S source : sources)
            targets.add(converter.convert(source));
        return targets;
    }

}
