package baseline.sysmgmt.service;

import baseline.sysmgmt.service.impl.BaseService;
import baseline.common.pojo.vo.ResponseResult;
import baseline.sysmgmt.pojo.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author crelle
 * @since 2022-10-01 12:06:26
 */
public interface MenuService extends IService<Menu>, BaseService<Menu> {
    //根据菜单名称查询菜单信息
    Menu queryByName(String menuName);

    //修改菜单目录
    ResponseResult<String> updateParentMenu(Set<Menu> menus);

    //查询所有菜单 分级
    ResponseResult<List<Menu>> queryAllMenus();
}
