package com.example.demo.resources;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.Post;
import com.example.demo.repository.PostRepository;
import com.example.demo.resources.util.URL;
import com.example.demo.services.PostService;

@RestController
@RequestMapping(value = "/posts")
public class PostResource {

    private final PostRepository postRepository;
	
	@Autowired
	private PostService service;

    PostResource(PostRepository postRepository) {
        this.postRepository = postRepository;
    }
	
	@GetMapping("/{id}")
	public ResponseEntity<Post> findById(@PathVariable String id){
		
		Post post = service.findById(id);
		return ResponseEntity.ok().body(post);
		
	}
	
	@GetMapping("/titlesearch")
	public ResponseEntity<List<Post>> findByTitle(@RequestParam(defaultValue = "") String text){
		
		text = URL.decodeParam(text);
		
		List <Post> list = service.findByTitle(text);
		
		return ResponseEntity.ok().body(list);
		
	}
	
	@GetMapping("/fullsearch")
	public ResponseEntity<List<Post>> fullSearch(
  			@RequestParam(defaultValue="") String text,
  			@RequestParam(defaultValue="") String minDate,
  			@RequestParam(defaultValue="") String maxDate) {
 		text = URL.decodeParam(text);
 		Date min = URL.convertDate(minDate, new Date(0L));
 		Date max = URL.convertDate(maxDate, new Date());
 		List<Post> list = service.fullSearch(text, min, max);
 		return ResponseEntity.ok().body(list);
 	}

}
