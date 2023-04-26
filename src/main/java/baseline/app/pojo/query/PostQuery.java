package baseline.app.pojo.query;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "PostQuery",description = "岗位查询体")
public class PostQuery {

    @ApiModelProperty("岗位id")
    private String id;

    @ApiModelProperty("岗位名称")
    private String name;

    @ApiModelProperty("技能")
    private String skill;

    @ApiModelProperty("项目id")
    private String projectId;

    @ApiModelProperty("项目名称")
    private String projectName;

    @ApiModelProperty("办公地点")
    private String address;

    @ApiModelProperty("客户Id")
    private String customerId;

    @ApiModelProperty("客户名称")
    private String customerName;

    @ApiModelProperty("岗位数量")
    private String number;

    @ApiModelProperty("计划满足日期")
    private String date;

    @ApiModelProperty("到岗最晚时间")
    private Date latestArrivalTime;

    @ApiModelProperty("岗位职责")
    private String position;

    @TableField("requirements")
    private String requirements;

    @ApiModelProperty("岗位需求人数")
    private String needPeopleNum;
}
