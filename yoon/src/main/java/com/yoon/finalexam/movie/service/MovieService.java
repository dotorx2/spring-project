package com.yoon.finalexam.movie.service;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.yoon.finalexam.movie.dto.MovieForm;
import com.yoon.finalexam.movie.model.Movie;
import com.yoon.finalexam.movie.repository.MovieRepository;

@Service
public class MovieService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MovieRepository movieRepository;

    public Iterable<Movie> getMovies() {
        return movieRepository.findAll();
    }

    public Movie getMovieDetail(Long id) {
        return movieRepository.findById(id).orElse(null);
    }

    public Movie postMovieCreate(MovieForm movieForm) throws IOException {
        log.debug("등록-/api/movies 호출.");
        log.info("MovieForm 전달값: " + movieForm.toString());

        MultipartFile image = movieForm.getImage();
        String uploadDir = new File("uploads").getAbsolutePath() + File.separator;
        File uploadDirFile = new File(uploadDir);
        if (!uploadDirFile.exists()) {
            uploadDirFile.mkdirs();
        }
        String imagePath = uploadDir + image.getOriginalFilename();
        image.transferTo(new File(imagePath));

        Movie movie = new Movie(
            movieForm.getGroupName(),
            movieForm.getKorName(),
            movieForm.getEngName(),
            movieForm.getContent(),
            image.getOriginalFilename() // 파일 경로 대신 파일 이름만 저장
        );

        movieRepository.save(movie);
        log.info("movie 저장후: " + movie.toString());
        return movie;
    }

    public Movie putMovieUpdate(MovieForm movieForm) throws IOException {
        log.debug("수정-/api/movies 호출.");
        log.info("MovieForm 전달값: " + movieForm.toString());

        Movie movie = movieRepository.findById(movieForm.getId()).orElse(null);
        if (movie == null) {
            return null;
        }

        MultipartFile image = movieForm.getImage();
        if (image != null && !image.isEmpty()) {
            String uploadDir = new File("uploads").getAbsolutePath() + File.separator;
            File uploadDirFile = new File(uploadDir);
            if (!uploadDirFile.exists()) {
                uploadDirFile.mkdirs();
            }
            String imagePath = uploadDir + image.getOriginalFilename();
            image.transferTo(new File(imagePath));
            movie.setImage(image.getOriginalFilename());
        }

        movie.setGroupName(movieForm.getGroupName());
        movie.setKorName(movieForm.getKorName());
        movie.setEngName(movieForm.getEngName());
        movie.setContent(movieForm.getContent());

        movieRepository.save(movie);
        log.info("movie 수정후: " + movie.toString());
        return movie;
    }

    public void deleteMovie(Long id) {
        log.debug("삭제-/api/movies 호출.");
        movieRepository.deleteById(id);
    }
}
