package baseline.app.mapper;

import baseline.app.pojo.entity.Post;
import baseline.app.pojo.query.PostQuery;
import baseline.app.pojo.vo.PostVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author crelle
 * @since 2022-11-09 10:06:28
 */
@Mapper
public interface PostMapper extends BaseMapper<Post> {
    Page<PostVo> manualPage(@Param("page") Page<PostQuery> page,@Param("param") PostQuery postQuery);
}
