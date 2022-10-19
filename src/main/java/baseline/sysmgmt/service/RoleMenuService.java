package baseline.sysmgmt.service;

import baseline.sysmgmt.common.ResponseResult;
import baseline.sysmgmt.model.entity.Role;
import baseline.sysmgmt.model.entity.RoleMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author crelle
 * @since 2022-10-01 12:06:26
 */
public interface RoleMenuService extends IService<RoleMenu>, BaseService<RoleMenu> {
    ResponseResult<Role> queryMenu(Role role);

    void updateRoleMenu(List<Role> roles);

}
