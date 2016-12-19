package com.kris.rest.service.impl;

import com.kris.rest.mapper.SysUserMapper;
import com.kris.rest.pojo.SysUser;
import com.kris.rest.service.SysUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysUserServiceImpl implements SysUserService {
	
	@Resource
	private SysUserMapper sysUserMapper;


	@Override
	public SysUser getById(Long id) {
		return sysUserMapper.selectByPrimaryKey(id);
	}
}
