package baseline.sysmgmt.pojo.entity;

import baseline.common.pojo.BaseDo;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author crelle
 * @since 2022-12-06 05:31:13
 */
@Getter
@Setter
@TableName("t_role")
@ApiModel(value = "Role对象", description = "角色表")
public class Role extends BaseDo {

    @ApiModelProperty("角色id")
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty("角色英文名称")
    @TableField("name")
    private String name;

    @ApiModelProperty("角色中文名称")
    @TableField("name_zh")
    private String nameZh;

    @TableField(exist = false)
    private List<Menu> menus;


}
