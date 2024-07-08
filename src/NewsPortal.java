import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class NewsPortal {

    // Template Method
    public void collectAndSaveNews(String keyword, String fileName) {
        try {
            List<String> newsLinks = collectNewsLinks();
            List<String> filteredLinks = filterByKeyword(newsLinks, keyword);
            saveToFile(filteredLinks, fileName);
        } catch (IOException e) {
            System.err.println("Erro ao coletar notícias: " + e.getMessage());
        }
    }

    // Métodos abstratos que as subclasses devem implementar
    protected abstract List<String> collectNewsLinks() throws IOException;

    protected abstract String getTitle(String link) throws IOException;

    // Método comum que pode ser utilizado diretamente pelas subclasses
    private List<String> filterByKeyword(List<String> links, String keyword) {
        List<String> filteredLinks = new ArrayList<>();
        for (String link : links) {
            try {
                String title = getTitle(link);
                if (title.toLowerCase().contains(keyword.toLowerCase())) {
                    filteredLinks.add(link);
                }
            } catch (IOException e) {
                System.err.println("Erro ao obter título: " + e.getMessage());
            }
        }
        return filteredLinks;
    }

    private void saveToFile(List<String> links, String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (String link : links) {
                writer.write(link + "\n");
            }
            System.out.println("Dados salvos em " + fileName);
        } catch (IOException e) {
            throw new IOException("Erro ao salvar arquivo: " + e.getMessage());
        }
    }
}
