package com.purple.demo.controller;

import java.util.HashMap;
import java.util.Map;

import com.purple.demo.common.Const;
import com.purple.demo.model.FeedListDTO;
import com.purple.demo.model.UserEntity;
import com.purple.demo.service.MypageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/mypage")
public class MypageController {

	@Autowired
	private MypageService service;

	@RequestMapping("")
	public String mypage() {
		return "/mypage";
	}

	@ResponseBody
	@RequestMapping(value="", method = RequestMethod.POST)
	public Map<String, Object> mypageFeedList(@RequestBody FeedListDTO param){
		Map<String, Object> mypageFeedListResult = new HashMap<String, Object>();
		mypageFeedListResult.put("result", service.selMypageFeedList(param));
		return mypageFeedListResult;
	}

	@ResponseBody
	@PutMapping("/mod_userinfo")
	public Map<String, Object> mod_userinfo(@RequestBody UserEntity userEntity) {
		Map<String, Object> modUserInfoResult = new HashMap<String, Object>();
		modUserInfoResult.put(Const.KEY_REUSLT, service.modUserInfo(userEntity));
		return modUserInfoResult;
	}

	@ResponseBody
	@PostMapping("/pw_check")
	public Map<String, Object> checkUserPw(@RequestBody Map<String, Object> map) {
		Map<String, Object> checkUserPwResult = new HashMap<String, Object>();
		checkUserPwResult.put(Const.KEY_REUSLT, service.checkUserpw(map));
		return checkUserPwResult;
	}

	@ResponseBody
	@PostMapping("/mod_userpw")
	public Map<String, Object> modUserPw(@RequestBody UserEntity userEntity) {
		Map<String, Object> modUserPwResult = new HashMap<String, Object>();
		modUserPwResult.put(Const.KEY_REUSLT, service.modUserPw(userEntity));
		return modUserPwResult;
	}

	// 마이페이지 프로필이미지 등록
	@ResponseBody
	@PostMapping("/profile_img")
	public Map<String, Object> profile_img(MultipartFile img) {
		Map<String, Object> Result = new HashMap<String, Object>();

		if (img == null) {
			UserEntity userEntity = new UserEntity();
			Result.put("result", userEntity.getUser_profileimg());
		}else{
			Result.put("result", service.profile_img(img));
		}
		return Result;
	}

	// 마이페이지 배경이미지 등록
	@ResponseBody
	@PostMapping("/background_img")
	public Map<String, Object> background_img(MultipartFile img) {
		Map<String, Object> Result = new HashMap<String, Object>();

		if (img == null) {
			UserEntity userEntity = new UserEntity();
			Result.put("result", userEntity.getUser_backgroundimg());
		}else{
			Result.put("result", service.background_img(img));
		}
		return Result;
	}
}
