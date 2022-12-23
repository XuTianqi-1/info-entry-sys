package baseline.sysmgmt.mapper;

import baseline.sysmgmt.pojo.query.UserQuery;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import baseline.sysmgmt.pojo.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author crelle
 * @since 2022-10-01 12:06:26
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    int updatePasswordById(@Param("password") String password, @Param("id") String id);

    List<User> manualPage(Page<User> page, UserQuery userQuery);
}
