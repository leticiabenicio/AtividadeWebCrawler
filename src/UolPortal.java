import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UolPortal extends NewsPortal {
    @Override
    protected List<String> collectNewsLinks() throws IOException {
        List<String> links = new ArrayList<>();
        String url = "https://www.uol.com.br/";

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            // Utilizando regex para extrair os links das notícias
            Pattern pattern = Pattern.compile("<a\\s+href=\"(https://noticias\\.uol\\.com\\.br/[^\"]+)\">");
            Matcher matcher = pattern.matcher(response.toString());

            while (matcher.find()) {
                links.add(matcher.group(1));
            }
        }

        return links;
    }

    @Override
    protected String getTitle(String link) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(link).openConnection();
        connection.setRequestMethod("GET");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            // Utilizando regex para extrair o título da notícia
            Pattern pattern = Pattern.compile("<title>(.*?)</title>");
            Matcher matcher = pattern.matcher(response.toString());
            if (matcher.find()) {
                return matcher.group(1);
            }
        }

        return "";
    }
}
