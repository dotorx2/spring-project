package com.yoon.finalexam.movie.restcontroller;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.yoon.finalexam.movie.dto.MovieForm;
import com.yoon.finalexam.movie.model.Movie;
import com.yoon.finalexam.movie.service.MovieService;

@RestController
public class MovieRestController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MovieService movieService;

    // 1. 전체 조회
    @GetMapping("/api/movies")
    public Iterable<Movie> getMovies() {
        return movieService.getMovies();
    }

    // 2. 단건 조회
    @GetMapping("/api/movies/{id}")
    public Movie getMovieDetail(@PathVariable Long id) {
        return movieService.getMovieDetail(id);
    }

    // 3. 등록
    @PostMapping("/api/movies")
    public Movie postMovieCreate(@ModelAttribute MovieForm movieForm, @RequestParam("image") MultipartFile image) throws IOException {
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

    // 4. 수정
    @PutMapping("/api/movies")
    public Movie putMovieUpdate(@ModelAttribute MovieForm movieForm, @RequestParam("image") MultipartFile image) throws IOException {
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

    // 5. 삭제
    @DeleteMapping("/api/movies/{id}")
    public void deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
    }
}
