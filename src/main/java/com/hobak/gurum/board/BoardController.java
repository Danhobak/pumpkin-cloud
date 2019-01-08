package com.hobak.gurum.board;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class BoardController {
	
	@RequestMapping(value = "/board", method = RequestMethod.GET)
	public String boardMain() {
		return "board/boardMain";
	}
	
	@RequestMapping(value = "/board/write", method = RequestMethod.POST)
	public String boardWrite() {
		return "board/boardWrite";
	}
}
