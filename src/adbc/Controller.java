package adbc;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import utils.PreferenceUtil;

import java.awt.*;
import java.io.*;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private JFXButton sdkPathButton;

    @FXML
    private JFXTextField portField;

    @FXML
    private JFXButton connectButton;


    @FXML
    private Label sdkPathLabel;


    PreferenceUtil preferenceUtil;
    String sdkPath;
    String clippedIp;
    private static final String COMMAND_SUFFIX_WIN = "\\platform-tools\\adb connect ";
    private static final String COMMAND_SUFFIX_MAC = "/platform-tools/adb connect ";

    private String os;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        os = System.getProperty("os.name").toLowerCase();
        preferenceUtil = new PreferenceUtil();
        try {
            String ip = InetAddress.getLocalHost().getHostAddress();
            clippedIp = ip.substring(0,ip.lastIndexOf(".") + 1);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }


        sdkPath = preferenceUtil.getPreference(Main.ADB_PATH_KEY);
        sdkPathLabel.setText(sdkPath);

        sdkPathButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String tempPath = Main.openChooser();
                if(!tempPath.matches("")) {
                    sdkPath = tempPath;
                }
                sdkPathLabel.setText(sdkPath);
                preferenceUtil.addPreference(Main.ADB_PATH_KEY, sdkPath);
            }
        });

        connectButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                connectAdb();
            }
        });
    }

    private void connectAdb() {
        if(sdkPath.matches("")) {
            sdkPathLabel.setText("Select Android SDK Path!");
        } else {
            if(getEndIp() !=null) {
                String fullCmd = getCommandPrefix() + sdkPath + getCommandSuffix() + clippedIp + getEndIp() + ":5555";

                try {
                    Process process = Runtime.getRuntime().exec(fullCmd);
                    BufferedReader bufferedInputStream = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    String s = null;
                    while((s = bufferedInputStream.readLine()) != null) {
                        if(s.contains("connected")) {
                            sdkPathLabel.setText("CONNECTED");
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String getEndIp() {
        if(portField.getText().matches("")) {
            sdkPathLabel.setText("Enter End IP");
            return null;
        } else {
            return portField.getText();
        }
    }

    private String getCommandSuffix() {
       if(os.startsWith("windows")) {
           return COMMAND_SUFFIX_WIN;
       } else {
           return COMMAND_SUFFIX_MAC;
       }
    }

    private String getCommandPrefix() {
        if(os.startsWith("windows")) {
            return "";
        } else {
            return "./";
        }
    }

    private void log(String message) {
        System.out.println(message);
    }
}
