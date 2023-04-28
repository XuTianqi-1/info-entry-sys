package baseline.app.service.impl;

import baseline.app.mapper.ProjectMapper;
import baseline.app.pojo.entity.ContactPerson;
import baseline.app.pojo.entity.ContactPersonProject;
import baseline.app.pojo.entity.Project;
import baseline.app.pojo.query.ProjectQuery;
import baseline.app.pojo.vo.ProjectVo;
import baseline.app.service.ContactPersonProjectService;
import baseline.app.service.EmployeeService;
import baseline.app.service.PostService;
import baseline.app.service.ProjectService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author crelle
 * @since 2022-11-09 10:06:28
 */
@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements ProjectService {
    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private ContactPersonProjectService contactPersonProjectService;

    @Autowired
    private PostService postService;

    @Autowired
    private EmployeeService employeeService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean create(Project object) {
        List<ContactPerson> contactPeoples = object.getContactPeoples();
        this.save(object);
        List<ContactPersonProject> contactPersonProjects = new ArrayList<>(contactPeoples.size());
        if (CollectionUtils.isNotEmpty(contactPeoples)) {
            contactPeoples.forEach(contactPerson -> {
                ContactPersonProject contactPersonProject = new ContactPersonProject();
                contactPersonProject.setContactPersonId(contactPerson.getId());
                contactPersonProject.setProjectId(object.getId());
                contactPersonProjects.add(contactPersonProject);
            });
        }
        contactPersonProjectService.saveBatch(contactPersonProjects);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean create(List<Project> objects) {

        return saveBatch(objects);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(String id) {
        //TODO
        //项目是否有接口人在负责

        //项目是否有员工

        //项目是否有岗位

        removeById(id);
    }

    @Override
    public void deleteByIds(List<String> ids) {
        removeByIds(ids);
    }

    @Override
    public boolean update(Project object) {
        return updateById(object);
    }

    @Override
    public Page<Project> pageByCondition(Page<Project> page) {
        Project project = page.getRecords().get(0);
        LambdaQueryWrapper<Project> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .like(StringUtils.isNotBlank(project.getName()), Project::getName, project.getName())
                .like(StringUtils.isNotBlank(project.getStatus()), Project::getStatus, project.getStatus())
                .like(StringUtils.isNotBlank(project.getRegionId()), Project::getRegionId, project.getRegionId())
                .like(StringUtils.isNotBlank(project.getDepartmentId()), Project::getDepartmentId, project.getDepartmentId());
        return page(page, queryWrapper);
    }

    @Override
    public Page<ProjectVo> manualPage(Page<ProjectQuery> pageBean) {
        ProjectQuery projectQuery = pageBean.getRecords().get(0);
        Page<ProjectVo> page = new Page<>();
        List<ProjectVo> list = projectMapper.manualPage(projectQuery);
        PageInfo<ProjectVo> pageInfo = new PageInfo<>(list);
        page.setTotal(pageInfo.getTotal());
        page.setRecords(list);
        return page;
    }

    @Override
    public Project queryById(String id) {
        return getById(id);
    }

    @Override
    public List<Project> queryByIds(List<String> ids) {
        return null;
    }
}
