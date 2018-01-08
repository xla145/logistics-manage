package com.logistics.controller;

import com.logistics.base.oss.UploadFile;
import com.logistics.base.utils.JsonBean;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 公共
 * 
 * @author caibin
 *
 */
@Controller
public class CommonController {
	
	private Logger loger = LoggerFactory.getLogger(getClass());
	
	// 图片高度
	private static final int IMG_HEIGHT = 100;
	// 图片宽度
	private static final int IMG_WIDTH = 38;
	// 验证码长度
	private static final int CODE_LEN = 4;
	/**
	 * 多张上传
	 * */
	@RequestMapping(value = "/upload",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> upload(HttpServletRequest request, @RequestParam("file") MultipartFile[] file) {
		Map<String, Object> value = new HashMap<String, Object>();
		if (file != null && file.length == 1) {
			if (file[0].getSize() > 1024 * 1024 * 4) {//图片不能大于2m
				value.put("success", false);
				value.put("code", 200);
				value.put("msg", "文件大小不能大于4m");
				return value;
			}
		}
		try {
			value.put("success", true);
			value.put("errno", 0);
			value.put("code", 0);
			value.put("msg", "");
			List<Object> data = new ArrayList<Object>();
			for (MultipartFile f : file) {
				String imgUrl = UploadFile.uploadContent(f);//此处是调用上传服务接口
				data.add(imgUrl);
			}
			value.put("data", data);
		} catch (Exception e) {
			e.printStackTrace();
			value.put("success", false);
			value.put("code", 200);
			value.put("msg", "文件上传失败");
		}
		return value;
	}

	/**
	 * 多张上传
	 * */
	@RequestMapping(value = "/uploadImage",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> uploadImage(HttpServletRequest request, @RequestParam("file") MultipartFile file){
		Map<String, Object> value = new HashMap<String, Object>();
		if (file.getSize() > 1024*1024*4) {//图片不能大于2m
			value.put("success", false);
			value.put("code", 200);
			value.put("msg", "图片大小不能大于4m");
			return value;
		}
		try {
			value.put("success", true);
			value.put("errno",0);
			value.put("code", 0);
			value.put("msg", "");
			String imgUrl = UploadFile.uploadContent(file);//此处是调用上传服务接口，4是需要更新的userId 测试数据
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("src",imgUrl);
			data.put("title",file.getOriginalFilename());
			value.put("data", data);
		} catch (Exception e) {
			e.printStackTrace();
			value.put("success", false);
			value.put("code", 200);
			value.put("msg", "图片上传失败");
		}
		return value;
	}
	

	/**
	 * 多张上传
	 * */
	@RequestMapping(value = "/uploadImageNew",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> uploadImageNew(HttpServletRequest request, @RequestParam("file") MultipartFile file){
		if (file.getSize() > 1024*1024*4) {//图片不能大于2m
			return JsonBean.error("图片大小不能大于4m");
		}
		try {
			String imgUrl = UploadFile.uploadContent(file);//此处是调用上传服务接口，4是需要更新的userId 测试数据

			if(StringUtils.isBlank(imgUrl)){
				return JsonBean.error("图片上传失败");
			}else{
				return JsonBean.success("ok", imgUrl);
			}
		} catch (Exception e) {
			loger.error("", e);
		}
		return JsonBean.error("图片上传失败");
	}

	@RequestMapping("/demo")
	public String getIndex() {
		return "modules/index";
	}
}
