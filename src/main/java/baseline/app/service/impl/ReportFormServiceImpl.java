package baseline.app.service.impl;

import baseline.app.mapper.ReportFormMapper;
import baseline.app.pojo.dto.reportform.EmployeeAgeDto;
import baseline.app.pojo.dto.reportform.EmployeeAnalysisDto;
import baseline.app.pojo.dto.reportform.EmployeeCustomerDto;
import baseline.app.pojo.dto.reportform.EmployeeSeniorityDto;
import baseline.app.pojo.query.reportform.*;
import baseline.app.pojo.vo.EmployeeVo;
import baseline.app.pojo.vo.reportform.*;
import baseline.app.service.ReportFormService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
public class ReportFormServiceImpl implements ReportFormService {

    @Autowired
    private ReportFormMapper reportFormMapper;

    @Override
    public List<DepartmentAnalysisVO> departmentAnalysis(DepartmentAnalysisQuery departmentAnalysisQuery) {
        return null;
    }

    @Override
    public List<ProjectGapAnalysisVO> projectGapAnalysis(ProjectGapAnalysisQuery projectGapAnalysisQuery) {
        return null;
    }

    @Override
    public List<ProjectNumberAnalysisVO> projectNumberAnalysis(ProjectNumberAnalysisQuery projectNumberAnalysisQuery) {
        return null;
    }

    @Override
    public List<RegionAnalysisVO> regionAnalysis(RegionAnalysisQuery regionAnalysisQuery) {
        return null;
    }

    @Override
    public List<SkillAnalysisVO> skillAnalysis(SkillAnalysisQuery skillAnalysisQuery) {
        return null;
    }

    @Override
    public List<EmployeeAnalysisVO> employeeAnalysis(EmployeeAnalysisQuery employeeAnalysisQuery) {
        List<EmployeeAnalysisDto> employeeAnalysisDtos = reportFormMapper.employeeAnalysis(employeeAnalysisQuery);
        if (CollectionUtils.isEmpty(employeeAnalysisDtos)) {
            return Collections.emptyList();
        }
        List<EmployeeAnalysisVO> list = new ArrayList<>(employeeAnalysisDtos.size());
        employeeAnalysisDtos.forEach(employeeAnalysisDto -> {
            EmployeeAnalysisVO employeeAnalysisVO = new EmployeeAnalysisVO();
            BeanUtils.copyProperties(employeeAnalysisDto, employeeAnalysisVO);
            List<String> value = new ArrayList<>(2);
            value.add(employeeAnalysisDto.getLongitude());
            value.add(employeeAnalysisDto.getLatitude());
            employeeAnalysisVO.setValue(value);
            list.add(employeeAnalysisVO);
        });
        return list;
    }

    @Override
    public List<EmployeeSeniorityVO> employeeSeniority(EmployeeSeniorityQuery employeeSeniorityQuery) {
        List<EmployeeSeniorityDto> employeeSeniorityDtos = reportFormMapper.employeeSeniority(employeeSeniorityQuery);
        if (CollectionUtils.isEmpty(employeeSeniorityDtos)) {
            return Collections.emptyList();
        }
        List<EmployeeSeniorityVO> employeeSeniorityVOS = new ArrayList<>(employeeSeniorityDtos.size());
        employeeSeniorityDtos.forEach(employeeSeniorityDto -> {
            EmployeeSeniorityVO employeeSeniorityVO = new EmployeeSeniorityVO();
            BeanUtils.copyProperties(employeeSeniorityDto, employeeSeniorityVO);
            employeeSeniorityVOS.add(employeeSeniorityVO);
        });

        return employeeSeniorityVOS;
    }

    @Override
    public List<EmployeeAgeVO> employeeAge(EmployeeAgeQuery employeeAgeQuery) {
        List<EmployeeAgeDto> employeeAgeDtos = reportFormMapper.employeeAge(employeeAgeQuery);
        if (CollectionUtils.isEmpty(employeeAgeDtos)) {
            return Collections.emptyList();
        }
        List<EmployeeAgeVO> employeeAgeVOS = new ArrayList<>(employeeAgeDtos.size());
        employeeAgeDtos.forEach(employeeAgeDto -> {
            EmployeeAgeVO employeeAgeVO = new EmployeeAgeVO();
            BeanUtils.copyProperties(employeeAgeDto, employeeAgeVO);
            employeeAgeVOS.add(employeeAgeVO);
        });
        return employeeAgeVOS;
    }

    @Override
    public List<EmployeeCustomerVO> employeeCustomer(EmployeeCustomerQuery employeeCustomerQuery) {
        List<EmployeeCustomerDto> employeeCustomerDtos = reportFormMapper.employeeCustomer(employeeCustomerQuery);
        if (CollectionUtils.isEmpty(employeeCustomerDtos)) {
            return Collections.emptyList();
        }
        List<EmployeeCustomerVO> employeeCustomerVOS = new ArrayList<>(employeeCustomerDtos.size());
        employeeCustomerDtos.forEach(employeeCustomerDto -> {
            EmployeeCustomerVO employeeCustomerVO = new EmployeeCustomerVO();
            BeanUtils.copyProperties(employeeCustomerDto, employeeCustomerVO);
            employeeCustomerVOS.add(employeeCustomerVO);
        });
        return employeeCustomerVOS;
    }

    @Override
    public EmployeeMarriageAndChildbirthAndSexVO employeeMarriageAndChildbirth(EmployeeMarriageAndChildbirthAndSexQuery marriageAndChildbirthQuery) {
        List<EmployeeVo> employeeVos = reportFormMapper.employeeMarriageAndChildbirthAndSex(marriageAndChildbirthQuery);
        if (CollectionUtils.isEmpty(employeeVos)) {
            return null;
        }
        EmployeeMarriageAndChildbirthAndSexVO result = new EmployeeMarriageAndChildbirthAndSexVO();
        result.setTotal(Long.valueOf(employeeVos.size()));
        long manNum = employeeVos.stream().filter(employeeVo -> {
            return StringUtils.equals(employeeVo.getGender(), "1");
        }).count();
        result.setManNum(manNum);
        long womanNum = employeeVos.stream().filter(employeeVo -> {
            return StringUtils.equals(employeeVo.getGender(), "0");
        }).count();
        result.setWomenNum(womanNum);
        long marriedAndNoPregnantNum = employeeVos.stream().filter(employeeVo -> {
            if (StringUtils.equals(employeeVo.getMarriage(), "1") && StringUtils.equals(employeeVo.getChild(), "0")) {
                return true;
            } else {
                return false;
            }
        }).count();
        result.setMarriedAndNoPregnantNum(marriedAndNoPregnantNum);

        long marriedAndPregnantNum = employeeVos.stream().filter(employeeVo -> {
            if (StringUtils.equals(employeeVo.getMarriage(), "1") && StringUtils.equals(employeeVo.getChild(), "1")) {
                return true;
            } else {
                return false;
            }
        }).count();
        result.setMarriedAndPregnantNum(marriedAndPregnantNum);
        long noMarriedAndNoPregnantNum = employeeVos.stream().filter(employeeVo -> {
            if (StringUtils.equals(employeeVo.getMarriage(), "0") && StringUtils.equals(employeeVo.getChild(), "1")) {
                return true;
            } else {
                return false;
            }
        }).count();
        result.setNoMarriedAndNoPregnantNum(noMarriedAndNoPregnantNum);
        return result;
    }
}
