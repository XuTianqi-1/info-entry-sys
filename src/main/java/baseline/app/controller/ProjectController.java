package baseline.app.controller;


import baseline.app.pojo.entity.Employee;
import baseline.app.pojo.entity.Post;
import baseline.app.pojo.entity.Project;
import baseline.app.pojo.query.ProjectQuery;
import baseline.app.service.EmployeeService;
import baseline.app.service.PostService;
import baseline.app.service.ProjectService;
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
import java.util.stream.Collectors;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author crelle
 * @since 2022-11-09 10:06:28
 */
@Api(tags = "项目服务")
@RestController
@RequestMapping("/app/project")
public class ProjectController implements BaseController<Project, ProjectQuery> {
    @Autowired
    private ProjectService projectService;

    @Autowired
    private PostService postService;

    @Autowired
    private EmployeeService employeeService;

    @ApiOperation(value = "创建")
    @ApiParam(required = true, name = "", value = "入参")
    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @Override
    public ResponseResult<Project> create(Project object) {
        ResponseResult result = new ResponseResult();
        boolean isSuccess = projectService.create(object);
        if (!isSuccess) {
            result.buildFail("创建失败！");
        }
        return result;
    }

    @ApiOperation(value = "批量创建")
    @ApiParam(required = true, name = "", value = "入参")
    @RequestMapping(value = "/creates", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @Override
    public ResponseResult<Project> creates(List<Project> objects) {
        ResponseResult result = new ResponseResult();
        boolean isSuccess = projectService.create(objects);
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
        List<Post> postList = postService
                .list()
                .stream()
                .filter(post -> post.getProjectId().equals(id))
                .collect(Collectors.toList());
        if (!postList.isEmpty()) {
            result.buildFail("有岗位在使用此项目，无法删除！");
            return result;
        }
        List<Employee> employeeList = employeeService
                .list()
                .stream()
                .filter(employee -> employee.getProjectId().equals(id))
                .collect(Collectors.toList());
        if (!employeeList.isEmpty()) {
            result.buildFail("有员工在使用此项目，无法删除！");
            return result;
        }
        projectService.deleteById(id);
        return result;
    }

    @ApiOperation(value = "根据ID批量删除")
    @ApiParam(required = true, name = "id", value = "入参")
    @RequestMapping(value = "/deleteByIds", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @Override
    public ResponseResult<String> deleteByIds(List<String> ids) {
        ResponseResult result = new ResponseResult();
        projectService.deleteByIds(ids);
        return result;
    }

    @ApiOperation(value = "根据ID更新")
    @ApiParam(required = true, name = "id", value = "入参")
    @RequestMapping(value = "/updateById", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @Override
    public ResponseResult<String> updateById(Project object) {
        ResponseResult result = new ResponseResult();
        boolean isSuccess = projectService.update(object);
        if (!isSuccess) {
            result.buildFail("更新失败！");
        }
        return result;
    }

    @ApiOperation(value = "分页查询")
    @ApiParam(required = true, name = "", value = "入参")
    @RequestMapping(value = "/pageByCondition", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @Override
    public ResponseResult<Page<Project>> page(Page<Project> pageBean) {
        ResponseResult result = new ResponseResult();
        result.setData(projectService.pageByCondition(pageBean));
        return result;
    }

    @ApiOperation(value = "手动分页查询")
    @ApiParam(required = true, name = "", value = "入参")
    @RequestMapping(value = "/manualPage", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @Override
    public ResponseResult<Page<Project>> manualPage(Page<ProjectQuery> pageBean) {
        ResponseResult result = new ResponseResult();
        result.setData(projectService.manualPage(pageBean));
        return result;
    }
}
