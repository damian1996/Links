import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.lang.String;
import java.net.*;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.*;
import java.awt.*;

public class connect {
    int counter, size, current, test;
    List<String> imgs;
    List<String> https;
    GUI view;
    List<String> curr;

    public connect(String inUrl, GUI view, List<String> curr, int current, int test) {
        this.view = view;
        this.curr = curr;
        this.current = current;
        this.test = test;
        test = 0;
        counter = 0;
        size = 0;
        final JDialog loading = new JDialog(view.frame, "LOADING", true);
        loading.setLayout(new BorderLayout());
        JPanel p1 = new JPanel();
        JLabel label = new JLabel("Please wait...");
        label.setFont(new Font("Serif", Font.PLAIN, 30));
        p1.add(label);
        loading.add(p1, BorderLayout.CENTER);
        loading.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        loading.setMinimumSize(new Dimension(500, 500));

        SwingWorker<String, Void> worker = new SwingWorker<String, Void>() {
            @Override
            protected String doInBackground() throws InterruptedException{
                String x = "SUKCES!";
                String y = "PORAZKA";
                try{
                    countImg(inUrl, loading);
                } catch (Exception e){
                    return y;
                }
                return x;
            }
            @Override
            protected void done() {
                loading.dispose();
            }
        };
        worker.execute();
        loading.setVisible(true);
    }
    /*  try {
                    get();
                } catch (Exception e) {

                }*/

    public void countImg(String inUrl, JDialog loading) throws Exception {
        InputStream input = null;
        Elements img = null;
        try {
            URL page = new URL(inUrl);
            URLConnection yc = page.openConnection();
            input = yc.getInputStream();
            Document doc = Jsoup.parse(input, "UTF-8", inUrl);
            Elements links = doc.select("a[href]");
            img = doc.getElementsByTag("img");
            imgs = new ArrayList<String>();
            https = new ArrayList<String>();
            for(Element el : links){
                String httpString = el.attr("href");
                if(httpString.startsWith("http")) {
                    https.add(httpString);
                }
            }
        } catch (Exception f){
            // IllegalArgumentException przez URLConnection
            if(test == 1){
                return;
            }
            JOptionPane.showMessageDialog(view.frame, "Incorrect URL! Try again",  "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        for (Element el : img) {
            try {
                String imgString = el.attr("src");
                if (imgs.contains(imgString) == false) {
                    imgs.add(imgString);
                    counter++;
                    String stringUrl = el.attr("abs:src");
                    HttpURLConnection connection = (HttpURLConnection) new URL(stringUrl).openConnection();
                    connection.setRequestMethod("HEAD");
                    int length = connection.getContentLength();
                    size += length;
                }
            } catch (Exception e) {
                continue;
            }
        }
        System.out.println("counter " + counter);
        System.out.println("size " + size);
        input.close();
    }
}