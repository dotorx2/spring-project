package com.yoon.finalexam.movie.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.yoon.finalexam.movie.dto.MovieForm;
import com.yoon.finalexam.movie.model.Movie;
import com.yoon.finalexam.movie.repository.MovieRepository;
import com.yoon.finalexam.movie.service.MovieService;

@Controller
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;
    
    @Autowired
    private MovieService movieService;

    @GetMapping("/list")
    public String getMovieAllList(Model model) {
        model.addAttribute("movies", movieRepository.findAll());
        return "movie/list"; // list.html 템플릿으로 이동
    }

    @GetMapping("/new")
    public String getMovieNew(Model model) {
        model.addAttribute("movieForm", new MovieForm());
        return "movie/new"; // new.html 템플릿으로 이동
    }

    @GetMapping("/movie")
    public String getMovie(Model model) {
        model.addAttribute("movies", movieRepository.findAll());
        return "movie/movie"; // movie.html 템플릿으로 이동
    }

    @PostMapping("/create")
    public String postMovieCreate(MovieForm movieForm) throws IOException {
        MultipartFile image = movieForm.getImage();
        String uploadDir = new File("uploads/").getAbsolutePath() + File.separator; // 업로드 디렉토리 경로 설정
        File uploadDirFile = new File(uploadDir);
        if (!uploadDirFile.exists()) {
            uploadDirFile.mkdirs(); // 디렉토리가 존재하지 않으면 생성
        }
        String imagePath = uploadDir + image.getOriginalFilename();
        image.transferTo(new File(imagePath)); // 이미지 파일을 해당 경로로 저장
        
        Movie movie = new Movie(
            movieForm.getGroupName(),
            movieForm.getKorName(),
            movieForm.getEngName(),
            movieForm.getContent(),
            image.getOriginalFilename() // 이미지 파일 이름 저장
        );
        movieRepository.save(movie);
        return "redirect:/movie/list"; // 영화 목록 페이지로 리다이렉트
    }

    @GetMapping("/edit")
    public String getMovieEdit(@RequestParam("id") Long id, Model model) {
        Movie movie = movieService.getMovieDetail(id);
        model.addAttribute("movie", movie);
        return "movie/edit"; // edit.html 템플릿으로 이동
    }

    // REST API 엔드포인트 추가
    @GetMapping("/api/movies")
    @ResponseBody
    public Iterable<Movie> getMovies() {
        return movieRepository.findAll();
    }

    @GetMapping("/api/movies/{id}")
    @ResponseBody
    public Movie getMovieDetail(@PathVariable Long id) {
        return movieService.getMovieDetail(id);
    }

    @PostMapping("/api/movies")
    @ResponseBody
    public Movie postMovieCreateApi(@ModelAttribute MovieForm movieForm, @RequestParam("image") MultipartFile image) throws IOException {
        String uploadDir = new File("uploads").getAbsolutePath() + File.separator;
        File uploadDirFile = new File(uploadDir);
        if (!uploadDirFile.exists()) {
            uploadDirFile.mkdirs();
        }
        String imagePath = uploadDir + image.getOriginalFilename();
        image.transferTo(new File(imagePath));
        movieForm.setImage(image);

        return movieService.postMovieCreate(movieForm);
    }

    @PutMapping("/api/movies")
    @ResponseBody
    public Movie putMovieUpdateApi(@ModelAttribute MovieForm movieForm, @RequestParam("image") MultipartFile image) throws IOException {
        if (image != null && !image.isEmpty()) {
            String uploadDir = new File("uploads").getAbsolutePath() + File.separator;
            File uploadDirFile = new File(uploadDir);
            if (!uploadDirFile.exists()) {
                uploadDirFile.mkdirs();
            }
            String imagePath = uploadDir + image.getOriginalFilename();
            image.transferTo(new File(imagePath));
            movieForm.setImage(image);
        }

        return movieService.putMovieUpdate(movieForm);
    }

    @DeleteMapping("/api/movies/{id}")
    @ResponseBody
    public void deleteMovieApi(@PathVariable Long id) {
        movieService.deleteMovie(id);
    }
}
