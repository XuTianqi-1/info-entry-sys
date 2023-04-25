package baseline.app.pojo.entity;

import baseline.common.pojo.entity.BaseDo;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 客户表
 * </p>
 *
 * @author crelle
 * @since 2023-04-23 02:35:38
 */
@Getter
@Setter
@TableName("t_customer")
@ApiModel(value = "Customer对象", description = "客户表")
public class Customer extends BaseDo {

    @ApiModelProperty("客户编号")
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    @NotBlank(groups = {DELETE.class, PUT.class})
    private String id;

    @ApiModelProperty(value = "客户名称",required = true)
    @TableField("name")
    @NotBlank(groups = POST.class)
    private String name;

    @ApiModelProperty("地域ID")
    @TableField("region_id")
    private String regionId;

    @ApiModelProperty(value = "办公地点",required = true)
    @TableField("address")
    @NotBlank(groups = POST.class)
    private String address;

    @ApiModelProperty("负责人ID")
    @TableField("user_id")
    private String userId;

    @ApiModelProperty("联系电话")
    @TableField("cell_phone")
    private String cellPhone;

    @ApiModelProperty("电子邮箱")
    @TableField("email")
    private String email;

    @ApiModelProperty("客户介绍")
    @TableField("introduce")
    private String introduce;
}
