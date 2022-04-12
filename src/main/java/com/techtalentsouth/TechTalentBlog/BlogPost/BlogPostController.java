package com.techtalentsouth.TechTalentBlog.BlogPost;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class BlogPostController {

    @Autowired
    private BlogPostRepository blogPostRepository;
    private static List<BlogPost> posts = new ArrayList<>();

    public BlogPostController() {
    }

    @GetMapping(value="/") //READ
    public String index(BlogPost blogPost) {
        return "blogpost/index";
    }

    private BlogPost blogPost;

//    @GetMapping(value = "/")
//    public String index(BlogPost blogPost, Model model) {
//        model.addAttribute("posts", posts);
//        return "blogpost/index";
//    }


    @PostMapping(value = "/blogposts") //CREATE
    public String addNewBlogPost(BlogPost blogPost, Model model) {
        blogPostRepository.save(new BlogPost(blogPost.getTitle(), blogPost.getAuthor(), blogPost.getBlogEntry()));
        model.addAttribute("title", blogPost.getTitle());
        model.addAttribute("author", blogPost.getAuthor());
        model.addAttribute("blogEntry", blogPost.getBlogEntry());
        return "blogpost/result";
    }

    @GetMapping(value = "/blogposts/new")
    public String newBlog (BlogPost blogPost) {
        return "blogpost/new";
    }

    @RequestMapping(value = "/blogposts/update/{id}")
    public String updateExistingPost(@PathVariable Long id, BlogPost blogPost, Model model) {
        Optional<BlogPost> post = blogPostRepository.findById(id);
        if (post.isPresent()) { //does the code exist?
            //blogPost
            BlogPost actualPost = post.get(); //apply post to intermediary
            actualPost.setTitle(blogPost.getTitle());//assigning title from blogPost
            actualPost.setAuthor(blogPost.getAuthor());
            actualPost.setBlogEntry(blogPost.getBlogEntry());
            blogPostRepository.save(actualPost); //saves intermediary back to repository
            model.addAttribute("blogPost", actualPost); // returning intermediary to
        }
        return "blogpost/result";
    }

    @RequestMapping(value = "blogposts/delete/{id}") //doesn't need a model bc we are deleting it
    public String deletePostById(@PathVariable Long id, BlogPost blogPost) {
        blogPostRepository.deleteById(id);
        return "blogpost/delete";
    }


}
