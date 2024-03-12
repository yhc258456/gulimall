package com.rachel.gulimall.product.app;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rachel.gulimall.product.entity.CategoryEntity;
import com.rachel.gulimall.product.service.CategoryService;
import com.rachel.common.utils.PageUtils;
import com.rachel.common.utils.R;



/**
 * 
 *
 * @author rachelk
 * @email 413843464@qq.com
 * @date 2024-03-01 14:43:24
 */
@RestController
@RequestMapping("product/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    /**
     * 修改分类
     */
    @RequestMapping("/update/sort")
    public R update(@RequestBody CategoryEntity[] category){

        categoryService.updateBatchById(Arrays.asList(category));
        return R.ok();
    }

    /**
     * 列表
     */
    @RequestMapping("/list/tree")
    public R listWithTree(@RequestParam Map<String, Object> params){
        List<CategoryEntity> categoryEntityList = categoryService.listWithTree(params);

        return R.ok().put("data", categoryEntityList);
    }


    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = categoryService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{catId}")
    public R info(@PathVariable("catId") Long catId){
		CategoryEntity category = categoryService.getById(catId);

        return R.ok().put("data", category);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody CategoryEntity category){
		categoryService.save(category);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody CategoryEntity category){
		categoryService.updateCascade(category);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] catIds){
        categoryService.deleteMenus(Arrays.asList(catIds));
        return R.ok();
    }

}
