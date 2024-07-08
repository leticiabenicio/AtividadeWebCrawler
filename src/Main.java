import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        NewsPortal g1 = new G1Portal();
        NewsPortal uol = new UolPortal();

        String keyword = "Brasil"; // Palavra-chave a ser filtrada
        String fileNameG1 = "Z:\\20212370043\\Downloads\\g1_noticias.txt";
        String fileNameUol = "Z:\\20212370043\\Downloads\\uol_noticias.txt";

        g1.collectAndSaveNews(keyword, fileNameG1);
        uol.collectAndSaveNews(keyword, fileNameUol);
    }
}
