package com.example.moviecatalogservice.resources;

import com.example.moviecatalogservice.CatalogItem;
import com.example.moviecatalogservice.Movie;
import com.example.moviecatalogservice.UserRating;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResources {
    //Autowired is a consumer. Spring will look for the bean that matches and try to consume
    private final RestTemplate restTemplate;
    private final WebClient.Builder webClientBuilder;

    public MovieCatalogResources(RestTemplate restTemplate, WebClient.Builder webClientBuilder) {
        this.restTemplate = restTemplate;
        this.webClientBuilder = webClientBuilder;
    }

    @RequestMapping("/hello")
    public String hello() {
        return "Hello World";
    }

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {

        UserRating userRatings = restTemplate.getForObject("http://localhost:8087/ratings/users/1" + userId, UserRating.class);

        return userRatings.getUserRating().stream().map(rating -> {

            //Replacing rest template with webclient builder
            Movie movies = restTemplate.getForObject("http://localhost:8084/movies/" + rating.getMovieId(), Movie.class);


            return new CatalogItem(movies.getName(), "some description", rating.getRating());
        }).collect(Collectors.toList());
    }
}


//We need the Ratings class from movies-data-services
//        List<Rating> ratingList = Arrays.asList(new Rating("foo", 4), new Rating("Another movie", 1));

//


  /*  Mono is a response that can hold either 0 or 1 stream.
            Movie movies = webClientBuilder.build().get().uri("http://localhost:8084/movies/" + rating.getMovieId()).retrieve().bodyToMono(Movie.class).block();*/


/*
        return Collections.singletonList(new CatalogItem("Titanic", "test", 3));
*/


//We need the Ratings class from movies-data-services
//        List<Rating> ratingList = Arrays.asList(new Rating("foo", 4), new Rating("Another movie", 1));

//        ParameterizedType<List<Rating>> parameterizedType = new
