package cn.itcast.mapper;

import cn.itcast.entify.QueryPageBean;
import cn.itcast.pojo.CheckItem;
import com.github.pagehelper.Page;

import java.util.List;

/**
 * @PackageName: cn.itcast.mapper
 * @ClassName: CheckItemMapper
 * @Author: dongxiyaohui
 * @Date: 2019/11/29 19:03
 * @Description: //TODO
 */
public interface CheckItemMapper {
    void add(CheckItem checkItem);

    Page<CheckItem> findPage(String queryString);

    Long findCheckItem_Checkgroup(Integer id);

    void deleteCheckItem(int id);

    CheckItem findCheckItemById(Integer id);

    void editCheckItemByid(CheckItem checkItem);

    List<CheckItem> findAll();
}
