package baseline.app.controller;


import baseline.app.pojo.entity.Post;
import baseline.app.service.PostService;
import baseline.common.baseBean.BaseController;
import baseline.common.pojo.vo.ResponseResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author crelle
 * @since 2022-11-09 10:06:28
 */
@Api(tags = "岗位服务")
@RestController
@RequestMapping("/app/post")
public class PostController implements BaseController<Post> {
    @Autowired
    private PostService postService;

    @ApiOperation(value = "创建")
    @ApiParam(required = true, name = "", value = "入参")
    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @Override
    public ResponseResult<Post> create(Post object) {
        ResponseResult result = new ResponseResult();
        boolean isSuccess = postService.create(object);
        if (!isSuccess) {
            result.buildFail("创建失败！");
        }
        return result;
    }

    @ApiOperation(value = "批量创建")
    @ApiParam(required = true, name = "", value = "入参")
    @RequestMapping(value = "/creates", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @Override
    public ResponseResult<Post> creates(List<Post> objects) {
        ResponseResult result = new ResponseResult();
        boolean isSuccess = postService.create(objects);
        if (!isSuccess) {
            result.buildFail("批量创建失败！");
        }
        return result;
    }

    @ApiOperation(value = "根据ID删除")
    @ApiParam(required = true, name = "id", value = "入参")
    @RequestMapping(value = "/deleteById/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @Override
    public ResponseResult<String> deleteById(String id) {
        ResponseResult result = new ResponseResult();
        postService.deleteById(id);
        return result;
    }

    @ApiOperation(value = "根据ID批量删除")
    @ApiParam(required = true, name = "id", value = "入参")
    @RequestMapping(value = "/deleteByIds", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @Override
    public ResponseResult<String> deleteByIds(List<String> ids) {
        ResponseResult result = new ResponseResult();
        postService.deleteByIds(ids);
        return result;
    }

    @ApiOperation(value = "根据ID更新")
    @ApiParam(required = true, name = "id", value = "入参")
    @RequestMapping(value = "/updateById", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @Override
    public ResponseResult<String> updateById(Post object) {
        ResponseResult result = new ResponseResult();
        boolean isSuccess = postService.update(object);
        if (!isSuccess) {
            result.buildFail("更新失败！");
        }
        return result;
    }

    @ApiOperation(value = "分页查询")
    @ApiParam(required = true, name = "", value = "入参")
    @RequestMapping(value = "/pageByCondition", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @Override
    public ResponseResult<Page<Post>> pageByCondition(Page<Post> pageBean) {
        ResponseResult result = new ResponseResult();
        result.setData(postService.pageByCondition(pageBean));
        return result;
    }
}
