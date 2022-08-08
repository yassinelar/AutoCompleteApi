package com.example.searchEngine;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.PostConstruct;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

import static com.example.searchEngine.util.regex;
import static java.util.stream.Collectors.toMap;


@SpringBootApplication
@RestController
public class SearchEngineApplication {
	private static Map<String, Integer> queries = new HashMap();
	private static ResourceBundle resource = ResourceBundle.getBundle("application");

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(SearchEngineApplication.class);
		application.run(args);
	}

	@PostConstruct
	protected void init() {
		String line = "";
		Logger logger = Logger.getLogger("SearchEngineApplication");

		try {
			String fileName = resource.getString("FILENAME");
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			int i = 0;
			while ((line = br.readLine()) != null) {
				if (i != 0) {
					int index = regex(line, "[0-9]+$");
					queries.put(line.substring(0, index - 1), Integer.parseInt(line.substring(index, line.length())));
				}
				i += 1;
			}
			logger.info("tree built successfully");
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	@GetMapping("/input")
	public List echo(@RequestParam (name = "text") String inputText) {
		// get the input text from the url http://localhost:8080/input?text={input}
		String input = inputText;
		Map<String, Integer> results = new HashMap();
		try {
			for (Map.Entry<String, Integer> req : queries.entrySet()) {
				if (req.getKey().startsWith(input)) {
					results.put(req.getKey(), req.getValue());
				}
			}
			if (results.size() == 0) {
				System.out.println (0);
			} else {
				Map<String, Integer> sorted = results.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue())).collect(toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2, LinkedHashMap::new));
				int numberOfValues = Math.min (results.size(), 10);
				return (new ArrayList<>(sorted.keySet()).subList(0, numberOfValues));
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}