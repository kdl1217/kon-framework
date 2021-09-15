package com.kon.framework.mybatis.util;

import com.kon.framework.core.share.Pagination;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 分页处理器
 *
 * @author kon, created on 2021-05-31T14:23.
 * @version 1.0.0-SNAPSHOT
 */
public class PaginationProcessor {

    /**
     * 获取分页对象
     * @param pageNum       当前页数
     * @param pageSize      每页条数
     * @return  分页对象
     */
    public static RowBounds getRowBounds(Integer pageNum, Integer pageSize) {
        return new RowBounds((pageNum - 1) * pageSize, pageSize);
    }

    /**
     * 获取结果
     * @param pageNum       当前页数
     * @param pageSize      每页条数
     * @param totalSupplier 总数函数
     * @param dataSupplier  数据
     * @param <T>           范型
     * @return  结果
     */
    public static <T> Pagination<T> result(Integer pageNum, Integer pageSize,
                                           Supplier<Integer> totalSupplier, Supplier<List<T>> dataSupplier) {
        Pagination<T> pagination = new Pagination<>(pageNum, pageSize);
        long total = totalSupplier.get();
        if (total > 0) {
            pagination.setTotalData(total);
            pagination.setTotalPage(0 == total % pageSize ? total / pageSize : total / pageSize + 1);
            pagination.setList(dataSupplier.get());
        }
        return pagination;
    }

    /**
     * 获取结果
     * @param pageNum       当前页数
     * @param pageSize      每页条数
     * @param total         总数
     * @param dataList      数据
     * @param <T>           范型
     * @return  结果
     */
    public static <T> Pagination<T> result(Integer pageNum, Integer pageSize,
                                           long total, List<T> dataList) {
        Pagination<T> pagination = new Pagination<>(pageNum, pageSize);
        pagination.setTotalData(total);
        pagination.setTotalPage(0 == total % pageSize ? total / pageSize : total / pageSize + 1);
        pagination.setList(dataList);
        return pagination;
    }

    /**
     * 获取结果
     * @param pageNum       当前页数
     * @param pageSize      每页条数
     * @param totalSupplier 总数函数
     * @param dataFunction  数据
     * @param <T>   范型
     * @return  结果
     */
    public static <T> Pagination<T> result(Integer pageNum, Integer pageSize,
                                           Supplier<Integer> totalSupplier, Function<RowBounds, List<T>> dataFunction) {
        Pagination<T> pagination = new Pagination<>(pageNum, pageSize);
        long total = totalSupplier.get();
        if (total > 0) {
            pagination.setTotalData(total);
            pagination.setTotalPage(0 == total % pageSize ? total / pageSize : total / pageSize + 1);
            pagination.setList(dataFunction.apply(getRowBounds(pageNum, pageSize)));
        }
        return pagination;
    }
}
