import java.io.*;
import java.util.*;


public class QuestionTree {
    private QuestionNode overallRoot;
    private int totalGames = 0;
    private int gamesWon = 0;
    private UserInterface ui;

    public QuestionTree(UserInterface ui) {
        if (ui == null) {
            throw new IllegalArgumentException("The constructor was passed a null object");
        } else {
            overallRoot = new QuestionNode("computer");
        }
        this.ui = ui;
    }

    public void play() {
        play(overallRoot);
    }

    private void play(QuestionNode root) {
        if (root.left == null && root.right == null) {
            totalGames++;
            ui.print("Would your object happpen to be a " + root.data + "?");
            if (ui.nextBoolean()) {
                gamesWon++;
                ui.println("I win!");
            } else {
                ui.print("I lose. What is your object?");
                String object = ui.nextLine();
                ui.print("Type a yes/no question to distinguish your item from " + root.data + ":");
                String question = ui.nextLine();
                ui.print("And what is the answer for your object?");
                if (ui.nextBoolean()) {
                    root.left = new QuestionNode(object);
                    root.right = new QuestionNode(root.data);
                    root.data = question;
                } else {
                    root.left = new QuestionNode(root.data);
                    root.right = new QuestionNode(object);
                    root.data = question;
                }
            }
        } else {
            ui.print(root.data); // recursive case
            if (ui.nextBoolean()) {
                play(root.left);
            } else {
                play(root.right);
            }
        }
    }

    public void save(PrintStream output) {
        if (output == null) {
            throw new IllegalArgumentException("The save method was passed " +
                    "a null PrintStream parameter.");
        }
        save(output, overallRoot);
    }
    //pre:Passing in ui and question node for wriing information
    //Return: a new ouput on the .txt where the new question and answer node will be printed 
    private void save(PrintStream output, QuestionNode node) {
        if (node == null) {
            return;
        }
        if (node.left == null && node.right == null) {
            output.println("A:" + node.data);
        } else {
            output.println("Q:" + node.data);
            save(output, node.left);
            save(output, node.right);
        }
    }

    public void load(Scanner input) {
        if (input == null) {
            throw new IllegalArgumentException("The load function was passed a null object");
        } else {
            overallRoot = buildTree(input);
        }
    }

    private QuestionNode buildTree(Scanner input) {
        if (input == null) {
            throw new IllegalArgumentException("buildTree was passed a null string");
        } else {
            if (!input.hasNext()) {
                return null;
            }

            String data = input.nextLine();

            QuestionNode node;
            if (data.startsWith("A")) {
                node = new QuestionNode(data.substring(2));
            } else {
                node = new QuestionNode(data.substring(2));
                node.left = buildTree(input); 
                node.right = buildTree(input);
            }
            return node;
        }
    }

    public int totalGames() {
        return totalGames;
    }

    public int gamesWon() {
        return gamesWon;
    }

}
