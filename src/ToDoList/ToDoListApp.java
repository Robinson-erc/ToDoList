package ToDoList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
                "To remove tasks click the task to be removed then click remove task");
        tutorialLabel.setHorizontalAlignment(JLabel.CENTER);
        frame.add(tutorialLabel, BorderLayout.NORTH);

        JList<String> taskList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(taskList);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new BorderLayout());
        JTextField taskTextField = new JTextField();
        taskTextField.setPreferredSize(new Dimension(300, 30));
        JButton addButton = new JButton("Add Task");
        JButton removeButton = new JButton("Remove Task");
        JButton displayButton = new JButton("Display Tasks");
        JButton clearButton = new JButton("Clear");

        inputPanel.add(taskTextField, BorderLayout.CENTER);
        inputPanel.add(addButton, BorderLayout.EAST);
        inputPanel.add(removeButton, BorderLayout.WEST);
        inputPanel.add(displayButton, BorderLayout.SOUTH);
        inputPanel.add(clearButton, BorderLayout.NORTH);

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
                        listModel.addElement(task.getDescription()); // Convert Task to String
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

        frame.setVisible(true);
    }
}



