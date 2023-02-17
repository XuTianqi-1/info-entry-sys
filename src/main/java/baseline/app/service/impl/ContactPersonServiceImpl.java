package baseline.app.service.impl;

import baseline.app.mapper.ContactPersonMapper;
import baseline.app.pojo.entity.ContactPerson;
import baseline.app.pojo.entity.Employee;
import baseline.app.pojo.query.ContactPersonQuery;
import baseline.app.service.ContactPersonService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author crelle
 * @since 2022-11-24 10:45:04
 */
@Service
public class ContactPersonServiceImpl extends ServiceImpl<ContactPersonMapper, ContactPerson> implements ContactPersonService {
    @Autowired
    private ContactPersonMapper contactPersonMapper;

    @Override
    public boolean create(ContactPerson object) {
        return save(object);
    }

    @Override
    public boolean create(List<ContactPerson> objects) {
        return saveBatch(objects);
    }

    @Override
    public void deleteById(String id) {
        removeById(id);
    }

    @Override
    public void deleteByIds(List<String> ids) {
        removeByIds(ids);
    }

    @Override
    public boolean update(ContactPerson object) {
        return updateById(object);
    }

    @Override
    public Page<ContactPerson> pageByCondition(Page<ContactPerson> page) {
        ContactPerson contactPerson = page.getRecords().get(0);
        LambdaQueryWrapper<ContactPerson> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper
                .like(StringUtils.isNotBlank(contactPerson.getCustomerId()),ContactPerson::getCustomerId,contactPerson.getCustomerId())
                .like(StringUtils.isNotBlank(contactPerson.getInterfaceName()), ContactPerson::getInterfaceName, contactPerson.getInterfaceName());
        return page(page, lambdaQueryWrapper);
    }

    @Override
    public Page<ContactPerson> manualPage(Page<ContactPersonQuery> pageBean) {
        ContactPersonQuery contactPersonQuery = pageBean.getRecords().get(0);
        Page<ContactPerson> personPage = new Page<>();
        return contactPersonMapper.manualPage(personPage, contactPersonQuery);
    }

    @Override
    public ContactPerson queryById(String id) {
        return getById(id);
    }

    @Override
    public List<ContactPerson> queryByIds(List<String> ids) {
        return null;
    }
}
