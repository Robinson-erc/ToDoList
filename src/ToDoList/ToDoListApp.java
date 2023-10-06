package ToDoList;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.io.*;

public class ToDoListApp {
    private static ToDoList toDoList = new ToDoList();
    private static DefaultListModel<String> listModel = new DefaultListModel<>();
    private static boolean isListDisplayed = false;

    public static void main(String[] args) {
        JFrame frame = new JFrame("To-Do List");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new BorderLayout());

        // Add a tutorial display at the top
        JLabel tutorialLabel = new JLabel("How to Use the To-Do List App:\n " +
                "To remove tasks, click the task to be removed, then click Remove Task.\n " +
                "To load saved tasks, click Load.");
        tutorialLabel.setHorizontalAlignment(JLabel.CENTER);
        frame.add(tutorialLabel, BorderLayout.NORTH);

        JList<String> taskList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(taskList);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new FlowLayout());

        JTextField taskTextField = new JTextField();
        taskTextField.setPreferredSize(new Dimension(300, 30));
        JButton addButton = new JButton("Add Task");
        JButton removeButton = new JButton("Remove Task");
        JButton displayButton = new JButton("Display Tasks");
        JButton clearButton = new JButton("Clear");
        JButton loadButton = new JButton("Load"); // Add a "Load" button
        JButton saveButton = new JButton("Save");

        inputPanel.add(taskTextField, BorderLayout.CENTER);
        inputPanel.add(addButton, BorderLayout.EAST);
        inputPanel.add(removeButton, BorderLayout.WEST);
        inputPanel.add(displayButton, BorderLayout.SOUTH);
        inputPanel.add(clearButton, BorderLayout.NORTH);
        inputPanel.add(loadButton, BorderLayout.WEST); // Add the "Load" button
        inputPanel.add(saveButton, BorderLayout.WEST);


        frame.add(inputPanel, BorderLayout.SOUTH);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String taskDescription = taskTextField.getText().trim();
                if (!taskDescription.isEmpty()) {
                    toDoList.addTask(taskDescription);
                    taskTextField.setText("");
                }
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = taskList.getSelectedIndex();
                if (selectedIndex != -1) {
                    toDoList.removeTask(selectedIndex);
                    listModel.remove(selectedIndex);
                }
            }
        });

        displayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isListDisplayed) {
                    listModel.clear();
                    for (Task task : toDoList.getTasks()) {
                        listModel.addElement(task.getDescription());
                    }
                    isListDisplayed = true;
                }
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listModel.clear();
                isListDisplayed = false;
            }
        });
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("To-Do List Files", "dat");
                fileChooser.setFileFilter(filter);

                int result = fileChooser.showSaveDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    String selectedFilePath = fileChooser.getSelectedFile().getAbsolutePath();
                    toDoList.saveToFile(selectedFilePath);
                }
            }
        });
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("To-Do List Files", "dat");
                fileChooser.setFileFilter(filter);

                int result = fileChooser.showOpenDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    String selectedFilePath = fileChooser.getSelectedFile().getAbsolutePath();
                    toDoList.loadFromFile(selectedFilePath);
                    refreshList();
                }
            }
        });

        frame.setVisible(true);
    }

    // Helper method to refresh the task list display
    private static void refreshList() {
        listModel.clear();
        for (Task task : toDoList.getTasks()) {
            listModel.addElement(task.getDescription());
        }
        isListDisplayed = true;
    }
}



