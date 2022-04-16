package vttp2022.paf.GIPHYRepractice.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import vttp2022.paf.GIPHYRepractice.service.service;

@Controller
@RequestMapping({"/", "index.html"})
public class controller {

    @Autowired
    private service svc;
    
    @GetMapping()
    public ModelAndView gifsOfMyBaby () {
        ModelAndView mvc = new ModelAndView();

        String[] babies = {"band+of+brothers", "dead+poets+society", "joseph+mazzello", "radiohead", "andrew+garfield"};

        String media = babies[(int) (Math.random() * babies.length)];

        List<String> urlList = svc.favMovies(media);

        media = media.replace('+', ' ');
        media = media.toUpperCase();

        mvc.setStatus(HttpStatus.OK);
        mvc.setViewName("index");
        mvc.addObject("media", media);
        mvc.addObject("urlList", urlList);

        return mvc;
    }
}
