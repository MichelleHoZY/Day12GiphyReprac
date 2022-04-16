package vttp2022.paf.GIPHYRepractice.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Service
public class service {

    @Value("${giphy.api.key}")
    private String giphyKey;

    Logger logger = LoggerFactory.getLogger(service.class);

    public List<String> favMovies(String name) {

        logger.info(">>>>> Name: " + name);

        String url = UriComponentsBuilder
                .fromUriString("https://api.giphy.com/v1/gifs/search?")
                .queryParam("api_key", giphyKey)
                .queryParam("q", name)
                .queryParam("limit", 33)
                .queryParam("offset", 0)
                .queryParam("rating", "g")
                .queryParam("lang", "en")
                .toUriString();

        logger.info(">>>>> URL: " + url);

        RequestEntity<Void> req = RequestEntity
            .get(url)
            .accept(MediaType.APPLICATION_JSON)
            .build();

        RestTemplate template = new RestTemplate();

        ResponseEntity<String> resp = template.exchange(req, String.class);

        List<String> urlList = new LinkedList<>();

        try (InputStream is = new ByteArrayInputStream(resp.getBody().getBytes())) {
            JsonReader reader = Json.createReader(is);
            JsonObject jObject = reader.readObject();
            JsonArray dataArray = jObject.getJsonArray("data");

            for (int i=0; i<dataArray.size(); i++) {
                JsonObject eachUrlObject = dataArray.getJsonObject(i);
                JsonObject imagesObject = eachUrlObject.getJsonObject("images");
                JsonObject fixedHeight = imagesObject.getJsonObject("fixed_height");
                String imageUrl = fixedHeight.getString("url");
                urlList.add(imageUrl);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        logger.info(">>>>> URL List: " + urlList);

        return urlList;

    }
    
}
