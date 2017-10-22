package com.logistics.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.logistics.base.cache.MCacheKit;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.logistics.base.oss.UploadFile;
import com.logistics.base.utils.JsonBean;

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
	public Map<String, Object> upload(HttpServletRequest request, @RequestParam("file") MultipartFile[] file){
		Map<String, Object> value = new HashMap<String, Object>();
		if (file != null && file.length == 1) {
			if (file[0].getSize() > 1024*1024*4) {//图片不能大于2m
				value.put("success", false);
				value.put("code", 200);
				value.put("msg", "文件大小不能大于4m");
				return value;
			}
		}
		try {
			value.put("success", true);
			value.put("errno",0);
			value.put("code", 0);
			value.put("msg", "");
			List<Object> data = new ArrayList<Object>();
			for (MultipartFile f:file) {
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

	@RequestMapping(value = "/getCode")
	public void getCode(HttpServletRequest req,HttpServletResponse resp) {
		// 用于绘制图片，设置图片的长宽和图片类型（RGB)
		BufferedImage bi = new BufferedImage(IMG_HEIGHT, IMG_WIDTH, BufferedImage.TYPE_INT_RGB);
		// 获取绘图工具
		Graphics graphics = bi.getGraphics();
		graphics.setColor(new Color(247, 242, 242)); // 使用RGB设置背景颜色
		graphics.fillRect(0, 0, 100, 38); // 填充矩形区域
		graphics.setColor(getRandColor(160,200));

		// 验证码中所使用到的字符
		char[] codeChar = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456".toCharArray();
		String captcha = ""; // 存放生成的验证码
		Random random = new Random();
		for (int i=0; i<155; i++){
			int x = random.nextInt(IMG_HEIGHT);
			int y = random.nextInt(IMG_WIDTH);
			int xl = random.nextInt(16);
			int yl = random.nextInt(16);
			graphics.drawLine(x,y,x+xl,y+yl);
		}
		for(int i = 0; i < CODE_LEN; i++) { // 循环将每个验证码字符绘制到图片上
			int index = random.nextInt(codeChar.length);
			// 随机生成验证码颜色
			graphics.setColor(new Color(random.nextInt(150), random.nextInt(200), random.nextInt(255)));
//			graphics.setFont(new Font("微软雅黑",Font.BOLD ,18));
			graphics.setFont(new Font("Times New Roman",Font.PLAIN,22));
			// 将一个字符绘制到图片上，并制定位置（设置x,y坐标）
			graphics.drawString(codeChar[index] + "", (i * 20) + 15, 24);
			captcha += codeChar[index];
		}
		// 将生成的验证码code放入sessoin中
		req.getSession().setAttribute("code", captcha);
		// 通过ImageIO将图片输出
		try {
			ImageIO.write(bi, "JPG", resp.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static Color getRandColor(int fc,int bc){//给定范围获得随机颜色
		Random random = new Random();
		 if(fc>255) fc=255;
		 if(bc>255) bc=255;
		 int r=fc+random.nextInt(bc-fc);
		 int g=fc+random.nextInt(bc-fc);
		 int b=fc+random.nextInt(bc-fc);
		 return new Color(r,g,b);
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
}
