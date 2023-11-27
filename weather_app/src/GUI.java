

import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.json.simple.JSONObject;
import com.sun.javafx.application.PlatformImpl;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.*;
import java.awt.*;

public
    class GUI implements ActionListener {


    private JFrame frame;
    private JPanel panel;
    private WebEngine engine;
    private JPanel weatherPanel;
    private JPanel currencyPanel;
    private JPanel userPanel;
    private JTextField countryTF;
    private JTextField cityTF;
    private JTextField currencyTF;
    private JButton check;
    private Service service;
    private JFXPanel vikiPanel;

    public GUI(Service service){
        vikiPanel=new JFXPanel();
        this.service=service;
        setFrame();
        setUserPanel();
        setCurrencyPanel(service);
        setWeatherPanel(service);
        String url = String.format("https://en.wikipedia.org/wiki/%s", "Warsaw");
        setVikiPanel(url);
        setSouthPanel();

    }

    public void setFrame(){
        frame=new JFrame();
        frame.setSize(640,480);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void setUserPanel(){
        userPanel=new JPanel();
        userPanel.setLayout(new FlowLayout());
        JLabel countryLB = new JLabel("Country :");
        countryTF=new JTextField();
        countryTF.setPreferredSize(new Dimension(100,30));
        userPanel.add(countryLB);
        userPanel.add(countryTF);
        JLabel cityLB=new JLabel("City :");
        cityTF=new JTextField();
        cityTF.setPreferredSize(new Dimension(100,30));
        userPanel.add(cityLB);
        userPanel.add(cityTF);
        JLabel currencyLB=new JLabel("Currency :");
        currencyTF=new JTextField("PLN");
        currencyTF.setPreferredSize(new Dimension(100,30));
        userPanel.add(currencyLB);
        userPanel.add(currencyTF);
        check=new JButton("Check");
        check.addActionListener(this);
        userPanel.add(check);
        frame.add(userPanel,BorderLayout.NORTH);
    }

    public void setSouthPanel(){
        panel=new JPanel();
        panel.setLayout(new GridLayout(0,3));
        panel.add(currencyPanel);
        panel.add(weatherPanel);
        panel.add(vikiPanel);
        frame.add(panel,BorderLayout.SOUTH);
    }



    public void setCurrencyPanel(Service service){
        currencyPanel=new JPanel();
        currencyPanel.setLayout(new GridLayout(3,0));
        JLabel topLabel = new JLabel("Currency");
        currencyPanel.add(topLabel);
        JLabel compareWithCountry=new JLabel(service.getCurrencyName() + " compare to " + currencyTF.getText() + " : " + service.getRateFor(currencyTF.getText()));
        currencyPanel.add(compareWithCountry);
        JLabel compareWithPLN=new JLabel("PLN" + " compare to " + service.getCurrencyName() + " : " + service.getNBPRate());
        currencyPanel.add(compareWithPLN);
    }

    public void setWeatherPanel(Service service){
        weatherPanel=new JPanel();
        weatherPanel.setLayout(new GridLayout(4,0));
        JSONObject obj=service.getWeatherToObject(service.getCityName());
        JLabel topLabel=new JLabel("Place : " + service.getCityName() + " , " + service.getCountryName());
        JLabel temperatureLabel=new JLabel("Temperature : " + +(Math.round(((Double)(((JSONObject)(obj.get("main"))).get("temp")))-273))+"°C");
        JLabel pressureLabel=new JLabel("Pressure : "+(((JSONObject)(obj.get("main"))).get("pressure")));
        JLabel humidityLabel=new JLabel("Humidity: " +(((JSONObject)(obj.get("main"))).get("humidity")));
        weatherPanel.add(topLabel);
        weatherPanel.add(temperatureLabel);
        weatherPanel.add(pressureLabel);
        weatherPanel.add(humidityLabel);
    }



    private void setVikiPanel(String url) {
        Thread thread=new Thread(()->{

            WebView view = new WebView();
            engine = view.getEngine();
            vikiPanel.setScene(new Scene(view));
            engine.load(url);

        });

        PlatformImpl.startup(thread);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==check){
            Service service=new Service(countryTF.getText(),cityTF.getText());
            panel.remove(currencyPanel);
            panel.remove(weatherPanel);
            this.service=service;
            setCurrencyPanel(this.service);
            setWeatherPanel(this.service);
            panel.add(currencyPanel);
            panel.add(weatherPanel);
            String url = String.format("https://en.wikipedia.org/wiki/%s", service.getCityName());
            setVikiPanel(url);
            panel.repaint();
            panel.revalidate();
        }
    }
}
