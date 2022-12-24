package baseline.sysmgmt.controller;


import baseline.common.enumeration.ResponseEnum;
import baseline.common.exception.BusinessException;
import baseline.common.pojo.vo.ResponseResult;
import baseline.common.util.ResultUtils;
import baseline.sysmgmt.pojo.entity.Role;
import baseline.sysmgmt.pojo.entity.User;
import baseline.sysmgmt.pojo.entity.UserRole;
import baseline.sysmgmt.pojo.query.UserQuery;
import baseline.sysmgmt.service.RoleService;
import baseline.sysmgmt.service.UserRoleService;
import baseline.sysmgmt.service.UserService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author crelleupdateById
 * @since 2022-10-01 12:06:26
 */
@Api(tags = "用户服务")
@RestController
@RequestMapping("/sysmgmt/user")
public class UserController implements BaseController<User,UserQuery> {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRoleService userRoleService;

    @ApiOperation(value = "新增用户")
    @ApiParam(required = true, name = "user", value = "入参")
    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<User> create(@RequestBody User user) {
        ResponseResult<User> responseResult = new ResponseResult<User>();
        try {
            if (!(null == user.getId())) {
                responseResult.buildFail("用户标识使用jpa自动生成，不需要传入！");
                return responseResult;
            }

            if (StringUtils.isBlank(String.valueOf(user.getUsername()))) {
                responseResult.buildFail("用户名为空！");
                return responseResult;
            }

            if (StringUtils.isBlank(String.valueOf(user.getPassword()))) {
                responseResult.buildFail("密码为空！");
                return responseResult;
            }
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.select().eq("username", user.getUsername());
            List<User> users = userService.list(queryWrapper);
            if (!CollectionUtils.isEmpty(users)) {
                responseResult.buildFail("用户名已经被占用！");
                return responseResult;
            }
            user.setAccountNonExpired(true);
            user.setAccountNonLocked(true);
            user.setCredentailsNonExpired(true);
            user.setEnabled(true);
            userService.create(user);

            QueryWrapper<User> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.select().eq("username", user.getUsername());
            User user1 = userService.getOne(queryWrapper1);

            //给用户设置默认的访客角色和默认状态
            QueryWrapper<Role> queryWrapperRole = new QueryWrapper<>();
            queryWrapperRole.select().eq("name", "ROLE_guest");
            Role guest = roleService.getOne(queryWrapperRole);

            UserRole userRole = new UserRole();
            userRole.setRoleId(guest.getId());
            userRole.setUserId(user1.getId());
            userRoleService.create(userRole);


        } catch (Exception e) {
            throw new BusinessException(ResponseEnum.UNKNOWN);
        }
        return responseResult;
    }

    @ApiOperation(value = "用户登录")
    @ApiParam(required = true, name = "user", value = "入参")
    @RequestMapping(value = "/xx", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<String> login(@RequestBody User user) {
        ResponseResult<String> responseResult = new ResponseResult<String>();
        try {
            Wrapper<User> queryWrapperUser = new QueryWrapper<>();
            ((QueryWrapper<User>) queryWrapperUser).setEntity(user);
            User result = userService.getOne(queryWrapperUser);
            if (null == result) {
                return ResultUtils.fail("用户或者密码不正确！");
            }
        } catch (Exception e) {
            throw new BusinessException(ResponseEnum.UNKNOWN);
        }
        return responseResult;
    }

    @ApiOperation(value = "根据用户标识查询用户")
    @ApiParam(required = true, name = "id", value = "入参")
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<User> queryById(@PathVariable String id) {
        ResponseResult<User> responseResult = new ResponseResult<>();
        try {
            User user = userService.queryById(id);
            responseResult.setData(user);
        } catch (Exception e) {
            throw new BusinessException(ResponseEnum.UNKNOWN);
        }
        return responseResult;
    }



    @ApiOperation(value = "分页查询用户")
    @ApiParam(required = true, name = "pageBean", value = "入参")
    @RequestMapping(value = "/page", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @Override
    public ResponseResult<Page<User>> page(Page<User> pageBean) {
        ResponseResult<Page<User>> responseResult = new ResponseResult<>();
        try {
            Page<User> page = userService.page(pageBean);
            responseResult.setData(page);
        } catch (Exception e) {
            throw new BusinessException(ResponseEnum.UNKNOWN);
        }
        return responseResult;
    }

    @ApiOperation(value = "手动分页查询用户")
    @ApiParam(required = true, name = "pageBean", value = "入参")
    @RequestMapping(value = "/manualPage", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @Override
    public ResponseResult<Page<User>> manualPage(Page<UserQuery> pageBean) {
        ResponseResult<Page<User>> responseResult = new ResponseResult<>();
        try {
            Page<User> page = userService.manualPage(pageBean);
            responseResult.setData(page);
        } catch (Exception e) {
            throw new BusinessException(ResponseEnum.UNKNOWN);
        }
        return responseResult;
    }

    @ApiOperation(value = "查询所有用户")
    @ApiParam(required = true, name = "user", value = "入参")
    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<List<User>> queryAll() {
        ResponseResult<List<User>> responseResult = new ResponseResult<>();
        try {
            List<User> userList = userService.queryAll();
            responseResult.setData(userList);
        } catch (Exception e) {
            throw new BusinessException(ResponseEnum.UNKNOWN);
        }
        return responseResult;
    }

    @ApiOperation(value = "更新用户")
    @ApiParam(required = true, name = "user", value = "入参")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<String> updateById(@PathVariable String id, @RequestBody User user) {
        ResponseResult<String> responseResult = new ResponseResult<>();
        try {
            boolean result = userService.update(user);
            if (!result) {
                responseResult.buildFail("没有此用户，无法更新！");
            }
        } catch (Exception e) {
            throw new BusinessException(ResponseEnum.UNKNOWN);
        }
        return responseResult;
    }

    @ApiOperation(value = "根据用户标识删除用户")
    @ApiParam(required = true, name = "user", value = "入参")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<String> deleteById(@PathVariable String id) {
        ResponseResult<String> responseResult = new ResponseResult<String>();
        try {
           if(!userService.deleteById(id)){
               responseResult.buildFail("删除失败！");
           }
        } catch (Exception e) {
            throw new BusinessException(ResponseEnum.UNKNOWN);
        }
        return responseResult;
    }


    @ApiOperation(value = "上传用户头像")
    @ApiParam(required = true, name = "", value = "入参")
    @RequestMapping(value = "/uploadAvatar", method = RequestMethod.POST)
    public ResponseResult<String> uploadAvatar(@RequestPart("file") MultipartFile multipartFile, @RequestParam("fileType") String fileType) {
        ResponseResult<String> responseResult = new ResponseResult<>();
        try {
            if (multipartFile.isEmpty()) {
                responseResult.buildFail("请选择文件！");
                return responseResult;
            }
//            String absolutelyUri = ftpService.uploadFromMultipartFile(multipartFile, fileType);
//            responseResult.setData(absolutelyUri);
        } catch (Exception e) {
            throw new BusinessException(ResponseEnum.UNKNOWN);
        }
        return responseResult;
    }


    @ApiOperation(value = "删除用户头像")
    @ApiParam(required = true, name = "", value = "入参")
    @RequestMapping(value = "/deleteAvatar", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<String> deleteAvatar(String absoluteUri) {
        ResponseResult<String> responseResult = new ResponseResult<>();
        try {
            if (StringUtils.isBlank(absoluteUri)) {
                responseResult.buildFail("请传入要删除的资源路径！");
                return responseResult;
            }
//            if (!ftpService.deleteByAbsoluteUri(absoluteUri)) {
//                responseResult.buildFail("删除失败！");
//                return responseResult;
//            }
        } catch (Exception e) {
            throw new BusinessException(ResponseEnum.UNKNOWN);
        }
        return responseResult;
    }

    @ApiOperation(value = "修改用户密码")
    @ApiParam(required = true, name = "", value = "入参")
    @RequestMapping(value = "/password/{password}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<String> deleteAvatar(@PathVariable("password") String password, @RequestBody User user) {
        ResponseResult<String> responseResult = new ResponseResult<>();
        int result = userService.updatePasswordById(password, user);
        if (result == 0) {
            responseResult.buildFail("旧密码错误");
        }
        return responseResult;
    }

}
