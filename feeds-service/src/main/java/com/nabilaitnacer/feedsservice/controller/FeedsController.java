package com.nabilaitnacer.feedsservice.controller;

import com.nabilaitnacer.feedsservice.dto.CompletReaction;
import com.nabilaitnacer.feedsservice.dto.PostDto;
import com.nabilaitnacer.feedsservice.service.FeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
public class FeedsController {
	@Autowired
	private FeedService feedService;

	@GetMapping("/feeds/{userId}")
	public HashMap<PostDto, List<CompletReaction>> getFeed(@PathVariable Long userId) {
		return feedService.getFeed(userId);
	}
}
