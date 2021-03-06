package com.liuscoding.edu.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liuscoding.commonutils.vo.ResultVo;
import com.liuscoding.edu.entity.Course;
import com.liuscoding.edu.model.query.CourseQuery;
import com.liuscoding.edu.model.vo.ChapterVo;
import com.liuscoding.edu.model.vo.CourseWebVo;
import com.liuscoding.edu.service.ChapterService;
import com.liuscoding.edu.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @className: CourseFrontController
 * @description: 课程C端Controller
 * @author: liusCoding
 * @create: 2020-05-17 11:20
 */

@Api(tags = "课程C端服务")
@RestController
@RequestMapping("/edu/courseFront")
@CrossOrigin
public class CourseFrontController {

    private final CourseService courseService;

    private final ChapterService chapterService;

    public CourseFrontController(CourseService courseService, ChapterService chapterService) {
        this.courseService = courseService;
        this.chapterService = chapterService;
    }

    @ApiOperation("条件分页查询课程")
    @PostMapping("/getFrontCourse/{page}/{size}")
    public ResultVo getFrontCourseList(@PathVariable long page,@PathVariable long size,
         CourseQuery courseQuery
    ){
        Page<Course> coursePage = new Page<>(page,size);
        Map<String,Object> map = courseService.getCourseFrontList(coursePage,courseQuery);
        return ResultVo.ok().data(map);
    }

    @ApiOperation("根据课程id，查询课程信息")
    @GetMapping("/getFrontCourseInfo/{courseId}")
    public ResultVo getFrontCourseInfo(@PathVariable String courseId){
        //根据课程id，查询课程的基本信息
        CourseWebVo courseWebVo =  courseService.getBaseCourseInfo(courseId);

        //根据课程id查询章节和小节
        List<ChapterVo> chapterVoList = chapterService.getChapterVideoByCourseId(courseId);
        return ResultVo.ok().data("courseWebVo",courseWebVo).data("chapterVoList",chapterVoList);
    }
}
