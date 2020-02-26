package com.app.services;


import com.app.model.Movie;
import com.app.model.MovieWithDateTime;
import com.app.model.enums.Genre;
import com.app.model.exception.AppException;
import com.app.model.valid.CustomerValidator;
import com.app.repo.CustomerRepository;
import com.app.repo.LoyaltyCardRepository;
import com.app.repo.MovieRepository;
import com.app.repo.SalesStandRepository;
import com.app.services.dataGenerator.DataManager;
import com.app.services.dataGenerator.MovieStoresJsonConverter;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class MovieService {

    private final String jsonFile = "movieTitle.json";
    private final CustomerRepository customerRepository;
    private final CustomerValidator customerValidator;
    private final SalesStandRepository salesStandRepository;
    private final LoyaltyCardRepository loyaltyCardRepository;
    private final MovieRepository movieRepository;

    public MovieService(
            CustomerRepository customerRepository,
            CustomerValidator customerValidator,
            SalesStandRepository salesStandRepository,
            LoyaltyCardRepository loyaltyCardRepository,
            MovieRepository movieRepository) {

        this.customerRepository = customerRepository;
        this.movieRepository = movieRepository;
        this.customerValidator = customerValidator;
        this.salesStandRepository = salesStandRepository;
        this.loyaltyCardRepository = loyaltyCardRepository;
        loadMoviesToDataBase(jsonFile);
    }

    /**
     * Method loads movie records from file to dataBase, one by one in loop;
    * */

    public void loadMoviesToDataBase(String fileName) throws AppException {
        MovieStoresJsonConverter movieStoresJsonConverter = new MovieStoresJsonConverter(fileName);
        List<Movie> movies = movieStoresJsonConverter.fromJson().get();
        for (Movie movie : movies) {
            movie.setRelease_date(movie.getRelease_date().plusDays(1));
            movieRepository.add(movie);
        }
    }

    public void removeMovieById(Integer movieId) {
        if (movieId == null) {
            throw new AppException("null id number");
        }
        movieRepository.delete(movieId);
    }

    public void showMovieById(Integer id) {
        System.out.println(getMovieById(id));
    }

    public void removeMovieById() throws AppException {
        Integer id = DataManager.getInt(" PRESS ID MOVIE NUMBER");
        removeMovieById(id);
    }

    public void showAllMoviesToday() {
        if (isMoviesBaseEmpty()) {
            System.out.println(" DATABASE IS EMPTY \n");
        } else {
            System.out.println("-----------------------------------------------------------------------------\n");
            System.out.printf("%5s %40s %25s %15s %15s %15s", "MOVIE ID", "TITLE", "GRADE", "DURATION", "PRICE", "RELEASE DATA\n");
            getAllMovies().stream().filter(f -> f.getRelease_date().equals(LocalDate.now())).forEach(this::printFormattedMovie);
            System.out.println("-----------------------------------------------------------------------------\n");
        }
    }

    private void printFormattedMovie(Movie s) {
        System.out.format("%5s %50s %20s %10d %20s %15s \n", s.getId(), s.getTitle(), s.getGenre(), s.getDuration(), s.getPrice(), s.getRelease_date());
    }

    public List<MovieWithDateTime> getInfo() {
        return movieRepository.getInfo();
    }

    boolean isMoviesBaseEmpty() {
        return getAllMovies().isEmpty();
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Movie getMovieById(Integer movieId) {
        return movieRepository.findOne(movieId).orElseThrow(() -> new AppException(" Wrong ID number "));
    }

    public void addMovie(Movie movie) {
        if (movie == null) {
            throw new AppException("add movie null arg");
        }
        movieRepository.add(movie);
    }


    public Movie createMovie() {
        String title = DataManager.getLine(" GIVE A TITLE ");
        Genre genre = Genre.valueOf(DataManager.getLine(" GIVE A GENRE  - > ACTION, HORROR, FANTASY, DRAMA, COMEDY ").toUpperCase());
        Double price = Double.valueOf(DataManager.getDouble(" GIVE A PRICE -> pattern ##.## "));
        Integer duration = DataManager.getInt(" GIVE NUMBER OF MINUTES TIME, DURATION TIME ");
        return Movie.builder().title(title).genre(genre).price(price).duration(duration).price(price).build();
    }

    public void sortMoviesByDurationTime() {
        getAllMovies().stream().sorted(Comparator.comparing(Movie::getDuration, Comparator.reverseOrder())).forEach(System.out::println);
    }

    public void editMovieById() {
        Integer idMovie = DataManager.getInt(" GIVE MOVIE ID TO EDIT ");
        Movie movie = createMovie();
        movieRepository.update(idMovie, movie);
    }

    /**
     * Methods for visualization movie Data
     * printStatisticByMoviePrice()
     * printStatisticByDurationTime()
     * printMapOfGenreAndNumberMovies()
     * */

    public void printStatisticByMoviePrice() {

        DecimalFormat dc = new DecimalFormat("#.##");
        DoubleSummaryStatistics stats = movieRepository.findAll()
                .stream()
                .collect(Collectors.summarizingDouble(Movie::getPrice));
        System.out.println("Statistic by movie Price");
        System.out.println("-----------------------------------------------------------------------------\n");
        System.out.println(" average praise for tickets -> " + dc.format(stats.getAverage()));
        System.out.println(" average praise for tickets -> " + dc.format(stats.getMax()));
        System.out.println(" average praise for tickets -> " + dc.format(stats.getMin()));
        System.out.println("-----------------------------------------------------------------------------\n");
    }

    public void printStatisticByDurationTime() {
        DecimalFormat dc = new DecimalFormat("#.##");
        IntSummaryStatistics stats = movieRepository.findAll()
                .stream()
                .collect(Collectors.summarizingInt(Movie::getDuration));
        System.out.println("Statistic of duration Time");
        System.out.println("-----------------------------------------------------------------------------\n");
        System.out.println(" average time for tickets -> " + dc.format(stats.getAverage()));
        System.out.println(" average time for tickets -> " + dc.format(stats.getMax()));
        System.out.println(" average time for tickets -> " + dc.format(stats.getMin()));
        System.out.println("-----------------------------------------------------------------------------\n");
    }

    public void printMapOfGenreAndNumberMovies() {
        System.out.println("MAP WITH GENRE NAD NUMBER OF MOVIES");
        System.out.println("-----------------------------------------------------------------------------\n");
        movieRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(Movie::getGenre))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue().size()
                ))
                .entrySet().forEach(System.out::println);
        System.out.println("-----------------------------------------------------------------------------\n");
    }


}
