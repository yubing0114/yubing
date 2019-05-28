package org.rrtf.user.LoginRegister.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("user")
public class MemberController {

	@RequestMapping("reference")
	@RequiresPermissions("reference")
	public String userinfo() {
		return "userinfo";
	}
}
