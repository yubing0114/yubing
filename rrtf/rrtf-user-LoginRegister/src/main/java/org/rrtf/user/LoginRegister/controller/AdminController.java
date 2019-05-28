package org.rrtf.user.LoginRegister.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin")
public class AdminController {

	@RequestMapping("index")
	public String user() {
		return "homepage";
	}

	@RequestMapping("management")
	@RequiresPermissions("management")
	public String userinfo() {
		return "userinfo";
	}
}
