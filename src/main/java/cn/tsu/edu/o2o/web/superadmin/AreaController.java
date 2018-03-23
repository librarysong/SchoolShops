package cn.tsu.edu.o2o.web.superadmin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.tsu.edu.o2o.entity.Area;
import cn.tsu.edu.o2o.service.AreaService;

/**
 * 
 * @author 宋维飞
 *
 */
@Controller
@RequestMapping("/superadmin")
public class AreaController {
	@Autowired
	private AreaService areaService;

	@RequestMapping(value="/listarea",method=RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listArea() {
	    Map<String, Object> modelMap = new HashMap<String, Object>();
        List<Area> list=new ArrayList<Area>();
        try {
        	list=areaService.getAreaList();
        	modelMap.put("rows", list);
        	modelMap.put("total", list.size());
        }catch(Exception e) {
        	e.printStackTrace();
        	modelMap.put("success", false);
        	modelMap.put("errMsg", e.toString());
        }
        
        return modelMap;
	}

}
