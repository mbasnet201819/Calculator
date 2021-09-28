package main;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.ArrayList;


public class Controller {

    private double result;
    private ArrayList<String> holder = new ArrayList<>();
    private boolean newCalculation = false;
    private double previousAnswer = 0.0;
    private boolean isLightMode = true;

    @FXML private TextField input;
    @FXML private TextField resultBox;
    @FXML private Pane parent;
    @FXML private Button btnMode;
    @FXML private ImageView imgMode;


    public void changeMod(ActionEvent event){
        isLightMode = !isLightMode;
        if(isLightMode){
            setLightMode();
        }
        else{
            setDarkMode();
        }
    }

    private void setLightMode(){
        parent.getStylesheets().remove("styles/darkMode.css");
        parent.getStylesheets().add("styles/lightMode.css");
        Image image = new Image("img/ic_dark.png");
        imgMode.setImage(image);
    }

    private void setDarkMode(){
        parent.getStylesheets().remove("styles/lightMode.css");
        parent.getStylesheets().add("styles/darkMode.css");
        Image image = new Image("img/ic_light.png");
        imgMode.setImage(image);
    }


    @FXML
    public void initialize() {
        input.setEditable(false);
        resultBox.setEditable(false);
    }


    public void getInput(ActionEvent evt) {


        if (newCalculation) {
            input.setText("");
            resultBox.setText("");
            holder.clear();
        }


        newCalculation = false;


        Button button = (Button) evt.getSource();
        final String buttonText = button.getText();

        if (buttonText.equals("C")) {

            input.setText("");
            resultBox.setText("");
            holder.clear();
            return;
        }


        if (buttonText.matches("-?\\d+(\\.\\d+)?") || buttonText.contains(".")) {


            if (holder.size() > 0) {
                String previousElement = holder.get(holder.size() - 1);


                if (previousElement.matches("-?\\d+(\\.\\d+)?") || previousElement.contains(".")) {

                    holder.set(holder.size() - 1, previousElement + buttonText);
                    input.appendText(buttonText);
                    return;
                }
            }

            input.appendText(buttonText);
            holder.add(buttonText);
            return;

        }

        if (buttonText.matches("[-+*÷^%.√]")) {

            if (holder.size() > 0) {
                String previousElement = holder.get(holder.size() - 1);

                if (previousElement.matches("[-+*÷^%.√]")) {

                    return;
                }
            }


            if (holder.size() == 0 && !buttonText.equals("√")) {
                input.appendText("Ans(" + previousAnswer + ")");
                holder.add(String.valueOf(previousAnswer));
            }
            input.appendText(buttonText);
            holder.add(buttonText);
            return;
        }

        if (buttonText.equals("Ans")) {

            input.appendText(buttonText + "(" + previousAnswer + ")");
            holder.add(String.valueOf(previousAnswer));
            return;
        }

        //Check if buttonText is ready to perform a calculation
        if (buttonText.equals("=")) {

            if (holder.size() >= 3 || holder.contains("√")) {

                calculate();

                resultBox.setText(String.valueOf(result));
                newCalculation = true;

                previousAnswer = result;
            }

        }

    }


    private void calculate() {

        try {
            while (holder.size() > 1) {

                if (holder.indexOf("√") > -1) {

                    int index = holder.indexOf("√");

                    double right = Double.parseDouble(holder.get(index + 1));

                    double result = Math.sqrt(right);

                    holder.set(index, String.valueOf(result));

                    holder.remove(index + 1);

                }

                if (holder.indexOf("^") > -1) {

                    int index = holder.indexOf("^");
                    double left = 1, right = 1;

                    if (holder.get(index - 1).matches("-?\\d+(\\.\\d+)?")) {
                        left = Double.parseDouble(holder.get(index - 1));
                    }

                    if (holder.get(index + 1).matches("-?\\d+(\\.\\d+)?")) {
                        right = Double.parseDouble(holder.get(index + 1));
                    }


                    double result = Math.pow(left, right);

                    holder.set(index - 1, String.valueOf(result));

                    holder.remove(index);
                    holder.remove(index);

                }


                if (holder.indexOf("*") > -1) {
                    int index = holder.indexOf("*");

                    double left = Double.parseDouble(holder.get(index - 1));

                    double right = Double.parseDouble(holder.get(index + 1));

                    double result = left * right;

                    holder.set(index - 1, String.valueOf(result));

                    holder.remove(index);
                    holder.remove(index);

                }

                if (holder.indexOf("÷") > -1) {
                    int index = holder.indexOf("÷");

                    double left = Double.parseDouble(holder.get(index - 1));

                    double right = Double.parseDouble(holder.get(index + 1));

                    double result = left / right;

                    holder.set(index - 1, String.valueOf(result));

                    holder.remove(index);
                    holder.remove(index);

                }

                if (holder.indexOf("%") > -1) {
                    int index = holder.indexOf("%");

                    double left = Double.parseDouble(holder.get(index - 1));

                    double right = Double.parseDouble(holder.get(index + 1));

                    double result = left % right;

                    holder.set(index - 1, String.valueOf(result));

                    holder.remove(index);
                    holder.remove(index);

                }

                if (holder.indexOf("+") > -1) {
                    int index = holder.indexOf("+");

                    double left = Double.parseDouble(holder.get(index - 1));

                    double right = Double.parseDouble(holder.get(index + 1));

                    double result = left + right;

                    holder.set(index - 1, String.valueOf(result));

                    holder.remove(index);
                    holder.remove(index);

                }

                if (holder.indexOf("-") > -1) {
                    int index = holder.indexOf("-");

                    double left = Double.parseDouble(holder.get(index - 1));

                    double right = Double.parseDouble(holder.get(index + 1));

                    double result = left - right;

                    holder.set(index - 1, String.valueOf(result));

                    holder.remove(index);
                    holder.remove(index);

                }

            }

            result = Double.parseDouble(holder.get(0));

        } catch (RuntimeException e) {

            resultBox.setText("Calculation Error");

        }

    }
}
